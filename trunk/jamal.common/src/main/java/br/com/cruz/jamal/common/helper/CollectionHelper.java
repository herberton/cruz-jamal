package br.com.cruz.jamal.common.helper;

import java.util.Collection;

public class CollectionHelper extends JamalHelper {

	private static final long serialVersionUID = 5564514635549011361L;
	
	public static final <T> boolean isNullOrEmpty(Collection<T> collection) {
		
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		
		for (T item : collection) {
			if (item != null) {
				return false;
			}
		}
		
		return true;
	}
	
	public static final <T> boolean isNullOrEmpty(T[] array) {
		
		if(array == null || array.length <= 0) {
			return true;
		}
		
		for (T item : array) {
			if (item != null) {
				return false;
			}
		}
		
		return true;
	}
}
