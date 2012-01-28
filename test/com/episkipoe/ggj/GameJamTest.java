package com.episkipoe.ggj;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.episkipoe.ggj.main.rooms.MainRoom;


public class GameJamTest {
	@Test public void createGame() throws Exception {
		MainRoom main = new MainRoom();
		assertFalse(main.showHud());
	}
}
