package com.episkipoe.ggj.main.rooms;

import java.util.Arrays;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.interact.BackgroundAction;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.common.sound.SoundUtils;
import com.episkipoe.ggj.main.GameRoom;

public class MainRoom extends Room {
	public MainRoom() {
		setBackground("Loading.png");
	}

	@Override
	public void onLoad() {
		setBackground("Main.png");
		addDrawable(new BackgroundAction(new Point(0,0), new Point(Game.canvasWidth, Game.canvasHeight), new ClickLogo()));
	}
	
	private class ClickLogo implements Runnable {
		@Override public void run() {
			TextUtils.growl(Arrays.asList("Welcome"));
			SoundUtils.play("victory.wav");
			Game.switchRoom(GameRoom.class);
		}
	}
}
