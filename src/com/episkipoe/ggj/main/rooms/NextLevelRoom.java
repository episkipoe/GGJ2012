package com.episkipoe.ggj.main.rooms;

import java.util.Arrays;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.dialog.DialogElement;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.interact.BackgroundDoor;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.GameRoom;
import com.episkipoe.ggj.main.Main;
import com.google.gwt.canvas.dom.client.Context2d;

public class NextLevelRoom extends Room {
	public NextLevelRoom() { 
		setBackground("NextLevel.png");
		addDrawable(new BackgroundDoor(new Point(0,0), new Point(Game.canvasWidth, Game.canvasHeight), GameRoom.class));
	}
	
	public void onEnter() {
		Main.gotoLevel(Main.level+1);
	}
	
	public void postDraw(Context2d context) {
		String msg = "Level " + Main.level + " ";
		TextUtils.drawBlackText(context, Arrays.asList(msg),  new Point(Game.canvasWidth*0.5, Game.canvasHeight*0.5));
	}
}
