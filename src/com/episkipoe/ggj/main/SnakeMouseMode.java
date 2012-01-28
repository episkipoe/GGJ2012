package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.interact.MouseMode;
import com.episkipoe.ggj.main.snake.Snake;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;

public class SnakeMouseMode extends MouseMode {
	private Snake snake;
	public SnakeMouseMode(Snake snake) { 
		this.snake = snake;
	}
	public MouseDownHandler mouseDownHandler() {
		return new MouseDownHandler() { 
			@Override
			public void onMouseDown(MouseDownEvent event) {
				Point loc = Game.getPointFromEvent(event);
				Point snakeLoc = snake.getLocation();
				float deltaX = loc.x - snakeLoc.x;
				float deltaY = loc.y - snakeLoc.y;
				float moveX=0, moveY=0;
				if(deltaX > 50) {
					moveX = Snake.moveDistance;
				} else if(deltaX < -50) {
					moveX = -Snake.moveDistance;
				}
				if(deltaY > 50) {
					moveY = Snake.moveDistance;
				} else if(deltaY < -50) {
					moveY = -Snake.moveDistance;
				}	
				snake.setNextMove(new Point(moveX, moveY));
				try {
					Game.click(loc);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};	
	}
}
