package br.com.cruz.jamal.common.helper;

public class StringHelper extends JamalHelper {

	private static final long serialVersionUID = -5230331087913913755L;

	public static final boolean isNullOrEmpty(String string) {
		return string == null || string.isEmpty();
	}
}
