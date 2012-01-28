package com.episkipoe.ggj.main.rooms;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.interact.BackgroundDoor;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.Main;

public class GameOverRoom extends Room {
	public GameOverRoom() { 
		setBackground("GameOver.png");
		addDrawable(new BackgroundDoor(new Point(0,0), new Point(Game.canvasWidth, Game.canvasHeight), MainRoom.class));
	}
	public void onEnter() {
		Main.level = 0;
	}
}
