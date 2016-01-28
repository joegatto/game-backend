package br.com.gatto.game.backend.handlers;

import java.net.URI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the unit test for HandlerFactory.java class
 */

public class HandlerFactoryTest {

    private HandlerFactory factory;
    private HttpExchange httpExchange;

    @Before
    public void setUp() {
        factory = new HandlerFactory();
        httpExchange = Mockito.mock(HttpExchange.class);
    }

    @Test
    public void testLoginURIReturningLoginRequestHandler() {
        Mockito.when(httpExchange.getRequestURI()).thenReturn(URI.create("/4711/login"));
        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof LoginHandler);
    }

    @Test
    public void testScoreURIReturningScoreUpdateRequestHandler() {
        Mockito.when(httpExchange.getRequestURI()).thenReturn(URI.create("/2/score?sessionkey=UICSNDK"));
        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof PostUserScoreHandler);
    }

    @Test
    public void testHighScoreListURIReturningHighScoreListRequestHandler() {
        Mockito.when(httpExchange.getRequestURI()).thenReturn(URI.create("/2/highscorelist"));
        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof GetHighScoreListHandler);
    }

    @Test
    public void testOthersURIReturningEmptyHttpHandler() {
        Mockito.when(httpExchange.getRequestURI()).thenReturn(URI.create("/whatever"));
        HttpHandler httpHandler = factory.getHandler(httpExchange);
        Assert.assertTrue(httpHandler instanceof EmptyHttpHandler);
    }
    
}
