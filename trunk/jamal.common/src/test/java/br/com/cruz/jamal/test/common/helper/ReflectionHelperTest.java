package br.com.cruz.jamal.test.common.helper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import org.junit.Assert;
import org.junit.Test;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.helper.ReflectionHelper;
import br.com.cruz.jamal.test.common.JamalTest;
import br.com.cruz.jamal.test.common.model.ObjectTestModel;

public class ReflectionHelperTest extends JamalTest {
	
	private static final long serialVersionUID = 4826772419885694662L;
	
	@Getter(AccessLevel.PRIVATE)
	@Setter(AccessLevel.PRIVATE)
	public int privateField;
	
	
	// get
	
	@SuppressWarnings("unchecked")
	@Test
	public void getStringTest() throws JamalException {
		
		ObjectTestModel<ObjectTestModel<String>> object = new ObjectTestModel<ObjectTestModel<String>>();
		
		Assert.assertEquals("Campo primitivo deve ser zero!", Integer.valueOf(0), ReflectionHelper.get(object, "primitiveTypeField", Integer.class));
		Assert.assertEquals("Campo complexo deve ser nulo!", null, ReflectionHelper.get(object, "complexTypeField", Object.class));
		Assert.assertEquals("Campo genérico deve ser nulo!", null, ReflectionHelper.get(object, "genericTypeField", ObjectTestModel.class));
		
		Assert.assertEquals("Campo array de tipo primitivo deve ser nulo!", null, ReflectionHelper.get(object, "arrayPrimitiveTypeField", Object.class));
		Assert.assertEquals("Campo array de tipo complexo deve ser nulo!", null, ReflectionHelper.get(object, "arrayComplexTypeField", Object.class));
		Assert.assertEquals("Campo array de tipo genérico deve ser nulo!", null, ReflectionHelper.get(object, "arrayGenericTypeField", Object.class));
		
		Assert.assertEquals("Campo lista de tipo complexo deve ser nulo!", null, ReflectionHelper.get(object, "listComplexTypeField", List.class));
		Assert.assertEquals("Campo lista de tipo genérico deve ser nulo!", null, ReflectionHelper.get(object, "listGenericTypeField", List.class));
		
		
		object.setPrimitiveTypeField(10);
		Assert.assertEquals("Campo primitivo deve ser " + object.getPrimitiveTypeField(), Integer.valueOf(object.getPrimitiveTypeField()), ReflectionHelper.get(object, "primitiveTypeField", Integer.class));
		
		object.setComplexTypeField(object);
		Assert.assertEquals("Campo primitivo deve ser " + object.getComplexTypeField(), object.getComplexTypeField(), ReflectionHelper.get(object, "complexTypeField", Object.class));
		
		object.setGenericTypeField(new ObjectTestModel<String>());
		Assert.assertEquals("Campo primitivo deve ser " + object.getGenericTypeField(), object.getGenericTypeField(), ReflectionHelper.get(object, "genericTypeField", ObjectTestModel.class));
		
		
		object.setArrayPrimitiveTypeField(new int[]{1, 2});
		Assert.assertEquals("Campo primitivo deve ser " + object.getArrayPrimitiveTypeField(), object.getArrayPrimitiveTypeField(), ReflectionHelper.get(object, "arrayPrimitiveTypeField", Object.class));
		
		object.setArrayComplexTypeField(new Object[]{object, object.getComplexTypeField(), object.getGenericTypeField()});
		Assert.assertEquals("Campo primitivo deve ser " + object.getArrayComplexTypeField(), object.getArrayComplexTypeField(), ReflectionHelper.get(object, "arrayComplexTypeField", Object.class));
		
		object.setArrayGenericTypeField(new ObjectTestModel[]{object.getGenericTypeField()});
		Assert.assertEquals("Campo primitivo deve ser " + object.getArrayGenericTypeField(), object.getArrayGenericTypeField(), ReflectionHelper.get(object, "arrayGenericTypeField", Object.class));
		
		
		object.setListComplexTypeField(new ArrayList<Object>());
		object.getListComplexTypeField().add(object);
		object.getListComplexTypeField().add(object.getComplexTypeField());
		object.getListComplexTypeField().add(object.getGenericTypeField());
		Assert.assertEquals("Campo primitivo deve ser " + object.getListComplexTypeField(), object.getListComplexTypeField(), ReflectionHelper.get(object, "listComplexTypeField", List.class));
		
		
		object.setListGenericTypeField(new ArrayList<ObjectTestModel<String>>());
		object.getListGenericTypeField().add(object.getGenericTypeField());
		Assert.assertEquals("Campo primitivo deve ser " + object.getListGenericTypeField(), object.getListGenericTypeField(), ReflectionHelper.get(object, "listGenericTypeField", List.class));
		
		
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void getFieldTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		ObjectTestModel<ObjectTestModel<String>> object = new ObjectTestModel<ObjectTestModel<String>>();
		
		Class<ObjectTestModel<String>> clazz = (Class<ObjectTestModel<String>>) object.getClass();
		
		Assert.assertEquals("Campo primitivo deve ser zero!", Integer.valueOf(0), ReflectionHelper.get(object, clazz.getField("primitiveTypeField"), Integer.class));
		Assert.assertEquals("Campo complexo deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("complexTypeField"), Object.class));
		Assert.assertEquals("Campo genérico deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("genericTypeField"), ObjectTestModel.class));
		
		Assert.assertEquals("Campo array de tipo primitivo deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("arrayPrimitiveTypeField"), Object.class));
		Assert.assertEquals("Campo array de tipo complexo deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("arrayComplexTypeField"), Object.class));
		Assert.assertEquals("Campo array de tipo genérico deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("arrayGenericTypeField"), Object.class));
		
		Assert.assertEquals("Campo lista de tipo complexo deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("listComplexTypeField"), List.class));
		Assert.assertEquals("Campo lista de tipo genérico deve ser nulo!", null, ReflectionHelper.get(object, clazz.getField("listGenericTypeField"), List.class));

		
		object.setPrimitiveTypeField(10);
		Assert.assertEquals("Campo primitivo deve ser " + object.getPrimitiveTypeField(), Integer.valueOf(object.getPrimitiveTypeField()), ReflectionHelper.get(object, clazz.getField("primitiveTypeField"), Integer.class));
		
		object.setComplexTypeField(object);
		Assert.assertEquals("Campo primitivo deve ser " + object.getComplexTypeField(), object.getComplexTypeField(), ReflectionHelper.get(object, clazz.getField("complexTypeField"), Object.class));
		
		object.setGenericTypeField(new ObjectTestModel<String>());
		Assert.assertEquals("Campo primitivo deve ser " + object.getGenericTypeField(), object.getGenericTypeField(), ReflectionHelper.get(object, clazz.getField("genericTypeField"), ObjectTestModel.class));
		
		
		object.setArrayPrimitiveTypeField(new int[]{1, 2});
		Assert.assertEquals("Campo primitivo deve ser " + object.getArrayPrimitiveTypeField(), object.getArrayPrimitiveTypeField(), ReflectionHelper.get(object, clazz.getField("arrayPrimitiveTypeField"), Object.class));
		
		object.setArrayComplexTypeField(new Object[]{object, object.getComplexTypeField(), object.getGenericTypeField()});
		Assert.assertEquals("Campo primitivo deve ser " + object.getArrayComplexTypeField(), object.getArrayComplexTypeField(), ReflectionHelper.get(object, clazz.getField("arrayComplexTypeField"), Object.class));
		
		object.setArrayGenericTypeField(new ObjectTestModel[]{object.getGenericTypeField()});
		Assert.assertEquals("Campo primitivo deve ser " + object.getArrayGenericTypeField(), object.getArrayGenericTypeField(), ReflectionHelper.get(object, clazz.getField("arrayGenericTypeField"), Object.class));
		
		
		object.setListComplexTypeField(new ArrayList<Object>());
		object.getListComplexTypeField().add(object);
		object.getListComplexTypeField().add(object.getComplexTypeField());
		object.getListComplexTypeField().add(object.getGenericTypeField());
		Assert.assertEquals("Campo primitivo deve ser " + object.getListComplexTypeField(), object.getListComplexTypeField(), ReflectionHelper.get(object, clazz.getField("listComplexTypeField"), List.class));
		
		
		object.setListGenericTypeField(new ArrayList<ObjectTestModel<String>>());
		object.getListGenericTypeField().add(object.getGenericTypeField());
		Assert.assertEquals("Campo primitivo deve ser " + object.getListGenericTypeField(), object.getListGenericTypeField(), ReflectionHelper.get(object, clazz.getField("listGenericTypeField"), List.class));
		
		
	}
	
	
	// set
	
	@SuppressWarnings("unchecked")
	@Test
	public void setStringTest() throws JamalException {
		
		ObjectTestModel<ObjectTestModel<String>> object = new ObjectTestModel<ObjectTestModel<String>>();
		
		int primitiveTypeField = 10;
		ReflectionHelper.set(object, "primitiveTypeField", primitiveTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + primitiveTypeField, primitiveTypeField, object.getPrimitiveTypeField());
		
		Object complexTypeField = object;
		ReflectionHelper.set(object, "complexTypeField", complexTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + complexTypeField, complexTypeField, object.getComplexTypeField());
		
		ObjectTestModel<String> genericTypeField = new ObjectTestModel<String>();
		ReflectionHelper.set(object, "genericTypeField", genericTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + genericTypeField, genericTypeField, object.getGenericTypeField());
		
		int[] arrayPrimitiveTypeField = new int[]{1, 2};
		ReflectionHelper.set(object, "arrayPrimitiveTypeField", arrayPrimitiveTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + arrayPrimitiveTypeField, arrayPrimitiveTypeField, object.getArrayPrimitiveTypeField());
		
		Object[] arrayComplexTypeField = new Object[]{object, object.getComplexTypeField(), object.getGenericTypeField()};
		ReflectionHelper.set(object, "arrayComplexTypeField", arrayComplexTypeField);
		Assert.assertArrayEquals("Campo primitivo deve ser " + arrayComplexTypeField, arrayComplexTypeField, object.getArrayComplexTypeField());
		
		ObjectTestModel<String>[] arrayGenericTypeField = new ObjectTestModel[]{object.getGenericTypeField()};
		ReflectionHelper.set(object, "arrayGenericTypeField", arrayGenericTypeField);
		Assert.assertArrayEquals("Campo primitivo deve ser " + arrayGenericTypeField, arrayGenericTypeField, object.getArrayGenericTypeField());
		
		ArrayList<Object> listComplexTypeField = new ArrayList<Object>();
		listComplexTypeField.add(object);
		listComplexTypeField.add(object.getComplexTypeField());
		listComplexTypeField.add(object.getGenericTypeField());
		ReflectionHelper.set(object, "listComplexTypeField", listComplexTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + listComplexTypeField, listComplexTypeField, object.getListComplexTypeField());
		
		ArrayList<ObjectTestModel<String>> listGenericTypeField = new ArrayList<ObjectTestModel<String>>();
		listGenericTypeField.add(object.getGenericTypeField());
		ReflectionHelper.set(object, "listGenericTypeField", listGenericTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + listGenericTypeField, listGenericTypeField, object.getListGenericTypeField());
		
	}	
	
	@SuppressWarnings("unchecked")
	@Test
	public void setFieldTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		ObjectTestModel<ObjectTestModel<String>> object = new ObjectTestModel<ObjectTestModel<String>>();
		
		Class<ObjectTestModel<String>> clazz = (Class<ObjectTestModel<String>>) object.getClass();
		
		int primitiveTypeField = 10;
		ReflectionHelper.set(object, clazz.getField("primitiveTypeField"), primitiveTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + primitiveTypeField, primitiveTypeField, object.getPrimitiveTypeField());
		
		Object complexTypeField = object;
		ReflectionHelper.set(object, clazz.getField("complexTypeField"), complexTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + complexTypeField, complexTypeField, object.getComplexTypeField());
		
		ObjectTestModel<String> genericTypeField = new ObjectTestModel<String>();
		ReflectionHelper.set(object, clazz.getField("genericTypeField"), genericTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + genericTypeField, genericTypeField, object.getGenericTypeField());
		
		int[] arrayPrimitiveTypeField = new int[]{1, 2};
		ReflectionHelper.set(object, clazz.getField("arrayPrimitiveTypeField"), arrayPrimitiveTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + arrayPrimitiveTypeField, arrayPrimitiveTypeField, object.getArrayPrimitiveTypeField());
		
		Object[] arrayComplexTypeField = new Object[]{object, object.getComplexTypeField(), object.getGenericTypeField()};
		ReflectionHelper.set(object, clazz.getField("arrayComplexTypeField"), arrayComplexTypeField);
		Assert.assertArrayEquals("Campo primitivo deve ser " + arrayComplexTypeField, arrayComplexTypeField, object.getArrayComplexTypeField());
		
		ObjectTestModel<String>[] arrayGenericTypeField = new ObjectTestModel[]{object.getGenericTypeField()};
		ReflectionHelper.set(object, clazz.getField("arrayGenericTypeField"), arrayGenericTypeField);
		Assert.assertArrayEquals("Campo primitivo deve ser " + arrayGenericTypeField, arrayGenericTypeField, object.getArrayGenericTypeField());
		
		ArrayList<Object> listComplexTypeField = new ArrayList<Object>();
		listComplexTypeField.add(object);
		listComplexTypeField.add(object.getComplexTypeField());
		listComplexTypeField.add(object.getGenericTypeField());
		ReflectionHelper.set(object, clazz.getField("listComplexTypeField"), listComplexTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + listComplexTypeField, listComplexTypeField, object.getListComplexTypeField());
		
		ArrayList<ObjectTestModel<String>> listGenericTypeField = new ArrayList<ObjectTestModel<String>>();
		listGenericTypeField.add(object.getGenericTypeField());
		ReflectionHelper.set(object, clazz.getField("listGenericTypeField"), listGenericTypeField);
		Assert.assertEquals("Campo primitivo deve ser " + listGenericTypeField, listGenericTypeField, object.getListGenericTypeField());
		
	}

	
	// executeMethod
	
	@Test
	public void executeMethodStringTest() throws JamalException {
		
		ObjectTestModel<String> object = new ObjectTestModel<>();
		
		Integer complexTypeField = 10;
		ReflectionHelper.executeMethod(object, "setComplexTypeField", complexTypeField);
		Assert.assertEquals("A seguinte expressão deve ser verdadeira: " + complexTypeField + " == " + object.getComplexTypeField(), complexTypeField, ReflectionHelper.executeMethod(object, "getComplexTypeField"));
		
	}
	
	@Test
	public void executeMethodMethodTest() throws JamalException, NoSuchMethodException, SecurityException {
		
		ObjectTestModel<String> object = new ObjectTestModel<>();
		
		@SuppressWarnings("unchecked")
		Class<ObjectTestModel<String>> clazz = (Class<ObjectTestModel<String>>) object.getClass();
		
		Integer complexTypeField = 10;
		ReflectionHelper.executeMethod(object, clazz.getMethod("setComplexTypeField", Object.class), complexTypeField);
		Assert.assertEquals("A seguinte expressão deve ser verdadeira: " + complexTypeField + " == " + object.getComplexTypeField(), complexTypeField, ReflectionHelper.executeMethod(object, clazz.getMethod("getComplexTypeField")));
		
	}

	
	// getPublicFieldList
	
	@Test
	public void getPublicFieldListTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		ObjectTestModel<String> object = new ObjectTestModel<String>();
		
		List<Field> publicFieldList = ReflectionHelper.getPublicFieldList(object.getClass());
		for (Field publicField : publicFieldList) {
			Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		} 
		
	}
	
	
	// getPublicField
	
	@Test
	public void getPublicFieldTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		ObjectTestModel<String> object = new ObjectTestModel<String>();
		
		Field publicField = null;
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "primitiveTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "complexTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "genericTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "arrayPrimitiveTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "arrayComplexTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "arrayGenericTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "listComplexTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
		publicField = ReflectionHelper.getPublicField(object.getClass(), "listGenericTypeField");
		Assert.assertEquals("O campo " + publicField.getName() + " não existe na classe ObjectTestModel.java", object.getClass().getField(publicField.getName()), publicField);
		
	}
	
	
	// isPublicField
	
	@Test
	public void isPublicFieldStringTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		Assert.assertEquals("O campo primitiveTypeField é publico!", true, ReflectionHelper.isPublicField(ObjectTestModel.class, "primitiveTypeField"));
		
		Assert.assertEquals("O campo privateField não é publico!", false, ReflectionHelper.isPublicField(ReflectionHelperTest.class, "privateField"));
		
	}
	
	@Test
	public void isPublicFieldFieldTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		Assert.assertEquals("O campo primitiveTypeField é publico!", true, ReflectionHelper.isPublicField(ObjectTestModel.class, ObjectTestModel.class.getField("primitiveTypeField")));
		
		Assert.assertEquals("O campo privateField não é publico!", false, ReflectionHelper.isPublicField(ReflectionHelperTest.class, ReflectionHelperTest.class.getField("privateField")));
		
	}

	
	// getGetterMethodName
	
	@Test
	public void getGetterMethodNameStringTest() throws JamalException {
		
		Assert.assertEquals("O nome do método  deve ser getPrimitiveTypeField", "getPrimitiveTypeField", ReflectionHelper.getGetterMethodName("primitiveTypeField"));
		
	}
	
	@Test
	public void getGetterMethodNameFieldTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		Assert.assertEquals("O nome do método  deve ser getPrimitiveTypeField", "getPrimitiveTypeField", ReflectionHelper.getGetterMethodName(ObjectTestModel.class.getField("primitiveTypeField")));
		
	}

	
	// getGetterMethod
	
	@Test
	public void getGetterMethodStringTest() throws JamalException, NoSuchMethodException, SecurityException {
		
		Assert.assertEquals("O método getPrimitiveTypeField não foi encontrado", ObjectTestModel.class.getMethod("getPrimitiveTypeField"), ReflectionHelper.getGetterMethod(ObjectTestModel.class, "primitiveTypeField"));
		
	}
	
	@Test
	public void getGetterMethodFieldTest() throws JamalException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		
		Assert.assertEquals("O método getPrimitiveTypeField não foi encontrado", ObjectTestModel.class.getMethod("getPrimitiveTypeField"), ReflectionHelper.getGetterMethod(ObjectTestModel.class, ObjectTestModel.class.getField("primitiveTypeField")));
		
	}

	
	// getSetterMethodName
	
	@Test
	public void getSetterMethodNameStringTest() throws JamalException {
		
		Assert.assertEquals("O nome do método  deve ser setPrimitiveTypeField", "setPrimitiveTypeField", ReflectionHelper.getSetterMethodName("primitiveTypeField"));
		
	}
	
	@Test
	public void getSetterMethodNameFieldTest() throws JamalException, NoSuchFieldException, SecurityException {
		
		Assert.assertEquals("O nome do método  deve ser setPrimitiveTypeField", "setPrimitiveTypeField", ReflectionHelper.getSetterMethodName(ObjectTestModel.class.getField("primitiveTypeField")));
		
	}

	
	// getSetterMethod
	
	@Test
	public void getSetterMethodStringTest() throws JamalException, NoSuchMethodException, SecurityException {
		
		Assert.assertEquals("O método setPrimitiveTypeField não foi encontrado", ObjectTestModel.class.getMethod("setPrimitiveTypeField", int.class), ReflectionHelper.getSetterMethod(ObjectTestModel.class, "primitiveTypeField"));
		
	}
	
	@Test
	public void getSetterMethodFieldTest() throws JamalException, NoSuchFieldException, SecurityException, NoSuchMethodException {
		
		Assert.assertEquals("O método setPrimitiveTypeField não foi encontrado", ObjectTestModel.class.getMethod("setPrimitiveTypeField", int.class), ReflectionHelper.getSetterMethod(ObjectTestModel.class, ObjectTestModel.class.getField("primitiveTypeField")));
		
	}

	
	// findMethod
	
	@Test
	public void findMethodTest() throws JamalException {
		
		Assert.assertEquals("Método getPrimitiveTypeField não encontrado", ReflectionHelper.getGetterMethod(ObjectTestModel.class, "primitiveTypeField"), ReflectionHelper.findMethod(ObjectTestModel.class, "getPrimitiveTypeField"));
		
		Assert.assertEquals("Método setPrimitiveTypeField não encontrado", ReflectionHelper.getSetterMethod(ObjectTestModel.class, "primitiveTypeField"), ReflectionHelper.findMethod(ObjectTestModel.class, "setPrimitiveTypeField", int.class));
		
	}

	
	// findMethodList
	
	@Test
	public void findMethodListTest() throws JamalException, NoSuchMethodException, SecurityException {
		
		List<Method> methodList = new ArrayList<Method>();
		methodList.add(ObjectTestModel.class.getMethod("method"));
		methodList.add(ObjectTestModel.class.getMethod("method", Object.class));
		methodList.add(ObjectTestModel.class.getMethod("method", Object.class, Object.class));
		
		List<Method> foundMethodList = ReflectionHelper.findMethodList(ObjectTestModel.class, "method");
		
		boolean isMethodNotFound =
			methodList == null ||
			foundMethodList == null ||
			methodList.size() != foundMethodList.size();
		
		if (!isMethodNotFound) {
			for (int i = 0; i < methodList.size(); i++) {
				for (int j = 0; j < foundMethodList.size(); j++) {
					if (foundMethodList.get(j).equals(methodList.get(i))) {
						isMethodNotFound = false;
						break;
					}
					isMethodNotFound = true;
				}
				if (isMethodNotFound) {
					break;
				}
			}
		}
		
		if (!isMethodNotFound) {
			
			methodList = new ArrayList<Method>();
			methodList.add(ObjectTestModel.class.getMethod("method", Object.class));
			
			foundMethodList = ReflectionHelper.findMethodList(ObjectTestModel.class, "method", Object.class);
			
			isMethodNotFound =
				methodList == null ||
				foundMethodList == null ||
				methodList.size() != foundMethodList.size();
			
			if (!isMethodNotFound) {
				for (int i = 0; i < methodList.size(); i++) {
					for (int j = 0; j < foundMethodList.size(); j++) {
						if (foundMethodList.get(j).equals(methodList.get(i))) {
							isMethodNotFound = false;
							break;
						}
						isMethodNotFound = true;
					}
					if (isMethodNotFound) {
						break;
					}
				}
			}
		}
		
		Assert.assertEquals("Não foram encontrados todos os métodos buscados!", false, isMethodNotFound); 
	}
	
}
