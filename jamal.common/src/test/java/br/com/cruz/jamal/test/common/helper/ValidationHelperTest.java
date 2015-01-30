package br.com.cruz.jamal.test.common.helper;

import org.junit.Test;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.NullObjectException;
import br.com.cruz.jamal.common.helper.ValidationHelper;
import br.com.cruz.jamal.test.common.JamalTest;

public class ValidationHelperTest extends JamalTest {

	private static final long serialVersionUID = 3212829349148337281L;

	@Test(expected=NullObjectException.class)
	public void notNullTest() throws JamalException {
		
		ValidationHelper.notNull(new Object());
		ValidationHelper.notNull(null);
		
	}
}
