package com.episkipoe.ggj.main.rooms;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.interact.BackgroundDoor;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.GameRoom;
import com.episkipoe.ggj.main.Main;

public class NextLevelRoom extends Room {
	public NextLevelRoom() { 
		setBackground("NextLevel.png");
		addDrawable(new BackgroundDoor(new Point(0,0), new Point(Game.canvasWidth, Game.canvasHeight), GameRoom.class));
	}
	
	public void onEnter() {
		Main.gotoLevel(Main.level+1);
	}
}
