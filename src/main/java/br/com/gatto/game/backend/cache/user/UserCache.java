package br.com.gatto.game.backend.cache.user;

import br.com.gatto.game.backend.cache.Cache;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This is a class that represents the cache structure for Users of the application.
 */

public class UserCache extends Cache<Integer, User> {

	public UserCache() {
		super();
	}

	@Override
	public User get(Integer key) {
		if (super.get(key) != null) {
			return super.get(key);
		}
		User user = new User(key, System.currentTimeMillis());
		super.put(key, user);
		return user;
	}

}
