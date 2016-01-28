package br.com.gatto.game.backend.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import br.com.gatto.game.backend.cache.user.User;
import br.com.gatto.game.backend.controllers.LevelController;
import br.com.gatto.game.backend.controllers.SessionController;

import com.sun.net.httpserver.HttpExchange;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class handles all requests arriving from POST /<levelid>/score?sessionkey=<sessionkey>.
 */

public class PostUserScoreHandler extends GameHttpHandler {

	private static final String CHARSET_NAME = "utf-8";
	private static final String PARAM_KEY = "sessionkey";
	public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "text/plain";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		super.handle(exchange);
		String requestMethod = exchange.getRequestMethod();

		int statusCode = HttpURLConnection.HTTP_OK;
		long length = 0;
		String result = "";

		if (requestMethod.equalsIgnoreCase(POST_METHOD)) {
			int levelId = getLevelIdParam(exchange);
			String sessionKey = getSessionKeyParam(exchange, PARAM_KEY);
			User user = SessionController.getInstance().getUserBySessionKey(sessionKey.trim());
			if (user != null) {
				int score = getScoreParam(exchange);
				statusCode = LevelController.getInstance().postScore(levelId, user, score);
			} else {
				statusCode = HttpURLConnection.HTTP_FORBIDDEN;
			}
			switch (statusCode) {
				case HttpURLConnection.HTTP_FORBIDDEN:
					statusCode = HttpURLConnection.HTTP_FORBIDDEN;
					result = INVALID_SESSION_MESSAGE;
					break;
				default:
					break;
			}
		} else {
			statusCode = HttpURLConnection.HTTP_BAD_METHOD;
			result = BAD_METHOD_MESSAGE;
		}
		
		handleResponse(exchange, statusCode, length, result);
	}

	private int getLevelIdParam(HttpExchange exchange) {
		String[] tokens = exchange.getRequestURI().toString().split("[/?=]");
		if (tokens.length > 2) {
			return Integer.parseInt(tokens[1].toLowerCase());
		}
		return 0;
	}

	private String getSessionKeyParam(HttpExchange exchange, String paramKey) {
		Map<String, String> result = new HashMap<String, String>();
	    for (String param : exchange.getRequestURI().getQuery().split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
	    return result.get(paramKey);
	}

	private int getScoreParam(HttpExchange exchange) throws NumberFormatException, IOException {
		InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), CHARSET_NAME);
		BufferedReader br = new BufferedReader(isr);
		return Integer.parseInt(br.readLine());
	}

}
