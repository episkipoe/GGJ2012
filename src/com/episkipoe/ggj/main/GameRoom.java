package com.episkipoe.ggj.main;

import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.snake.Snake;

public class GameRoom extends Room {
	public GameRoom() { 
		addDrawable(new Snake());
	}
	
	@Override
	public boolean showHud() { return false; }
}
