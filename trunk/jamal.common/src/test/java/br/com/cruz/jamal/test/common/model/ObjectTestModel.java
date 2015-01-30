package br.com.cruz.jamal.test.common.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.cruz.jamal.common.annotation.PublicField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ObjectTestModel<T> extends JamalTestModel {

	private static final long serialVersionUID = 4943699594971497253L;

	@Getter
	@Setter
	public int primitiveTypeField;

	@PublicField
	@Getter
	@Setter
	public Object complexTypeField;
	
	@PublicField
	@Getter
	@Setter
	public T genericTypeField;

	@PublicField
	@Getter
	@Setter
	public int[] arrayPrimitiveTypeField;

	@PublicField
	@Getter
	@Setter
	public Object[] arrayComplexTypeField;

	@PublicField
	@Getter
	@Setter
	public T[] arrayGenericTypeField;

	@PublicField
	@Getter
	@Setter
	public List<Object> listComplexTypeField;

	@PublicField
	@Getter
	@Setter
	public List<T> listGenericTypeField;
	
	
	
	public void method() {
		System.out.println("method()");
	}
	
	public void method(Object parameter1) {
		System.out.println("method(Object parameter1)");
	}
	
	public void method(Object parameter1, Object parameter2) {
		System.out.println("method(Object parameter1, Object parameter2)");
	}

	public final static ObjectTestModel<Calendar> create() {
		
		ObjectTestModel<Calendar> object = new ObjectTestModel<Calendar>();
		
		object.setPrimitiveTypeField(1);
		object.setComplexTypeField(object);
		object.setGenericTypeField(Calendar.getInstance());
		
		object.setArrayPrimitiveTypeField(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
		object.setArrayComplexTypeField(new Object[]{object, object, object, object, object, "teste 6", "teste 7", "teste 8", "teste 9", "teste 10"});
		object.setArrayGenericTypeField(new Calendar[]{Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance(), Calendar.getInstance()});
		
		object.setListComplexTypeField(new ArrayList<Object>());
		object.getListComplexTypeField().add("teste 1");
		object.getListComplexTypeField().add("teste 2");
		object.getListComplexTypeField().add("teste 3");
		object.getListComplexTypeField().add("teste 4");
		object.getListComplexTypeField().add("teste 5");
		object.getListComplexTypeField().add(object);
		object.getListComplexTypeField().add(object);
		object.getListComplexTypeField().add(object);
		object.getListComplexTypeField().add(object);
		object.getListComplexTypeField().add(object);
		
		object.setListGenericTypeField(new ArrayList<Calendar>());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		object.getListGenericTypeField().add(Calendar.getInstance());
		
		return object;
	}
	
	public final static List<ObjectTestModel<Calendar>> createList() {
		
		List<ObjectTestModel<Calendar>> list = new ArrayList<ObjectTestModel<Calendar>>();
		for (int i = 0; i < 10; i++) {
			list.add(ObjectTestModel.create());
		}
		return list;
		
	}
	
}