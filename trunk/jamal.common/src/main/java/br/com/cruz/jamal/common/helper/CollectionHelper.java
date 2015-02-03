package br.com.cruz.jamal.common.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.exception.UnableToCompleteOperationException;

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

	@SafeVarargs
	public static final <T> List<T> newArrayList(T item, T... itemArray) throws JamalException {
		
		List<T> itemList = new ArrayList<T>();
		
		try {
			if (item != null) {
				itemList.add(item);
			}
			if (!CollectionHelper.isNullOrEmpty(itemArray)) {
				for (T itemAux : itemArray) {
					if (item != null) {
						itemList.add(itemAux);
					}
				}
			}
		} catch (Exception e) {
			throw new UnableToCompleteOperationException("newArrayList", e);
		}
		
		return itemList;
	}
}
