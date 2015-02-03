package br.com.cruz.jamal.common.to;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;


public class FieldMapperTO extends JamalTO<FieldMapperTO> {

	private static final long serialVersionUID = -4619749426287104063L;
	
	// properties
	
	@Getter
	private Class<?> clazz;
	
	@Setter
	private List<Field> fieldList;
	
	public List<Field> getFieldList() {
		if (this.fieldList == null) {
			this.fieldList = new ArrayList<Field>();
		}
		return fieldList;
	}
	
	// constructors
	
	public FieldMapperTO(Class<?> clazz) throws JamalException {
		super();
		
		if (clazz == null) {
			throw new UnableToCompleteOperationException("newInstance", "clazzIsNull");
		}
		
		this.clazz = clazz;
	}
	
	public FieldMapperTO(Class<?> clazz, List<Field> publicFieldList) throws JamalException {
		this(clazz);
		this.setFieldList(publicFieldList);
	}
	
	public FieldMapperTO(Class<?> clazz, Field... publicFieldArray) throws JamalException {
		this(clazz, Arrays.asList(publicFieldArray));
	}
	
	
	// methods
	
	@Override
	public boolean equals(Object object) {
		
		if (!(object instanceof FieldMapperTO)) {
			return false;
		}

		return this.getClazz().equals(((FieldMapperTO)object).getClazz());
	}
	
	@Override
	public int hashCode() {
		return this.getClazz().hashCode();
	}
}
