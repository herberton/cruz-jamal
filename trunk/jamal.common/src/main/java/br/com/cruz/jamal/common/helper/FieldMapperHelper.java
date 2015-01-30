package br.com.cruz.jamal.common.helper;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Setter;
import br.com.cruz.jamal.common.annotation.PublicField;
import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;
import br.com.cruz.jamal.common.to.FieldMapperTO;

public class FieldMapperHelper extends JamalHelper {

	private static final long serialVersionUID = 6352433382165842596L;

	
	// defaultPublicFieldMapperList
	
	@Setter(AccessLevel.PRIVATE)
	private static Set<FieldMapperTO> defaultPublicFieldMapperList;
	
	public static Set<FieldMapperTO> getDefaultPublicFieldMapperList() {
		if (FieldMapperHelper.defaultPublicFieldMapperList == null) {
			FieldMapperHelper.setDefaultPublicFieldMapperList(new HashSet<FieldMapperTO>());
		}
		return FieldMapperHelper.defaultPublicFieldMapperList;
	}
	
	
	// extendedPublicFieldMapperList
	
	@Setter(AccessLevel.PRIVATE)
	private static Set<FieldMapperTO> extendedPublicFieldMapperList;
	
	public static Set<FieldMapperTO> getExtendedPublicFieldMapperList() {
		if (FieldMapperHelper.extendedPublicFieldMapperList == null) {
			FieldMapperHelper.setExtendedPublicFieldMapperList(new HashSet<FieldMapperTO>());
		}
		return FieldMapperHelper.extendedPublicFieldMapperList;
	}
	
	
	// getDefaultPublicFieldMapper
	
	public static FieldMapperTO getDefaultPublicFieldMapper(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			FieldMapperTO publicFieldMapperClazz = new FieldMapperTO(clazz);
			
			if (!FieldMapperHelper.getDefaultPublicFieldMapperList().contains(publicFieldMapperClazz)) {
				return FieldMapperHelper.loadFieldMapper(publicFieldMapperClazz);
			}
			
			for (FieldMapperTO publicFieldMapper : FieldMapperHelper.getDefaultPublicFieldMapperList()) {
				if (publicFieldMapper.equals(publicFieldMapperClazz)) {
					return publicFieldMapper;
				}
			}
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getDefaultPublicFieldMapper", e);
		}
		
		return null;
	}

	
	// getExtendedPublicFieldMapper
	
	public static FieldMapperTO getExtendedPublicFieldMapper(Class<?> clazz) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(clazz);
			
			FieldMapperTO extendedFieldMapper = new FieldMapperTO(clazz);
			
			if (!FieldMapperHelper.getExtendedPublicFieldMapperList().contains(extendedFieldMapper)) {
				return FieldMapperHelper.loadFieldMapper(extendedFieldMapper);
			}
			
			for (FieldMapperTO extendedPublicFieldMapper : FieldMapperHelper.getExtendedPublicFieldMapperList()) {
				if (extendedPublicFieldMapper.equals(extendedFieldMapper)) {
					return extendedPublicFieldMapper;
				}
			}
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getExtendedPublicFieldMapper", e);
		}
		
		return null;
	}

	
	// loadFieldMapper
	
	private static FieldMapperTO loadFieldMapper(FieldMapperTO fieldMapper) throws JamalException {
		
		try {
			
			ValidationHelper.notNull(fieldMapper);
			
			Class<?> clazz = fieldMapper.getClazz();
			
			ValidationHelper.notNull(clazz);
			
			FieldMapperTO extendedFieldMapper = new FieldMapperTO(clazz);
			
			do {
				
				for (Field field : clazz.getDeclaredFields()) {
					
					if (!FieldMapperHelper.isDefaultPublicField(clazz, field)) {
						
						if (!field.isAnnotationPresent(PublicField.class)) {
							continue;
						}
						
						extendedFieldMapper.getFieldList().add(field);
						continue;
					}
					
					fieldMapper.getFieldList().add(field);
				}
				
				clazz = clazz.getSuperclass();
				
			} while(clazz != null);
			
			FieldMapperHelper.getDefaultPublicFieldMapperList().add(fieldMapper);
			FieldMapperHelper.getExtendedPublicFieldMapperList().add(extendedFieldMapper);
			
			return fieldMapper;
		
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("getPublicFieldMapper", e);
		}
	}
	
	
	// isPublicField
	
	private static final boolean isDefaultPublicField(Class<?> clazz, Field field) throws JamalException {

		try {
			
			ValidationHelper.notNull(clazz);
			ValidationHelper.notNull(field);
			
			return 
				ReflectionHelper.getGetterMethod(clazz, field) != null &&
				ReflectionHelper.isKnownType(field.getType()) &&
				!ReflectionHelper.isArrayOrCollection(field.getType());
			
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("isDefaultPublicField", e);
		}
	}
}
