package br.com.gatto.game.backend.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This is a generic class that represents the cache. 
 * Every new entity that we would create in the application needs to extend this class. 
 * This cache structure will hold the data as long as the server be up.
 */

public class Cache<K, T extends Cacheable> {

    private ConcurrentMap<K, T> cacheMap;

    public Cache() {
        cacheMap = new ConcurrentHashMap<K, T>();
    }

    public void put(K key, T value) {
        cacheMap.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public T get(K key) {
        Cacheable c = cacheMap.get(key);
        if (c == null)
            return null;
        else {
            c.setLastAccessed(System.currentTimeMillis());
            return (T) c;
        }
    }

    public void remove(K key) {
        cacheMap.remove(key);
    }

    public int size() {
        return cacheMap.size();
    }

    public void cleanup() {
    	cacheMap = new ConcurrentHashMap<K, T>();
    }
}
