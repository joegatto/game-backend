package br.com.gatto.game.backend.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is parent class of all handlers instances.
 * It will also contain the common logic applicable in handler making of all types.
 */

public class GameHttpHandler implements HttpHandler {
    
	public static final String POST_METHOD = "POST";
	public static final String GET_METHOD = "GET";
    public static final String BAD_METHOD_MESSAGE = "The service you reach only works with GET method.";
    public static final String INVALID_SESSION_MESSAGE = "The session is not valid. Please, login again.";
    public static final String BAD_URL_REQUEST_MESSAGE = "Bad URL request. Check your url.";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "text/plain";
    public static final String URL_VALIDATION_REGEX = "[/]+[0-9]+[/]+[a-zA-Z]+[/]*";
    public static final int DEFAULT_RESPONSE_LENGTH = 0;
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!isValidRequest(exchange)) {
            handleResponse(exchange, HttpURLConnection.HTTP_BAD_REQUEST, DEFAULT_RESPONSE_LENGTH, BAD_URL_REQUEST_MESSAGE);
            return;
        }
    }
    
    public boolean isValidRequest(HttpExchange exchange) {
        Pattern pattern = Pattern.compile(URL_VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(exchange.getRequestURI().getPath());
        return matcher.matches();
    }
    
    public void handleResponse(HttpExchange exchange, int statusCode, long responseLength, String result) throws IOException {
		Headers responseHeaders = exchange.getResponseHeaders();
		responseHeaders.set(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
		exchange.sendResponseHeaders(statusCode, responseLength);
		OutputStream responseBody = exchange.getResponseBody();
		String s = result;
		responseBody.write(s.getBytes());
		responseBody.close();
	}

}
