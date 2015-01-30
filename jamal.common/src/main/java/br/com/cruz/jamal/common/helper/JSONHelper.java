package br.com.cruz.jamal.common.helper;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.cruz.jamal.common.annotation.PublicField;
import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;

public class JSONHelper extends JamalHelper {

	private static final long serialVersionUID = -2611604875090789180L;
	
	public static final JSONObject THIS = new JSONObject("{value:\"this\"}");
	
	
	// toJSONString
	
	public static final <T> String toJSONString(T object, String... fieldNameArray) throws JamalException {

		if (object == null) {
			return null;
		}
		
		try {
			
			if (ReflectionHelper.isPrimitiveOrWrapper(object.getClass()) || ReflectionHelper.isString(object.getClass())) {
				return object.toString();
			}
			
			if (ReflectionHelper.isEnum(object.getClass())) {
				return ((Enum<?>)object).name();
			}
			
			if (ReflectionHelper.isDateOrCalendar(object.getClass())) {
				
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMDDHHmmssSSS");
				
				if (ReflectionHelper.isDate(object.getClass())) {
					return simpleDateFormat.format((Date)object);
				}
				
				return simpleDateFormat.format(((Calendar)object).getTime());
			}
			
			if (ReflectionHelper.isArrayOrCollection(object.getClass())) {
				
				JSONArray jsonArray = 
					JSONHelper.toJSONArray(ReflectionHelper.convertToArray(object), fieldNameArray);
				
				if (jsonArray == null) {
					jsonArray = new JSONArray();
				}
				
				return jsonArray.toString();
			}
			
			JSONObject jsonObject =
				JSONHelper.toJSONObject(object, fieldNameArray);
			
			if (jsonObject == null) {
				return null;
			}
			
			return jsonObject.toString();
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("toJSON", e);
		}
	}
	
	// toJSONObject
	
	
	// toJSONObject
	
	public static final <T> JSONObject toJSONObject(T object, String... fieldNameArray) throws JamalException {
		
		try {
			
			if (object == null) {
				return null;
			}
			
			JSONObject jsonObject = new JSONObject();
			
			List<Field> publicFieldList =
				ReflectionHelper.getPublicFieldList(object.getClass(), true, fieldNameArray);
			
			if (CollectionHelper.isNullOrEmpty(publicFieldList)) {
				return jsonObject;
			}
			
			for (Field publicField : publicFieldList) {
				
				Object publicFieldValue =
					ReflectionHelper.get(object, publicField, publicField.getType());
				
				if (publicFieldValue == null) {
					jsonObject.put(publicField.getName(), publicFieldValue);
					continue;
				}
				
				List<String> fieldNameList = new ArrayList<String>();
				for (String fieldName : fieldNameArray) {
					if (StringHelper.isNullOrEmpty(fieldName)) {
						continue;
					}
					if (fieldName.startsWith(publicField.getName()) && fieldName.contains(".")) {
						fieldNameList.add(fieldName.substring(fieldName.indexOf('.')));
					}
				}
				
				if (ReflectionHelper.isArrayOrCollection(publicField.getType())) {
					
					JSONArray jsonArray = 
						JSONHelper.toJSONArray(object, ReflectionHelper.convertToArray(publicFieldValue), fieldNameList.toArray(new String[]{}));
					
					if (jsonArray == null) {
						jsonArray = new JSONArray();
					}
					
					jsonObject.put(publicField.getName(), jsonArray);
					continue;
				}
				
				if (!ReflectionHelper.isKnownType(publicField.getType())) {
					
					if(!publicField.isAnnotationPresent(PublicField.class)) {
						continue;
					}
					
					if (publicField.getAnnotation(PublicField.class).isToString()) {
						
						if (publicFieldValue == object || publicFieldValue.equals(object)) {
							jsonObject.put(publicField.getName(), JSONHelper.THIS);
							continue;
						}
						
						String jsonStringValue =
							JSONHelper.toJSONString(publicFieldValue, fieldNameList.toArray(new String[]{}));
						
						jsonObject.put(publicField.getName(), jsonStringValue);
						continue;
					}
					
					continue;
				}
				
				if (ReflectionHelper.isDateOrCalendar(publicField.getType()) || ReflectionHelper.isEnum(publicField.getType())) {
					publicFieldValue = JSONHelper.toJSONString(publicFieldValue);
				}
				
				jsonObject.put(publicField.getName(), publicFieldValue);
			
			}
			
			return jsonObject;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("toJSONObject", e);
		}
		
	}
	
	
	// toJSONArray
	
	
	// toJSONArray
	
	public static final <T> JSONArray toJSONArray(Collection<T> objectList, String... fieldNameArray) throws JamalException {
		
		try {
			
			if (CollectionHelper.isNullOrEmpty(objectList)) {
				return new JSONArray();
			}
			
			return JSONHelper.toJSONArray(objectList.toArray(), fieldNameArray);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("toJSONArray", e);
		}
		
	}
	
	@SafeVarargs
	public static final <T> JSONArray toJSONArray(T[] objectArray, String... fieldNameArray) throws JamalException {
		return JSONHelper.toJSONArray(null, objectArray, fieldNameArray);
	}
	
	@SafeVarargs
	public static final <T> JSONArray toJSONArray(Object parent, T[] objectArray, String... fieldNameArray) throws JamalException {
		
		try {
			
			JSONArray jsonArray = new JSONArray();
			
			if (CollectionHelper.isNullOrEmpty(objectArray)) {
				return jsonArray;
			}
			
			for (T child : objectArray) {
				
				if (child == null) {
					continue;
				}
				
				if (parent != null && (parent == child || parent.equals(child))) {
					jsonArray.put(JSONHelper.THIS);
					continue;
				}
				
				if (ReflectionHelper.isArrayOrCollection(child.getClass())) {
					
					JSONArray jsonArrayValue = 
						JSONHelper.toJSONArray(ReflectionHelper.convertToArray(child), fieldNameArray);
						
					if (jsonArrayValue == null) {
						jsonArrayValue = new JSONArray();
					}
					
					jsonArray.put(jsonArrayValue);
					continue;
				}
				
				if (!ReflectionHelper.isKnownType(child.getClass())) {
					jsonArray.put(JSONHelper.toJSONObject(child, fieldNameArray));
					continue;
				}
				
				if (ReflectionHelper.isDateOrCalendar(child.getClass()) || ReflectionHelper.isEnum(child.getClass())) {
					jsonArray.put(JSONHelper.toJSONString(child, fieldNameArray));
					continue;
				}
				
				jsonArray.put(child);
				
			}
			
			return jsonArray;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("toJSONArray", e);
		}
	}
	
	
	// toObject
	
	public static final <T> T toObject(String json, Class<T> clazz) throws JamalException {
		
		try {
			
			if (StringHelper.isNullOrEmpty(json)) {
				return null;
			}
			
			ValidationHelper.notNull(clazz);
			
			return JSONHelper.toObject(new JSONObject(json), clazz);
			
		} catch (Exception e) {
			throw new JamalException("toObject", e);
		}
		
	}
	
	public static final <T> T toObject(JSONObject jsonObject, Class<T> clazz) throws JamalException {
		
		try {
			
			if (jsonObject == null) {
				return null;
			}
			
			ValidationHelper.notNull(clazz);
			
			T object = ReflectionHelper.newInstance(clazz);
			
			if (object == null) {
				return null;
			}
			
			List<Field> fieldList = ReflectionHelper.getPublicFieldList(clazz, true);
			
			for (Field field : fieldList) {
				
				try {
					
					if (ReflectionHelper.isArrayOrCollection(object.getClass())) {
						continue;
					}
					
					if (!ReflectionHelper.isKnownType(field.getType())) {
						continue;
					}
					
					ReflectionHelper.set(object, field, jsonObject.get(field.getName()));
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			return object;
			
		} catch (Exception e) {
			throw new JamalException("toObject", e);
		}
	}
	
	
	// toObjectList
	
	
	
	// toObjectList
	
	public static final <T> List<T> toObjectList(String json, Class<T> clazz) throws JamalException {
		
		try {
			
			if (StringHelper.isNullOrEmpty(json)) {
				return null;
			}
			
			ValidationHelper.notNull(clazz);
			
			return JSONHelper.toObjectList(new JSONArray(json), clazz);
			
		} catch (Exception e) {
			throw new JamalException("toObjectList", e);
		}
		
	}
	
	
	public static final <T> List<T> toObjectList(JSONArray jsonArray, Class<T> clazz) throws JamalException {
		
		try {
			
			if (jsonArray == null) {
				return null;
			}
			
			ValidationHelper.notNull(clazz);
			
			List<T> list = new ArrayList<T>();
			
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(JSONHelper.toObject(jsonArray.getJSONObject(i), clazz));
			}
			
			return list;
			
		} catch (Exception e) {
			throw new JamalException("toObjectList", e);
		}
		
	}
	
	
}
