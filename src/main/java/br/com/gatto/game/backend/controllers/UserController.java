package br.com.gatto.game.backend.controllers;

import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.cache.user.UserCache;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is a thread-safe singleton that is going to be used to 
 * guarantee one instance of user cache in the application.
 */

public class UserController {
	
	private UserCache userCache;

	public UserController() {
		userCache = new UserCache();
	}

	private static class Holder {
		static final UserController INSTANCE = new UserController();
	}

	public static UserController getInstance() {
		return Holder.INSTANCE;
	}
	
	public User getUserById(int userId) {
		return userCache.get(userId);
	}

	public void clear() {
		userCache.cleanup();
	}
}
