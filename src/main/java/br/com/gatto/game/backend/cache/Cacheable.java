package br.com.gatto.game.backend.cache;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class represents an item that is eligible to be inserted to the cache.
 */
public class Cacheable {

    private long lastAccessed = System.currentTimeMillis();

    protected Cacheable() {
    }

    public long getLastAccessed() {
        return lastAccessed;
    }

    public void setLastAccessed(long lastAccessed) {
        this.lastAccessed = lastAccessed;
    }

}
