package br.com.gatto.game.backend.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the handler used by GameServer.java that calls the factory to create the proper handler.
 */

public class RequestHandler implements HttpHandler {

    private HandlerFactory factory;

    public RequestHandler() {
        factory = new HandlerFactory();
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        HttpHandler handler = factory.getHandler(httpExchange);
        handler.handle(httpExchange);
    }

}
