package br.com.gatto.game.backend.cache.level;

import br.com.gatto.game.backend.cache.Cache;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This is a class that represents the cache structure for Levels of the application.
 */

public class LevelCache extends Cache<Integer, Level> {
	
	public LevelCache() {
        super();
    }
    
	@Override
	public Level get(Integer key) {
		if (super.get(key) != null) {
			return super.get(key);
		}
		Level level = new Level(key);
		super.put(key, level);
		return level;
	}
	
}
