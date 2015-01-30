package br.com.cruz.jamal.test.common.helper;

import java.util.Calendar;

import org.junit.Test;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.helper.JSONHelper;
import br.com.cruz.jamal.common.helper.JamalHelper;
import br.com.cruz.jamal.test.common.model.ObjectTestModel;

public class JSONHelperTest extends JamalHelper {
	
	private static final long serialVersionUID = 2806607387892223720L;
	
	
	// toJSONStringTest
	
	@Test
	public void toJSONStringTest() throws JamalException {
		// testar parametro1.parametro2.etc
		System.out.println(JSONHelper.toJSONString("teste"));
		System.out.println(JSONHelper.toJSONString(1));
		System.out.println(JSONHelper.toJSONString(new Integer(1)));
		System.out.println(JSONHelper.toJSONString(Calendar.getInstance()));
		System.out.println(JSONHelper.toJSONString(ObjectTestModel.create()));
		System.out.println(JSONHelper.toJSONString(ObjectTestModel.createList()));
	}
	
	
	// toJSONObjectTest
	
	@Test
	public void toJSONObjectTest() throws JamalException {
		
	}
	
	
	// toJSONArrayTest
	
	@Test
	public void toJSONArrayTest() throws JamalException {
		
	}
	
	
	// toObjectTest
	
	@Test
	public void toObjectStringTest() throws JamalException {
		
	}
	
	@Test
	public void toObjectJSONObjectTest() throws JamalException {
		
	}
	
	
	// toObjectListTest
	
	@Test
	public void toObjectListStringTest() throws JamalException {
		
	}
	
	@Test
	public void toObjectListJSONArrayTest() throws JamalException {
		
	}
}