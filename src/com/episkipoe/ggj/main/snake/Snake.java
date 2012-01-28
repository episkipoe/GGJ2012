package com.episkipoe.ggj.main.snake;

import java.util.ArrayList;
import java.util.List;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;

public class Snake extends ImageDrawable {
	private SnakeHead head = new SnakeHead();
	private SnakeTail tail = new SnakeTail();
	private List<SnakeBody> bodyList = new ArrayList<SnakeBody>();
	
	Timer moveTimer;
	public Snake() { 
		setLocation(new Point(10, Game.canvasHeight*0.5));
		
		moveTimer = new Timer() {
	    	@Override public void run() {
	    		move();
	    		moveTimer.schedule(500);
	    	}
		};
		moveTimer.schedule(500);
	}

	final int moveDistance=20;
	Point moveLeft = new Point(-moveDistance, 0);
	Point moveRight = new Point(moveDistance, 0);
	Point moveUp = new Point(0, -moveDistance);
	Point moveDown = new Point(0, moveDistance);
	
	Point nextMove;
	
	public void moveUp() { nextMove=moveUp; }
	public void moveDown() { nextMove=moveDown; }
	public void moveLeft() { nextMove=moveLeft; }
	public void moveRight() { nextMove=moveRight; }
	
	public void move() {
		if(nextMove==null) return ;
		Point newLoc = getLocation().add(nextMove);
		//System.out.println("move: " + newLoc.x + ", " + newLoc.y);
		setLocation(newLoc);
		head.setLocation(newLoc);
		for(SnakeBody body : bodyList) body.setLocation(newLoc);
		tail.setLocation(newLoc);
		nextMove=null;
	}
	
	@Override
	public void click() throws Exception { }

	public void postDraw(Context2d context) {
		
		head.draw(context);
		for(SnakeBody body : bodyList) body.draw(context);
		tail.draw(context);
	}
	
}
