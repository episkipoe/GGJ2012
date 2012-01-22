package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;

public class GameStorage implements com.episkipoe.common.GameStorage {

	@Override
	public String[] getCommonImages() {
		String[] imgs = {"Main.png"};
		return imgs;
	}

	@Override
	public boolean loadGame() throws Exception {
		return false;
	}

	@Override
	public void loadRooms() throws Exception {

	}

	@Override
	public void newGame() throws Exception {
		Game.switchRoom(MainRoom.class);
	}

	@Override
	public void saveGame() {

	}

	@Override
	public void startup() throws Exception {
		Game.registerRoom(MainRoom.class, new MainRoom());
		if(!loadGame()) newGame(); 
	}

}
