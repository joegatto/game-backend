package br.com.gatto.game.backend.cache.level;

import java.util.concurrent.ConcurrentHashMap;

import br.com.gatto.game.backend.cache.Cacheable;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This is a class that represents the Levels of the application. It extends Cacheable so it means it can be handled by its cache.
 */

public class Level extends Cacheable {
	
	private int levelId;
	private ConcurrentHashMap<Integer, Integer> scores;
	
	public Level(int levelId) {
		this.levelId = levelId;
		scores = new ConcurrentHashMap<Integer, Integer>();
	}

	public int getLevelId() {
		return levelId;
	}

	public ConcurrentHashMap<Integer, Integer> getScore() {
		return scores;
	}
	
	public void addScore(int userId, int score) {
		if (scores.containsKey(userId) && scores.get(userId) > score) {
			return;
		}
		scores.put(userId, score);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + levelId;
		result = prime * result + ((scores == null) ? 0 : scores.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Level))
			return false;
		if (obj == this)
			return true;
		return this.levelId == ((Level) obj).levelId;
	}
	
}
