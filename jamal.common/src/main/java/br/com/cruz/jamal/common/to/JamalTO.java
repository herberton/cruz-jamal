package br.com.cruz.jamal.common.to;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import lombok.NoArgsConstructor;
import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;
import br.com.cruz.jamal.common.helper.CollectionHelper;
import br.com.cruz.jamal.common.helper.JSONHelper;
import br.com.cruz.jamal.common.helper.ReflectionHelper;

@NoArgsConstructor
public class JamalTO<T extends JamalTO<T>> implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -6062467496837761026L;
	
	
	// getPublicFieldList
	
	public List<Field> getPublicFieldList() throws JamalException {
		try {
			return this.getPublicFieldList(false);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
	}
	
	public List<Field> getPublicFieldList(boolean isAddDefaultField) throws JamalException {
		try {
			return ReflectionHelper.getPublicFieldList(this.getClass(), isAddDefaultField);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldList", e);
		}
	}
	
	
	// get
	
	public final <V> V get(String fieldName, Class<V> fieldType) throws JamalException {
		try {
			return ReflectionHelper.get(this, fieldName, fieldType);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("get", e);
		}
	}
	
	
	// set
	
	public final <V> void set(String fieldName, V fieldValue) throws JamalException {
		try {
			ReflectionHelper.set(this, fieldName, fieldValue);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("set", e);
		}
	}
	
	
	// toString
	
	@Override
	public String toString() {
		try {
			return this.toString(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getClass().toString();
	}
	
	public String toString(String fieldName, String... fieldNameArray) {
		try {
			List<String> fieldNameList = CollectionHelper.newArrayList(fieldName, fieldNameArray);
			return JSONHelper.toJSONString(this, fieldNameList.toArray(new String[]{}));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.getClass().toString();
	}
	
	
	// equals
	
	@Override
	public boolean equals(Object object) {
		try {
			if (object == null) {
				return false;
			}
			if (!this.getClass().equals(object.getClass())) {
				return false;
			}
			return this.toString().equals(object.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	// hashcode
	
	@Override
	public int hashCode() {
		try {
			return this.toString().hashCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.hashCode();
	}
	
	
	// clone
	
	@Override
	public T clone() throws CloneNotSupportedException {
		try {
			return this.clone(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public T clone(String fieldName, String... fieldNameArray) throws CloneNotSupportedException {
		try {
			return (T) JamalTO.load(this.toString(fieldName, fieldNameArray), this.getClass());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// load
	
	public static <T> T load(String json, Class<T> clazz) throws JamalException {
		try {
			return JSONHelper.toObject(json, clazz);
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("load", e);
		}
	}
		
}
