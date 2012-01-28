package com.episkipoe.ggj.main;

import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.food.Egg;
import com.episkipoe.ggj.main.snake.Snake;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

public class GameRoom extends Room {
	private Timer timer;
	public GameRoom() { 
		timer = new Timer() {
	    	@Override public void run() {
	    		addDrawable(new Egg());
	    		timer.schedule(1000+2000*Random.nextInt(4));
	    	}
		};
		addDrawable(new Snake());
		
	}

	@Override
	public void onEnter() {
		timer.schedule(1000);	
	}
	
	@Override
	public void onExit() {
		timer.cancel();
	}
	
	@Override
	public boolean showHud() { return false; }
}
