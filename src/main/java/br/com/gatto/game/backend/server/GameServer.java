package br.com.gatto.game.backend.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class represents the server itself.
 * Here we can start/stop the server, change the port and request handlers.
 */

public class GameServer {

	private HttpServer server = null;

	public GameServer(Builder builder) throws IOException {
		server = HttpServer.create(new InetSocketAddress(builder.port), 0);
		server.createContext(builder.context, builder.handler);
		server.setExecutor(Executors.newCachedThreadPool());
	}

	/**
	 * Builder design pattern to help the unit tests and game server creation.
	 */
	public static class Builder {
        private int port;
        private String context;
        private HttpHandler handler;

        public Builder port(int port) {this.port = port; return this;}
        public Builder context(String context) {this.context = context; return this;}
        public Builder handler(HttpHandler handler) {this.handler = handler; return this;}
      
        public GameServer build() throws IOException {
            return new GameServer(this);
        }
    }
	
	/**
	 * This method is used to start the server, after creating the context and
	 * setting the Executor to allow the multi-threading to be done.
	 */
	public void start() {
		server.start();
		System.out.println("Server is listening on port " + server.getAddress().getPort());
	}

	/**
	 * This method is used to stop the server.
	 */
	public void stop() {
		server.stop(0);
		System.out.println("Server has stopped");
	}

}
