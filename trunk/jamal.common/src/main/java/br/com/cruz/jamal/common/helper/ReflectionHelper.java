package br.com.cruz.jamal.common.helper;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;
import br.com.cruz.jamal.common.to.FieldMapperTO;

public class ReflectionHelper extends JamalHelper {

	private static final long serialVersionUID = 4184413189222960213L;
	
	
	// newInstance
	
	public static final <T> T newInstance(Class<T> clazz) throws JamalException {
		
		try {
			
			return clazz.newInstance();
			
		} catch (Exception e) {
			throw new JamalException(e);
		}
		
	}
	
	
	// get
	
	public static final <K, V> V get(K object, String fieldName, Class<V> fieldType) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(fieldName);
			ValidationHelper.notNull(fieldType);
			
			Field field = ReflectionHelper.getPublicField(object.getClass(), true, fieldName);
			
			if (field == null) {
				return null;
			}
			
			return ReflectionHelper.get(object, field, fieldType);
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static final <K, V> V get(K object, Field field, Class<V> fieldType) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(field);
			ValidationHelper.notNull(fieldType);
			
			return (V) ReflectionHelper.executeMethod(object, ReflectionHelper.getGetterMethod(object.getClass(), field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
		
	}
	
	
	// set
	
	public static final <K, V> void set(K object, String fieldName, V fieldValue) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(fieldName);
			
			Field field = ReflectionHelper.getPublicField(object.getClass(), true, fieldName);
			
			if (field == null) {
				return;
			}
			
			ReflectionHelper.set(object, field, fieldValue);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
		
	}
	
	public static final <K, V> void set(K object, Field field, V fieldValue) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(field);
			ValidationHelper.notNull(fieldValue);
			
			ReflectionHelper.executeMethod(object, ReflectionHelper.getSetterMethod(object.getClass(), field), fieldValue);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
		
	}
	
	
	// executeMethod
	
	public static final Object executeMethod(Object object, String methodName, Object... parameterArgumentArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(methodName);
			
			Class<?>[] parameterTypeArray = null;
			
			if (!CollectionHelper.isNullOrEmpty(parameterArgumentArray)) {
				
				parameterTypeArray = new Class<?>[parameterArgumentArray.length];
				
				for (int i = 0; i < parameterArgumentArray.length; i++) {
					
					parameterTypeArray[i] =
						parameterArgumentArray[i] != null ?
							parameterArgumentArray[i].getClass() :
							null;
					
				}
				
			}
			
			return ReflectionHelper.executeMethod(object, ReflectionHelper.findMethod(object.getClass(), methodName, parameterTypeArray), parameterArgumentArray);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeMethod", e);
		}

	}
	
	public static final Object executeMethod(Object object, Method method, Object... parameterArgumentArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(object);
			ValidationHelper.notNull(method);
			
			return method.invoke(object, parameterArgumentArray);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("executeMethod", e);
		}

	}
	
	
	// getPublicFieldList
	
	public static final List<Field> getPublicFieldList(Class<?> clazz, String... fieldNameArray) throws JamalException {
		
		try {
			return ReflectionHelper.getPublicFieldList(clazz, true, fieldNameArray);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
		
	}
	
	public static final List<Field> getPublicFieldList(Class<?> clazz, boolean isAddExtendedFieldList, String... fieldNameArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			
			FieldMapperTO defaultPublicFieldMapper =
				FieldMapperHelper.getDefaultPublicFieldMapper(clazz);
			
			ValidationHelper.notNull(defaultPublicFieldMapper);
			
			
			FieldMapperTO fieldMapper =
				new FieldMapperTO(clazz, defaultPublicFieldMapper.getFieldList());
			
			ValidationHelper.notNull(fieldMapper);
			
			
			if (isAddExtendedFieldList) {
				
				FieldMapperTO extendedPublicFieldMapper =
					FieldMapperHelper.getExtendedPublicFieldMapper(clazz);
					
				ValidationHelper.notNull(extendedPublicFieldMapper);
				
				
				fieldMapper.getFieldList().addAll(extendedPublicFieldMapper.getFieldList());
			}
			
			
			if (CollectionHelper.isNullOrEmpty(fieldNameArray)) {
				return fieldMapper.getFieldList();
			}
			
			
			List<String> fieldNameList = new ArrayList<String>();
			for (String fieldName : fieldNameArray) {
				if (StringHelper.isNullOrEmpty(fieldName)) {
					continue;
				}
				if (fieldName.contains(".")) {
					fieldNameList.add(fieldName.substring(0, fieldName.indexOf('.')));
				}
				fieldNameList.add(fieldName);
			}
			
			
			List<Field> publicFieldList = new ArrayList<Field>();
			
			for (Field publicField : fieldMapper.getFieldList()) {
				
				boolean isAddFieldOnList = false;
				
				Iterator<String> iterator = fieldNameList.iterator();
				while (iterator.hasNext()) {
					
					if (!publicField.getName().equals(iterator.next())) {
						continue;
					}
					
					isAddFieldOnList = true;
					iterator.remove();
					break;
				}
				
				if (!isAddFieldOnList) {
					continue;
				}
				
				publicFieldList.add(publicField);
				
				if (CollectionHelper.isNullOrEmpty(fieldNameList)) {
					break;
				}
			}
			
			return publicFieldList;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
		
	}
	
	
	// getPublicField
	
	public static final Field getPublicField(Class<?> clazz, String fieldName) throws JamalException {
		
		try {
			return ReflectionHelper.getPublicField(clazz, true, fieldName);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicField", e);
		}
		
	}
	
	public static final Field getPublicField(Class<?> clazz, boolean isAddDefaultField, String fieldName) throws JamalException {
		 
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			List<Field> publicFieldList = ReflectionHelper.getPublicFieldList(clazz, isAddDefaultField, fieldName);
			
			if (CollectionHelper.isNullOrEmpty(publicFieldList)) {
				return null;
			}
			
			return publicFieldList.get(0);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicField", e);
		}
		
	}
	
	
	// isPublicField
	
	public static final boolean isPublicField(Class<?> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.isPublicField(clazz, field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPublicField", e);
		}
	}
	
	public static final boolean isPublicField(Class<?> clazz, String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			return ReflectionHelper.getPublicField(clazz, true, fieldName) != null;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPublicField", e);
		}
	}
	
	
	// getGetterMethodName
	
	public static final String getGetterMethodName(Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.getGetterMethodName(field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethodName", e);
		}
	}
	
	public static final String getGetterMethodName(String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(fieldName);
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			return String.format("get%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethodName", e);
		}
	}
	
	
	// getGetterMethod
	
	public static final Method getGetterMethod(Class<?> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getGetterMethodName(field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethod", e);
		}
	}
	
	public static final Method getGetterMethod(Class<?> clazz, String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getGetterMethodName(fieldName));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getGetterMethod", e);
		}
	}
	
	
	// getSetterMethodName
	
	public static final String getSetterMethodName(Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.getSetterMethodName(field.getName());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethodName", e);
		}
	}
	
	public static final String getSetterMethodName(String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(fieldName);
			
			if (StringHelper.isNullOrEmpty(fieldName)) {
				return null;
			}
			
			return String.format("set%s", fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length()));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethodName", e);
		}
	}
	
	
	// getSetterMethod
	
	public static final Method getSetterMethod(Class<?> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getSetterMethodName(field));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethod", e);
		}
		
	}
	
	public static final Method getSetterMethod(Class<?> clazz, String fieldName) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(fieldName);
			
			return ReflectionHelper.findMethod(clazz, ReflectionHelper.getSetterMethodName(fieldName));
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getSetterMethod", e);
		}
		
	}
	
	
	// findMethod
	
	public static final Method findMethod(Class<?> clazz, String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(methodName);
			
			List<Method> foundMethodList = ReflectionHelper.doFindMethodList(clazz, 1, methodName, parameterTypeArray);
			
			if (CollectionHelper.isNullOrEmpty(foundMethodList)) {
				return null;
			}
			
			return foundMethodList.get(0);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethod", e);
		}
		
	}
	

	// findMethodList
	
	public static final List<Method> findMethodList(Class<?> clazz, String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(methodName);
		
			return ReflectionHelper.doFindMethodList(clazz, -1, methodName, parameterTypeArray);
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethodList", e);
		}
		
	}
	
	
	// doFindMethodList
	
	private static final List<Method> doFindMethodList(Class<?> clazz, int resultSize, String methodName, Class<?>... parameterTypeArray) throws JamalException {
		
		List<Method> foundMethodList = new ArrayList<Method>();
		
		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(methodName);
			
			boolean isSearchByParameterType = !CollectionHelper.isNullOrEmpty(parameterTypeArray);
			
			for (Method method : clazz.getMethods()) {
				
				boolean isFindMethod = false;
				
				if (resultSize < 0) {
					isFindMethod = true;
				} else {
					isFindMethod = foundMethodList.size() < resultSize;
				}
				
				if (!isFindMethod) {
					return foundMethodList;
				}
				
				if (methodName.equals(method.getName())) {
					
					if (!isSearchByParameterType) {
						foundMethodList.add(method);
						continue;
					}
					
					if (method.getParameterTypes().length != parameterTypeArray.length) {
						continue;
					}
					
					for (int i = 0; i < method.getParameterTypes().length; i++) {
						if (parameterTypeArray[i] != method.getParameterTypes()[i]) {
							continue;
						}
					}
					
					foundMethodList.add(method);
					
				}
				
			}
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("findMethodList", e);
		}
		
		return foundMethodList;
		
	}
	
	
	// isPrimitive
	
	public static final boolean isPrimitive(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				clazz.isPrimitive();
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPrimitive", e);
		}
		
	}
	
	
	// isPrimitiveOrWrapper
	
	public static final boolean isPrimitiveOrWrapper(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				ReflectionHelper.isPrimitive(clazz) || ClassUtils.isPrimitiveOrWrapper(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isPrimitiveOrWrapper", e);
		}
		
	}
	
	
	// isEnum
	
	public static final boolean isEnum(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				clazz.isEnum();
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isEnum", e);
		}
		
	}
	
	// isDate
	
	public static final boolean isDate(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				Date.class.isAssignableFrom(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isDate", e);
		}
		
	}
	
	
	// isCalendar
	
	public static final boolean isCalendar(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
					Calendar.class.isAssignableFrom(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isDate", e);
		}
		
	}
	
	
	// isDateOrCalendar
	
	public static final boolean isDateOrCalendar(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				ReflectionHelper.isDate(clazz) ||
				ReflectionHelper.isCalendar(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isDate", e);
		}
		
	}
	
	
	// isString
	
	public static final boolean isString(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				clazz.equals(String.class);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isString", e);
		}
	}
	
	
	// isArray
	
	public static final boolean isArray(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				clazz.isArray();
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isArray", e);
		}
	}
	
	
	// isCollection
	
	public static final boolean isCollection(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				Collection.class.isAssignableFrom(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isCollection", e);
		}
	}
	
	
	// isArrayOrCollection
	
	public static final boolean isArrayOrCollection(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			return
				ReflectionHelper.isArray(clazz) ||
				ReflectionHelper.isCollection(clazz);
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isArrayOrCollection", e);
		}
	}
	
	
	// isKnown
	
	public static final boolean isKnownType(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			boolean isKnownType =
				ReflectionHelper.isPrimitiveOrWrapper(clazz) ||
				ReflectionHelper.isEnum(clazz) ||
				ReflectionHelper.isDateOrCalendar(clazz) ||
				ReflectionHelper.isString(clazz);
			
			if (isKnownType) {
				return true;
			}
			
			if (ReflectionHelper.isArray(clazz)) {
				return ReflectionHelper.isKnownType(clazz.getComponentType());
			}
			
			if (ReflectionHelper.isCollection(clazz)) {
				return false;
			}
			
			return false;
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isKnownType", e);
		}
			
	}
	
	// convertToArray
	
	public static final Object[] convertToArray(Object arrayOrCollection) throws JamalException {
		
		if (arrayOrCollection == null) {
			return null;
		}
		
		if (!ReflectionHelper.isArrayOrCollection(arrayOrCollection.getClass())) {
			return null;
		}
		
		Object[] array = null;
		
		if (ReflectionHelper.isArray(arrayOrCollection.getClass())) {
			array = ReflectionHelper.toArray(arrayOrCollection);
		} else {
			Collection<?> collection = ReflectionHelper.toCollection(arrayOrCollection);
			if (collection != null) {
				array = collection.toArray();
			}
		}
		
		return array;
		
	}
	
	
	
	// toArray
	
	@SuppressWarnings("unchecked")
	public static final <T> T[] toArray(T array) throws JamalException {
		
		try {
			
			if (array == null) {
				return null;
			}
			
			int arrayLenth = Array.getLength(array);
			
			List<T> list = new ArrayList<T>();
			
			for (int i = 0; i < arrayLenth; i++) {
				list.add((T)Array.get(array, i));
			}
			
			return (T[]) list.toArray();
			
		} catch (IllegalArgumentException e) {
			throw new UnableToCompleteOperationException("toArray", e);
		}
		
	}
	
	
	// toCollection
	
	public static final Collection<?> toCollection(Object collection) throws JamalException {
		
		try {
			
			if (collection == null) {
				return null;
			}
			
			
			return (Collection<?>)collection;
			
		} catch (IllegalArgumentException e) {
			throw new UnableToCompleteOperationException("toCollection", e);
		}
		
	}

	
}
