package br.com.gatto.game.backend.controllers;

import br.com.gatto.game.backend.cache.session.SessionCache;
import br.com.gatto.game.backend.cache.user.User;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is a thread-safe singleton that is going to be used to
 * guarantee one instance of session cache in the application.
 */

public class SessionController {

	private SessionCache sessionCache;

	public SessionController() {
		sessionCache = new SessionCache();
	}

	private static class Holder {
		static final SessionController INSTANCE = new SessionController();
	}

	public static SessionController getInstance() {
		return Holder.INSTANCE;
	}

	public String login(User user) {
		 sessionCache.put(user.getSession().getKey(), user.getSession());
		 return user.getSession().getKey();
	}
	
	public User getUserBySessionKey(String key) {
		if (sessionCache.get(key) != null) {
			return sessionCache.get(key).getUser();
		}
		return null;
	}
	
	public boolean isSessionValid(String key) {
		return !sessionCache.get(key).hasExpired();
	}

	public void clear() {
		sessionCache.cleanup();
	}
}
