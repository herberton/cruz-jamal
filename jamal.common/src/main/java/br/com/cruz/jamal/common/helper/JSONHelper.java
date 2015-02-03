package br.com.cruz.jamal.common.helper;

import br.com.cruz.jamal.common.exception.JamalException;
import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;


public class JSONHelper extends JamalHelper {

	private static final long serialVersionUID = -2611604875090789180L;

	public static <T> String toJSONString(T object, String... fieldNameArray) throws JamalException {
		try {
			if (CollectionHelper.isNullOrEmpty(fieldNameArray)) {
				return new JSONSerializer().serialize(object);
			}
			return new JSONSerializer().include(fieldNameArray).serialize(object);
		} catch (Exception e) {
			throw new JamalException("toJSONString", e);
		}
	}
	
	public static <T> T toObject(String json, Class<T> clazz) throws JamalException {
		try {
			return new JSONDeserializer<T>().deserialize(json);
		} catch (Exception e) {
			throw new JamalException("toObject", e);
		}
	}
}
