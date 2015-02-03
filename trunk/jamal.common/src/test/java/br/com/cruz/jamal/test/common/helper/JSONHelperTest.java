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
		// TODO: Teste - JSONHelperTest.toJSONStringTest()
	}
	
	
	@Test
	public void toObjectTest() {
		// TODO: Teste - JSONHelperTest.toObjectTest()
	}
}