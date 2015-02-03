package br.com.cruz.jamal.test.common.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.cruz.jamal.common.exception.JamalException;
import br.com.cruz.jamal.common.helper.CollectionHelper;
import br.com.cruz.jamal.test.common.JamalTest;

public class CollectionHelperTest extends JamalTest {

	private static final long serialVersionUID = -7936816376019130529L;

	@Test
	public void isNullOrEmptyCollectionTest() {
		
		Collection<Object> collection = null;
		Assert.assertTrue("A coleção deve ser nula!", CollectionHelper.isNullOrEmpty(collection));
		
		collection = new HashSet<Object>();
		Assert.assertTrue("A coleção deve ser vazia!", CollectionHelper.isNullOrEmpty(collection));
		
		collection.add("Item");
		Assert.assertFalse("A coleção não deve ser vazia!", CollectionHelper.isNullOrEmpty(collection));
		
		collection = new ArrayList<Object>();
		Assert.assertTrue("A coleção deve ser vazia!", CollectionHelper.isNullOrEmpty(collection));
		
		collection.add(null);
		collection.add(null);
		collection.add(null);
		Assert.assertTrue("A coleção deve ser vazia!", CollectionHelper.isNullOrEmpty(collection));
		
		collection = new ArrayList<Object>();
		Assert.assertTrue("A coleção deve ser vazia!", CollectionHelper.isNullOrEmpty(collection));
		
		collection.add("Item");
		Assert.assertFalse("A coleção não deve ser vazia!", CollectionHelper.isNullOrEmpty(collection));
		
	}
	
	@Test
	public void isNullOrEmptyArrayTest() {
		
		Object[] array = null;
		Assert.assertTrue("O array deve ser nulo!", CollectionHelper.isNullOrEmpty(array));
		
		array = new Object[1];
		Assert.assertTrue("O array deve ser vazio!", CollectionHelper.isNullOrEmpty(array));
		
		array[0] = "Item";
		Assert.assertFalse("O array não deve ser vazio!", CollectionHelper.isNullOrEmpty(array));
		
	}

	@Test
	public void newArrayListTest() {
		
		List<String> list1 = new ArrayList<String>();
		list1.add("teste1");
		list1.add("teste2");
		list1.add("teste3");
		list1.add("teste4");
		list1.add("teste5");
		list1.add("teste6");
		list1.add("teste7");
		list1.add("teste8");
		list1.add("teste9");
		list1.add("teste10");
		
		List<String> list2 = null;
		try {
			list2 = CollectionHelper.newArrayList("teste1", "teste2", "teste3", "teste4", "teste5", "teste6", "teste7", "teste8", "teste9", "teste10");
		} catch (JamalException e) {
			e.printStackTrace();
		}
		
		
		Assert.assertTrue("As lista 2 não devem ser nula!", list2 != null);
		Assert.assertTrue("As listas devem ter o mesmo tamanho!", list1.size() == list2.size());
		
		for (int i = 0; i < list1.size(); i++) {
			Assert.assertTrue("As listas devem ter o mesmo valor!", list1.get(i).equals(list2.get(i)));
		}
	}
}
