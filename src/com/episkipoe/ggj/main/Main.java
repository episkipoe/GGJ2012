package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	public static int level=0;
	public static boolean paused=false; 
	
	@Override
	public void onModuleLoad() {
		Game.begin(new GameStorage());
	}
	
	public static void gotoLevel(int newLevel) {
		level = newLevel;
		GameRoom room=null;
		try {
			room = (GameRoom)Game.getRoom(GameRoom.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(room != null) room.reset();
	}
}
