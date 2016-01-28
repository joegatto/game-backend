package br.com.gatto.game.backend.cache.session;

import java.util.Random;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * That is the class used to create the session key string, we are currently 
 * wrapping the string to have 10 chars max and be consisted by only alphanumeric chars.
 */

public class KeyGenerator {
    private static final String ALLOWED_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int MAX_LENGTH = 10;
    private static final Random random = new Random();

    public static String getKey() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < MAX_LENGTH; i++) {
            builder.append (ALLOWED_CHARS.charAt(random.nextInt(ALLOWED_CHARS.length())));
        }
        return builder.toString();
    }
}
