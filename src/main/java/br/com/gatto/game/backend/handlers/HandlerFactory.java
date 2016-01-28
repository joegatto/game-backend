package br.com.gatto.game.backend.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is our main class implemented using factory pattern. 
 * It instantiates a handler instance only after determining its action.
 */

public class HandlerFactory {

    public HttpHandler getHandler(HttpExchange exchange) {
        String action = getAction(exchange);
        if (action.equalsIgnoreCase("login")) {
            return new LoginHandler();
        } else if (action.equalsIgnoreCase("score")) {
            return new PostUserScoreHandler();
        } else if (action.equalsIgnoreCase("highscorelist")) {
            return new GetHighScoreListHandler();
        }
        return new EmptyHttpHandler();
    }

	private String getAction(HttpExchange exchange) {
        String[] tokens = exchange.getRequestURI().toString().split("[/?=]");
        if(tokens.length > 2) {
        	return tokens[2].toLowerCase();
        }
        return "";
	}
}
