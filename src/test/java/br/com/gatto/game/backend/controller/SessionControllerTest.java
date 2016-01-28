package br.com.gatto.game.backend.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.controllers.SessionController;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for SessionController.java class
 */

public class SessionControllerTest {

	private static final int USER_1 = 4711;
	private static final int USER_2 = 4712;
	
	private User user1;
	private User user2;
	
	private SessionController sessionController;
	
	@Before
    public void setUp() {
		user1 = new User(USER_1, System.currentTimeMillis());
		user2 = new User(USER_2, System.currentTimeMillis());
		
		sessionController = SessionController.getInstance();
		sessionController.clear();
    }
	
	@Test
    public void testSingletonDesignPattern() {
		SessionController sessionController1 = SessionController.getInstance();
		SessionController sessionController2 = SessionController.getInstance();
        Assert.assertEquals(sessionController1, sessionController2);
    }
	
	@Test
	public void testLoginReturningSessionKey(){
		String key = sessionController.login(user1);
		Assert.assertNotNull(key);
        Assert.assertEquals(10, key.length());
	}
	
	@Test
    public void testSessionKeyForTwoDifferentUsers() {
    	String key1 = sessionController.login(user1);
        String key2 = sessionController.login(user2);
        Assert.assertNotNull(key1);
        Assert.assertNotNull(key2);
        Assert.assertFalse(key1.equals(key2));
    }
	
	@Test
	public void testSessionKeyNotExpiredForTheSameUser() {
        String key1 = sessionController.login(user1);
        String key2 = sessionController.login(user1);
        Assert.assertNotNull(key1);
        Assert.assertNotNull(key2);
        Assert.assertEquals(key1, key2);
        Assert.assertTrue(key1 == key2);
	}
	
}
