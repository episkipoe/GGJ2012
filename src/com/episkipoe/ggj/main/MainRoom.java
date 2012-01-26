package com.episkipoe.ggj.main;

import java.util.Arrays;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.interact.BackgroundAction;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.common.sound.SoundUtils;

public class MainRoom extends Room {
	public MainRoom() {
		setBackground("Main.png");
		addDrawable(new BackgroundAction(new Point(276,116), new Point(491, 165), new ClickLogo()));
	}
	private class ClickLogo implements Runnable {
		@Override public void run() {
			TextUtils.growl(Arrays.asList("Welcome"));
			SoundUtils.play("victory.wav");
		}
	}
	
	@Override
	public boolean showHud() { return false; }
}
