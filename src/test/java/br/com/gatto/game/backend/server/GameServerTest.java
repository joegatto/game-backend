package br.com.gatto.game.backend.server;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for GameServer.java class
 */

public class GameServerTest {

    public static final int PORT = 8082;
    private static final String CONTEXT = "/test";

    private GameServer gameServer;

    @Before
    public void setup() {
        try {
            gameServer = new GameServer.Builder().port(PORT).context(CONTEXT).handler(new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    Headers responseHeaders = exchange.getResponseHeaders();
                    responseHeaders.set("Content-Type", "text/plain");
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
            }).build();
            gameServer.start();
        } catch (IOException e) {
            Assert.fail("Caught IOException exception: " + e.getMessage());
        }
    }

    @Test
    public void testConnectivityToTheServer() {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) new URL("http://localhost:8082/test").openConnection();
            Assert.assertTrue("Server has connectivity", HttpURLConnection.HTTP_OK == conn.getResponseCode());
        } catch (IOException e) {
            Assert.fail("Caught IOException exception: " + e.getMessage());
        }
    }

    @After
    public void finish() {
        gameServer.stop();
    }
}
