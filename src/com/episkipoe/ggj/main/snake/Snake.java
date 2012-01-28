package com.episkipoe.ggj.main.snake;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.ggj.main.Main;
import com.episkipoe.ggj.main.food.Food;
import com.episkipoe.ggj.main.rooms.GameOverRoom;
import com.episkipoe.ggj.main.rooms.NextLevelRoom;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.Timer;

public class Snake extends ImageDrawable {
	private SnakeHead head = new SnakeHead();
	private SnakeTail tail = new SnakeTail();
	private Queue<SnakeBody> bodyList = new LinkedList<SnakeBody>();
	
	private int sectionsTilNextLevel=4;
	public int getSectionsTilNextLevel() { return sectionsTilNextLevel; }
	
	Timer moveTimer;
	public Snake() { 
		moveTimer = new Timer() {
	    	@Override public void run() {
	    		move();
	    		moveTimer.schedule(80);
	    	}
		};
		moveTimer.schedule(500);
	}

	/**
	 * Called every time a game or level starts
	 */
	public void reset() {
		bodyList.clear();
		sectionsTilNextLevel = 4 + Main.level*2;
		ImageElement img = getImageElement();
		int width = 16;
		if(img != null) width = img.getWidth();
		int x = width*sectionsTilNextLevel;
		int y = (int) (Game.canvasHeight*0.5);
		setLocation(new Point(x, y));
		for(int i = 0; i < sectionsTilNextLevel; i++) {
			x-=width;
			SnakeBody body = new SnakeBody();
			body.setLocation(new Point(x, y));
			bodyList.add(body);
		}
		x-=width;
		tail.setLocation(new Point(x,y));
		head.setColor(getActiveBody().getColor());
	}

	public static final int moveDistance=20;
	Point moveLeft = new Point(-moveDistance, 0);
	Point moveRight = new Point(moveDistance, 0);
	Point moveUp = new Point(0, -moveDistance);
	Point moveDown = new Point(0, moveDistance);
	
	Point nextMove;
	public void setNextMove(Point nextMove) {
		this.nextMove = nextMove;
	}
	
	public void moveUp() { nextMove=moveUp; }
	public void moveDown() { nextMove=moveDown; }
	public void moveLeft() { nextMove=moveLeft; }
	public void moveRight() { nextMove=moveRight; }
	
	public void move() {
		if(nextMove==null) return ;
		Point nextLoc = getLocation();
		Point newLoc = getLocation().add(nextMove);
		setLocation(newLoc);
		head.setLocation(newLoc);
		for(SnakeBody body : bodyList) {
			newLoc = nextLoc;
			nextLoc = body.getLocation();
			body.setLocation(newLoc);
		}
		tail.setLocation(nextLoc);
		nextMove=null;
	}
	
	public void eat(Drawable d) {
		if(d==null) return;
		if(d instanceof SnakeBody) {
			Game.switchRoom(GameOverRoom.class);
			TextUtils.growl(Arrays.asList("Don't eat your middle"));
			return;
		}
		if(d instanceof SnakeTail) { 
			handleEatingTail(); 
			return;
		}
		if(d instanceof Food) { 
			Food f = (Food) d;
			handleEatingFood(f); 
			return;
		}
		
		//Obstacle, etc?
	}

	/**
	 * The head ran into the tail
	 * 		Go to the next level if you've eaten enough
	 */
	private void handleEatingTail() {
		if(sectionsTilNextLevel<=0) {
			Game.switchRoom(NextLevelRoom.class);
		}
	}

	private SnakeBody getActiveBody() {
		for(SnakeBody body: bodyList) {
			if(body.full()) continue;
			return body;
		}
		return null;
	}
	
	private void handleEatingFood(Food food) {
		if(bodyList.isEmpty()) {
			bodyList.add(new SnakeBody(food.getColor()));
			return;
		}
		SnakeBody activeBody = getActiveBody();
		if(activeBody.colorMatches(food)) {
			activeBody.eat(food);
			food.eat();
			head.setColor(getActiveBody().getColor());
			if(sectionsTilNextLevel>0) sectionsTilNextLevel--;
		} else {
			TextUtils.growl(Arrays.asList("Wrong color"));
			sectionsTilNextLevel++;	
			bodyList.add(new SnakeBody(food.getColor()));
		}		
		Game.room.removeDrawable(food);
	}

	/**
	 * Handle an object intersecting with your body
	 * @param body the segment that got hit
	 * @param d the thing that hit a body segment
	 */
	private void getHitBy(SnakeBody body, Drawable d) {
		//TODO
	}
	
	@Override
	public void click() throws Exception { }

	@Override
	public void postDraw(Context2d context) {
		head.draw(context);
		for(SnakeBody body : bodyList) body.draw(context);
		tail.draw(context);	
	}
	
	public void interactWith(Drawable d) {
		if(d==null) return ;
		if(d instanceof Snake) return;
		if(!(d instanceof ImageDrawable)) return;
		ImageDrawable img = (ImageDrawable) d;  
		if(head.intersectsWith(img)) eat(d);
		for(SnakeBody body : bodyList) if(body.intersectsWith(img)) getHitBy(body, d);
	}
	
}
