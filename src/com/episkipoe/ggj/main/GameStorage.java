package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.episkipoe.common.player.MovePlayer;

public class GameStorage implements com.episkipoe.common.GameStorage {

	@Override
	public String[] getCommonImages() {
		String[] imgs = {"Main.png", "SnakeHead.png", "SnakeTail.png", "SnakeTail.png"};
		return imgs;
	}

	@Override
	public boolean loadGame() throws Exception {
		return false;
	}

	@Override
	public void newGame() throws Exception {
		Game.switchRoom(MainRoom.class);
		Game.setMouseMode(new MovePlayer());
	}

	@Override
	public void saveGame() {

	}

	@Override
	public void startup() throws Exception {
		Game.registerRoom(MainRoom.class, new MainRoom());
		Game.registerRoom(GameRoom.class, new GameRoom());
		if(!loadGame()) newGame(); 
	}


}
