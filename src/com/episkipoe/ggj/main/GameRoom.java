package com.episkipoe.ggj.main;

import java.util.Arrays;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.food.Egg;
import com.episkipoe.ggj.main.snake.Snake;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

public class GameRoom extends Room {
	private Timer foodSpawnTimer;
	private Snake snake;
	public GameRoom() { 
		foodSpawnTimer = new Timer() {
	    	@Override public void run() {
	    		addDrawable(new Egg());
	    		foodSpawnTimer.schedule(1000+2000*Random.nextInt(4));
	    	}
		};
		snake = new Snake();
		Game.setKeypressHandler(new SnakeKeyboardHandler(snake));
		Game.setMouseMode(new SnakeMouseMode(snake));
		addDrawable(snake);
	}
	
	public void reset() {
		snake.reset();
	}

	private void drawHUD(Context2d context) {
		String msg = "Level " + Main.level;
		TextUtils.drawWhiteText(context, Arrays.asList(msg), new Point(50,100));
		
		/*
		msg = "Meals until you eat your tail: " + snake.getSectionsTilNextLevel();
		TextUtils.drawWhiteText(context, Arrays.asList(msg), new Point(50,120));
		*/
	}
	
	@Override
	public void postDraw(Context2d context) {
		drawHUD(context);
		if(Main.paused) return;
		
		snake.checkForSelfCollision();
		for(Drawable d : getDrawables()) {
			snake.interactWith(d);
		}
	}

	@Override
	public void onEnter() {
		foodSpawnTimer.schedule(1000);	
		snake.reset();
	}
	
	@Override
	public void onExit() {
		foodSpawnTimer.cancel();
	}
}
