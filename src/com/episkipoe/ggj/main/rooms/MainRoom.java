package com.episkipoe.ggj.main.rooms;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.interact.BackgroundAction;
import com.episkipoe.common.interact.BackgroundDoor;
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
		addDrawable(new BackgroundAction(new Point(295,292), new Point(521, 340), new ClickLogo()));
		addDrawable(new BackgroundDoor(new Point(295,347), new Point(521, 422), HowToPlay.class));
		addDrawable(new BackgroundDoor(new Point(295,430), new Point(521, 479), OptionRoom.class));
		addDrawable(new BackgroundDoor(new Point(295,495), new Point(521, 550), CreditRoom.class));
	}
	
	private class ClickLogo implements Runnable {
		@Override public void run() {
			SoundUtils.play("victory.wav");
			Game.switchRoom(GameRoom.class);
		}
	}
}
