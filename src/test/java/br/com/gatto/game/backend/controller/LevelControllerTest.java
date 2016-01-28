package br.com.gatto.game.backend.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.gatto.game.backend.cache.level.Level;
import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.controllers.LevelController;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for LevelController.java class
 */

public class LevelControllerTest {

	private static final int TEN_MINUTES = 600001;
	private static final int EMPTY_SIZE = 0;
	private static final String EMPTY_STRING = "";
	private static final int SIZE = 1;
	
	private static final int USER_1 = 4711;
	private static final int USER_2 = 4712;
	
	private static final int LEVEL_1 = 1;
	private static final int LEVEL_2 = 2;
	
	private static final int SCORE_1 = 1000;
	private static final int SCORE_2 = 2000;
	
	private static final String HIGHSCORE_USER1 = "4711=2000";
	private static final String HIGHSCORE_LIST_1 = "15=15000,14=14000,13=13000,12=12000,11=11000,10=10000,9=9000,8=8000,7=7000,6=6000,5=5000,4=4000,3=3000,2=2000,1=1000";
	private static final String HIGHSCORE_LIST_2 = "17=17000,16=16000,15=15000,14=14000,13=13000,12=12000,11=11000,10=10000,9=9000,8=8000,7=7000,6=6000,5=5000,4=4000,3=3000";
	private static final String HIGHSCORE_LIST_3 = "14=42000,12=36000,10=30000,15=30000,13=26000,8=24000,11=22000,6=18000,9=18000,7=14000,4=12000,5=10000,2=6000,3=6000,1=2000";
	
	private static final int SCORE_COUNT = 15;
	
	private User user1;
	private User user2;
	
	private LevelController levelController;
	
	@Before
    public void setUp() {
		user1 = new User(USER_1, System.currentTimeMillis());
		user2 = new User(USER_2, System.currentTimeMillis() - TEN_MINUTES);
		
		levelController = LevelController.getInstance();
		levelController.clear();
    }
	
	@Test
    public void testSingletonDesignPattern() {
		LevelController sessionController1 = LevelController.getInstance();
		LevelController sessionController2 = LevelController.getInstance();
        Assert.assertEquals(sessionController1, sessionController2);
    }
	
	@Test
	public void testLevelCreation() {
		Level level = levelController.getLevelById(LEVEL_1);
		Assert.assertNotNull(level);
        Assert.assertEquals(LEVEL_1, level.getLevelId());
	}
	
	@Test
	public void testPostUserScoreInOneLevel() {
		levelController.postScore(LEVEL_1, user1, SCORE_1);
		Level level = levelController.getLevelById(LEVEL_1);
		Assert.assertNotNull(level);		
        Assert.assertEquals(Integer.valueOf(SCORE_1), Integer.valueOf(level.getScore().get(user1.getUserId())));
	}
	
	@Test
	public void testPostUserScoreInTwoLevels() {
		levelController.postScore(LEVEL_1, user1, SCORE_1);
		levelController.postScore(LEVEL_2, user1, SCORE_1);
		Level level1 = levelController.getLevelById(LEVEL_1);
		Level level2 = levelController.getLevelById(LEVEL_2);
		Assert.assertNotNull(level1);
		Assert.assertNotNull(level2);
        Assert.assertEquals(Integer.valueOf(SCORE_1), Integer.valueOf(level1.getScore().get(user1.getUserId())));
        Assert.assertEquals(Integer.valueOf(SCORE_1), Integer.valueOf(level2.getScore().get(user1.getUserId())));
	}
	
	@Test
	public void testPostScoreWithExpiredSession() {
		levelController.postScore(LEVEL_1, user2, SCORE_1);
		Level level = levelController.getLevelById(LEVEL_1);
		Assert.assertEquals(EMPTY_SIZE, level.getScore().size());
	}
	
	@Test
	public void testPostScoreWithValidSession() {
		levelController.postScore(LEVEL_1, user1, SCORE_1);
		Level level = levelController.getLevelById(LEVEL_1);
		Assert.assertNotNull(level);		
		Assert.assertEquals(SIZE, level.getScore().size());
	}
	
	@Test
    public void testPostHigherScoreToLevelThatAnUserHasAlreadyBeenSubmitted() {
		levelController.postScore(LEVEL_1, user1, SCORE_1);
		Level level = levelController.getLevelById(LEVEL_1);
		Assert.assertNotNull(level);		
        Assert.assertEquals(Integer.valueOf(SCORE_1), Integer.valueOf(level.getScore().get(user1.getUserId())));
        levelController.postScore(LEVEL_1, user1, SCORE_2);
        Assert.assertEquals(Integer.valueOf(SCORE_2), Integer.valueOf(level.getScore().get(user1.getUserId())));
    }
	
	@Test
    public void testPostLowerScoreToLevelThatAnUserHasAlreadyBeenSubmitted() {
		levelController.postScore(LEVEL_1, user1, SCORE_2);
		Level level = levelController.getLevelById(LEVEL_1);
		Assert.assertNotNull(level);		
        Assert.assertEquals(Integer.valueOf(SCORE_2), Integer.valueOf(level.getScore().get(user1.getUserId())));
        levelController.postScore(LEVEL_1, user1, SCORE_1);
        Assert.assertEquals(Integer.valueOf(SCORE_2), Integer.valueOf(level.getScore().get(user1.getUserId())));
    }
	
	@Test
	public void testListRetrieval() {
		populateCache(SCORE_COUNT);
		String result = levelController.getHighscore(LEVEL_1);
		Assert.assertEquals(HIGHSCORE_LIST_1, result);
	}
	
	@Test
	public void testResultAsCommaSeparatedList() {
		populateCache(SCORE_COUNT);
		String result = levelController.getHighscore(LEVEL_1);
		Assert.assertEquals(HIGHSCORE_LIST_1, result);
	}
	
	@Test
	public void testResultInDescendingOrder() {
		populateCacheForSorting(SCORE_COUNT);
		String result = levelController.getHighscore(LEVEL_1);
		Assert.assertEquals(HIGHSCORE_LIST_3, result);
	}
	
	@Test
	public void testResultToShowOnlyFifteenRows() {
		populateCache(SCORE_COUNT + 2);
		String result = levelController.getHighscore(LEVEL_1);
		Assert.assertEquals(HIGHSCORE_LIST_2, result);
	}
	
	@Test
	public void testResultToEnsureUserAppearsOnlyOneTime() {
		levelController.postScore(LEVEL_1, user1, SCORE_1);
		levelController.postScore(LEVEL_1, user1, SCORE_2);
		String result = levelController.getHighscore(LEVEL_1);
		Assert.assertEquals(HIGHSCORE_USER1, result);
	}
	
	@Test
	public void testEmptyResultIfNoScoreHasBeenSubmitted() {
		populateCache(SCORE_COUNT);
		String result = levelController.getHighscore(LEVEL_2);
		Assert.assertEquals(EMPTY_STRING, result);
	}
	
	@Test
	public void testScoreIsNotPresentInTheListIfNoScoreHasBeenSubmittedByUser() {
		populateCache(SCORE_COUNT);
		String result = levelController.getHighscore(LEVEL_1);
		Assert.assertNotNull(result);
		Assert.assertFalse(result.contains(EMPTY_STRING + USER_1));
	}
	
	private void populateCache(int scores) {
		for (int i = 1; i <= scores; i++) {
			User user = new User(i, System.currentTimeMillis());
			levelController.postScore(LEVEL_1, user, i * 1000);
		}
	}
	
	private void populateCacheForSorting(int scores) {
		for (int i = 1; i <= scores; i++) {
			User user = new User(i, System.currentTimeMillis());
			if (i % 2 == 0) {
				levelController.postScore(LEVEL_1, user, i * 3 * 1000);
			} else {
				levelController.postScore(LEVEL_1, user, i * 2 * 1000);
			}
			
		}
	}
	
}
