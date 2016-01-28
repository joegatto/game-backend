package br.com.gatto.game.backend;

import java.io.IOException;

import br.com.gatto.game.backend.handlers.RequestHandler;
import br.com.gatto.game.backend.server.GameServer;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the entry point of the application.
 */

public class EntryPoint {

	public static final int PORT = 8081;
	private static final String CONTEXT = "/";
	
    /**
     * Method that initializes the application 
     */
    public static void main(String[] args) throws IOException {
        new GameServer.Builder().port(PORT).context(CONTEXT).handler(new RequestHandler()).build().start();
    }

}