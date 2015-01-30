package br.com.cruz.jamal.test.common.helper;

import org.junit.Assert;
import org.junit.Test;

import br.com.cruz.jamal.common.helper.StringHelper;
import br.com.cruz.jamal.test.common.JamalTest;

public class StringHelperTest extends JamalTest {

	private static final long serialVersionUID = -3326938684812569168L;

	@Test
	public void isNullOrEmpty() {
		
		Assert.assertEquals("O resultado não foi verdadeiro", true, StringHelper.isNullOrEmpty(null));
		Assert.assertEquals("O resultado não foi verdadeiro", true, StringHelper.isNullOrEmpty(""));
		Assert.assertEquals("O resultado não foi falso", false, StringHelper.isNullOrEmpty("teste"));
		
	}
}
