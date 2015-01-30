package br.com.cruz.jamal.common.helper;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.NullObjectException;

public class ValidationHelper extends JamalHelper {

	private static final long serialVersionUID = 3895057146334873623L;

	public static <T> void notNull(T object) throws JamalException {
		if (object == null) {
			throw new NullObjectException();
		}
	}
}
