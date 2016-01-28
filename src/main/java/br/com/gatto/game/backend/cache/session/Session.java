package br.com.gatto.game.backend.cache.session;

import br.com.gatto.game.backend.cache.Cacheable;
import br.com.gatto.game.backend.cache.user.User;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This is a class that represents the Session of the application. It extends Cacheable so it means it can be handled by its cache.
 */

public class Session extends Cacheable {

    private static final int EXPIRATION_TIME = 600000;
    
    private String key;
    private long startTime;
    private User user;
    
    public Session(User user, long startTime) {
        super();
        this.startTime = startTime;
        this.user = user;
    }

    public String getKey() {
        if (key == null || hasExpired()) {
            key = KeyGenerator.getKey();
        }
        return key;
    }
    
    public boolean hasExpired() {
        long now = System.currentTimeMillis();
        return (startTime + EXPIRATION_TIME) < now;
    }

    public User getUser() {
        return user;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Session))
			return false;
		if (obj == this)
			return true;
		return this.key == ((Session) obj).key;
	}

}
