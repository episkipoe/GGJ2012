package com.episkipoe.ggj.main;

import java.util.Arrays;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.common.interact.BackgroundDoor;
import com.episkipoe.common.rooms.Room;
import com.episkipoe.ggj.main.bonus.GoldenEgg;
import com.episkipoe.ggj.main.bonus.RottenEgg;
import com.episkipoe.ggj.main.food.Egg;
import com.episkipoe.ggj.main.food.Food;
import com.episkipoe.ggj.main.rooms.MainRoom;
import com.episkipoe.ggj.main.snake.Snake;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Timer;

public class GameRoom extends Room {
	private Timer foodSpawnTimer;
	private Snake snake;
	public GameRoom() { 
		setBackground("GameRoom.png");
		addDrawable(new BackgroundDoor(new Point(730, 0), new Point(800,44), MainRoom.class));
		foodSpawnTimer = new Timer() {
	    	@Override public void run() {
	    		foodSpawnTimer.schedule(1000+2000*Random.nextInt(4));
	    		if(getFoodCount()>20 || !inside() || Main.paused) {
	    			return ;
	    		}
	    		if(Random.nextInt(100) < 5) {
	    			addDrawable(new RottenEgg());
	    		}
	    		if(Random.nextInt(100) < 5) {
	    			addDrawable(new GoldenEgg());
	    		}	
	    		addDrawable(new Egg(Color.getRandomColor()));
	    		if(Random.nextBoolean()) addDrawable(new Egg(Color.getRandomColor()));
	    	}
		};
		snake = new Snake();
		snake.reset();
		Game.setKeypressHandler(new SnakeKeyboardHandler(snake));
		Game.setMouseMode(new SnakeMouseMode(snake));
		addDrawable(snake);
		foodSpawnTimer.schedule(1000);	
	}
	
	private int getFoodCount() {
		int count=0;
		for(Drawable d: getDrawables()) {
			if(d instanceof Food) count++;
		}
		return count;
	}
	
	public void reset() {
		snake.reset();
		for(Drawable d: getDrawables()) {
			if(d instanceof Food) {
				removeDrawable(d);
			}
		}
	}

	private void drawHUD(Context2d context) {
		/*
		String msg = "Level " + Main.level;
		TextUtils.drawWhiteText(context, Arrays.asList(msg), new Point(50,100));
		*/
		
		/*
		msg = "Meals until you eat your tail: " + snake.getSectionsTilNextLevel();
		TextUtils.drawWhiteText(context, Arrays.asList(msg), new Point(50,120));
		*/
	}
	
	@Override
	public void postDraw(Context2d context) {
		drawHUD(context);
		if(Main.paused) {
			String msg = "Paused";
			TextUtils.drawBlackText(context, Arrays.asList(msg),  new Point(Game.canvasWidth*0.5, Game.canvasHeight*0.5));	
			return;
		}
		
		for(Drawable d : getDrawables()) {
			snake.interactWith(d);
		}
	}

	@Override
	public void onEnter() { }
	
	@Override
	public void onExit() { }
	
	public static boolean inside() {
		return (Game.room != null && Game.room instanceof GameRoom);
	}
}
