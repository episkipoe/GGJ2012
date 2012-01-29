package com.episkipoe.ggj.main.rooms;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.interact.BackgroundDoor;
import com.episkipoe.common.rooms.Room;

public class OptionRoom extends Room {
	public OptionRoom() { 
		addDrawable(new BackgroundDoor(new Point(0,0), new Point(Game.canvasWidth, Game.canvasHeight), MainRoom.class));
	}
}
