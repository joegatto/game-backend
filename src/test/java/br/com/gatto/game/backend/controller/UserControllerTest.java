package br.com.gatto.game.backend.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.controllers.UserController;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for UserController.java class
 */

public class UserControllerTest {
	
	private static final int USER_1 = 4711;
	
	private UserController userController;
	
	@Before
    public void setUp() {
		userController = UserController.getInstance();
		userController.clear();
    }
	
	@Test
    public void testSingletonDesignPattern() {
		UserController userController1 = UserController.getInstance();
		UserController userController2 = UserController.getInstance();
        Assert.assertEquals(userController1, userController2);
    }

	@Test
	public void testUserCreation() {
		User user = UserController.getInstance().getUserById(USER_1);
		Assert.assertNotNull(user);
        Assert.assertEquals(USER_1, user.getUserId());
	}
}
