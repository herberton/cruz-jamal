package br.com.cruz.jamal.test.common.helper;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import br.com.cruz.jamal.common.helper.JSONHelper;
import br.com.cruz.jamal.common.helper.JamalHelper;

public class JSONHelperTest extends JamalHelper {
	
	private static final long serialVersionUID = 2806607387892223720L;
	
	
	// toJSONStringTest
	
	@Test
	public void toJSONStringTest() {
	
		String json = null;
		
		try {
			
			json = JSONHelper.toJSONString("teste");
			Assert.assertTrue("O json não deve ser nulo!", json != null);
			Assert.assertTrue("Os valores devem ser iguais!", json.equals("\"teste\""));
			
			json = JSONHelper.toJSONString(1);
			Assert.assertTrue("O json não deve ser nulo!", json != null);
			Assert.assertTrue("Os valores devem ser iguais!", json.equals("1"));
			
			
			json = JSONHelper.toJSONString(new Integer(1));
			Assert.assertTrue("O json não deve ser nulo!", json != null);
			Assert.assertTrue("Os valores devem ser iguais!", json.equals(new Integer(1).toString()));
			
			Calendar calendar = Calendar.getInstance();
			json = JSONHelper.toJSONString(calendar);
			Assert.assertTrue("O json não deve ser nulo!", json != null);
			Assert.assertTrue("Os valores devem ser iguais!", json.equals(new Long(calendar.getTimeInMillis()).toString()));

			// TODO: Testar com ObjectTestModel
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void toObjectTest() {
		// TODO: Teste - JSONHelperTest.toObjectTest()
	}
}