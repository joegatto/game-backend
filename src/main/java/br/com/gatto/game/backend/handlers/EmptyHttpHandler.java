package br.com.gatto.game.backend.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class handles all requests arriving for empty requests.
 */

public class EmptyHttpHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set("Content-Type", "text/plain");
		exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
		OutputStream responseBody = exchange.getResponseBody();
		String s = "No function found for this URL in the game: " + exchange.getRequestURI();
		responseBody.write(s.getBytes());
		responseBody.close();
	}

}
