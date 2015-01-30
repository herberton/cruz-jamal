package br.com.cruz.jamal.test.common.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

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
}
