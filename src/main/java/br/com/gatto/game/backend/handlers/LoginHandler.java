package br.com.gatto.game.backend.handlers;

import java.io.IOException;
import java.net.HttpURLConnection;

import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.controllers.SessionController;
import br.com.gatto.game.backend.controllers.UserController;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class handles all requests arriving from GET /<levelid>/highscorelist
 */

public class LoginHandler extends GameHttpHandler {
	
	@Override
    public void handle(HttpExchange exchange) throws IOException {
		super.handle(exchange);
        String requestMethod = exchange.getRequestMethod();
        
        int statusCode = HttpURLConnection.HTTP_OK;
        long length = 0;
        String result = "";
        
        if (requestMethod.equalsIgnoreCase(GET_METHOD)) {
            int userId = getUserId(exchange);
            User user = UserController.getInstance().getUserById(userId);
            String sessionKey = SessionController.getInstance().login(user);
            handleResponse(exchange, statusCode, sessionKey.length(), sessionKey);
        } else {
        	statusCode = HttpURLConnection.HTTP_BAD_METHOD;
        	result = BAD_METHOD_MESSAGE;
        }
        
        handleResponse(exchange, statusCode, length, result);
    }
    
    private int getUserId(HttpExchange exchange) {
        String[] tokens = exchange.getRequestURI().toString().split("[/?=]");
        if(tokens.length > 2) {
        	return Integer.parseInt(tokens[1].toLowerCase());
        }
        return 0;
	}
    
}
