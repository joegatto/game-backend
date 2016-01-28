package br.com.gatto.game.backend.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import br.com.gatto.game.backend.controllers.LevelController;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class handles all requests arriving from GET /<userid>/login.
 */

public class GetHighScoreListHandler extends GameHttpHandler {

	@Override
    public void handle(HttpExchange exchange) throws IOException {
		super.handle(exchange);
		String requestMethod = exchange.getRequestMethod();
        
        int statusCode = HttpURLConnection.HTTP_OK;
        long length = 0;
        String result = "";
        
        if (requestMethod.equalsIgnoreCase(GET_METHOD)) {
        	int levelId = getLevelId(exchange);
            result = LevelController.getInstance().getHighscore(levelId);
        } else {
        	statusCode = HttpURLConnection.HTTP_BAD_METHOD;
        	result = BAD_METHOD_MESSAGE;
        }
        
        handleResponse(exchange, statusCode, length, result);
    }
    
    private int getLevelId(HttpExchange exchange) {
        String[] tokens = exchange.getRequestURI().toString().split("[/?=]");
        if(tokens.length > 2) {
        	return Integer.parseInt(tokens[1].toLowerCase());
        }
        return 0;
	}
    
}
