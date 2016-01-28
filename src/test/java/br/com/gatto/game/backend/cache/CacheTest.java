package br.com.gatto.game.backend.cache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for Cache.java class
 */

public class CacheTest {

	private static final int EMPTY_SIZE = 0;
	private static final int SIZE = 1;
	private static final int TEST_KEY = 1;
	
	class TestObject extends Cacheable {
		
	}
	
	private Cache<Integer, TestObject> cache;
	private TestObject test = null;
	
	@Before
    public void setup() {
		cache = new Cache<Integer, TestObject>();
		test = new TestObject();
	}
	
    @Test
    public void testAddingItemsToTheCache() {
    	cache.put(TEST_KEY, test);
    	Assert.assertNotNull(cache.get(TEST_KEY));
        Assert.assertEquals(test, cache.get(TEST_KEY));
    }
    
    @Test
    public void testRemovingItemsToTheCache() {
    	cache.put(TEST_KEY, test);
    	Assert.assertNotNull(cache.get(TEST_KEY));
    	Assert.assertEquals(test, cache.get(TEST_KEY));
    	cache.remove(TEST_KEY);
    	Assert.assertNull(cache.get(TEST_KEY));
    }
    
    @Test
    public void testCacheCleanUp() {
    	cache.put(TEST_KEY, test);
    	Assert.assertNotNull(cache.get(TEST_KEY));
    	Assert.assertEquals(test, cache.get(TEST_KEY));
    	cache.cleanup();
    	Assert.assertEquals(EMPTY_SIZE, cache.size());
    }
    
    @Test
    public void testCacheSize() {
    	cache.put(TEST_KEY, test);
    	Assert.assertNotNull(cache.get(TEST_KEY));
    	Assert.assertEquals(test, cache.get(TEST_KEY));
    	Assert.assertEquals(SIZE, cache.size());
    }

}
