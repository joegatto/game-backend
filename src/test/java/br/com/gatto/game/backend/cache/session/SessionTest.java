package br.com.gatto.game.backend.cache.session;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.gatto.game.backend.cache.user.User;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for Session.java class
 */

public class SessionTest {

	private static final String BLANK_CHAR = " ";
	private static final int NINE_MINUTES = 599999;
	private static final int TEN_MINUTES = 600001;
	private Session session = null;
	private User user = null;
	
	@Before
    public void setup() {
		user = Mockito.mock(User.class);
		session = new Session(user, System.currentTimeMillis());
	}
	
	@Test
	public void testSessionKeyWithNoSpaces() {
		String key = session.getKey();
		Assert.assertFalse(key.contains(BLANK_CHAR));
	}
	
	@Test
	public void testSessionKeyWithNoStrangeCharacter() {
		String key = session.getKey();
		Pattern p = Pattern.compile("[^a-z0-9]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(key);
		boolean b = m.find();
		Assert.assertFalse(b);
	}
	
    @Test
    public void testSessionKeyCreation() {
    	String key = session.getKey();
        Assert.assertNotNull(key);
        Assert.assertEquals(10, key.length());
    }
    
    @Test
	public void testSessionKeyIsStillValidAfterTenMinutes() {
    	session = new Session(user, System.currentTimeMillis() - TEN_MINUTES);
        Assert.assertTrue(session.hasExpired());
	}
	
	@Test
	public void testSessionKeyIsStillValidBeforeTenMinutes() {
		session = new Session(user, System.currentTimeMillis() - NINE_MINUTES);
        Assert.assertFalse(session.hasExpired());
	}
    
}
