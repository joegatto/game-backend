package br.com.gatto.game.backend.controllers;

import java.net.HttpURLConnection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import br.com.gatto.game.backend.cache.level.Level;
import br.com.gatto.game.backend.cache.level.LevelCache;
import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.helper.SortHelper;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is a thread-safe singleton that is going to be used to 
 * guarantee one instance of level cache in the application.
 */

public class LevelController {
	
	private static final int MAX_COUNT = 15;
	private LevelCache scoreCache;
	
	public LevelController() {
		scoreCache = new LevelCache(); 
	}
	
    private static class Holder {
        static final LevelController INSTANCE = new LevelController();
    }

    public static LevelController getInstance() {
        return Holder.INSTANCE;
    }

	public Level getLevelById(int levelId) {
		return scoreCache.get(levelId);
	}
    
	public int postScore(int levelId, User user, int score) {
		if (!user.getSession().hasExpired()) {
			Level level = getLevelById(levelId);
			level.addScore(user.getUserId(), score);
			scoreCache.put(levelId, level);
		} else {
			return HttpURLConnection.HTTP_FORBIDDEN;
		}
		return HttpURLConnection.HTTP_OK;
	}
	
	public String getHighscore(int levelId) {
		String result = "";
		Level level = scoreCache.get(levelId);
		ConcurrentMap<Integer, Integer> unsortedMap = level.getScore();
		Map<Integer, Integer> sortedScores = Collections.synchronizedMap(SortHelper.sortByComparator(unsortedMap));
		synchronized (sortedScores) {
			Iterator<Integer> iterator = sortedScores.keySet().iterator();
	        int user = 0;
	        int count = 0;
	        while(iterator.hasNext() && count++ < MAX_COUNT) {
	            user = iterator.next();
	            if (result.isEmpty()) {
	            	result = user + "=" + sortedScores.get(user).intValue();
	            } else {
	            	result += "," + user + "=" + sortedScores.get(user).intValue();
	            }
	        }
		}
        return result;
	}

	public void clear() {
		scoreCache.cleanup();
	}
}
