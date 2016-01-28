package br.com.gatto.game.backend.cache.user;

import br.com.gatto.game.backend.cache.Cacheable;
import br.com.gatto.game.backend.cache.session.Session;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This is a class that represents the Users of the application. It extends Cacheable so it means it can be handled by its cache.
 */

public class User extends Cacheable {

	private int userId;
	private Session session;

	public User(int userId, long createTime) {
		super();
		this.userId = userId;
		session = new Session(this, createTime);
	}

	public Session getSession() {
		return session;
	}

	public int getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((session == null) ? 0 : session.hashCode());
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		if (obj == this)
			return true;
		return this.userId == ((User) obj).userId;
	}
	
}
