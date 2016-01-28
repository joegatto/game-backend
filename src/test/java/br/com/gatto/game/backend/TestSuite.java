package br.com.gatto.game.backend;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import br.com.gatto.game.backend.cache.CacheTest;
import br.com.gatto.game.backend.cache.session.SessionTest;
import br.com.gatto.game.backend.controller.LevelControllerTest;
import br.com.gatto.game.backend.controller.SessionControllerTest;
import br.com.gatto.game.backend.controller.UserControllerTest;
import br.com.gatto.game.backend.handlers.HandlerFactoryTest;
import br.com.gatto.game.backend.server.GameServerTest;

/**
 * @author Joao Gatto <joaocarlosgatto@hotmail.com>
 * @version 1.0
 * 
 * This class is the jUnit test suite
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({ GameServerTest.class, CacheTest.class, HandlerFactoryTest.class,
		UserControllerTest.class, SessionControllerTest.class, LevelControllerTest.class,
		SessionTest.class })
public class TestSuite {

}
