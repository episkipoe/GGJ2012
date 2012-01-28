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
		sectionsTilNextLevel = 5 + Main.level*2;
		int width = new SnakeBody().getWidth();
		if(width<=0) width = 64; 
		int x = width*sectionsTilNextLevel;
		int y = (int) (Game.canvasHeight*0.5);
		setLocation(new Point(x, y));
		head.setLocation(getLocation());
		for(int i = 0; i < sectionsTilNextLevel; i++) {
			x-=width;
			SnakeBody body = new SnakeBody();
			body.setLocation(new Point(x, y));
			bodyList.add(body);
		}
		x-=width;
		tail.setLocation(new Point(x,y));
		head.setColor(getActiveBody().getColor());
		System.out.println("reset: " + getActiveBody().getColor().name);
	}

	public static final int moveDistance=64;
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

	@SuppressWarnings("unused")
	private void followMove() {
		if(nextMove==null) return ;
		Point moveVector = head.previousMove;
		Point newLoc = getLocation().add(nextMove);
		setLocation(newLoc);
		head.setLocation(newLoc);
		head.previousMove=nextMove;
		for(SnakeBody body : bodyList) {
			if(moveVector != null) {
				newLoc =  body.getLocation().add(moveVector);
				body.setLocation(newLoc);
			}
			Point tmpMove=body.previousMove;
			body.previousMove=moveVector;
			moveVector = tmpMove;
		}
		if(moveVector != null) {
			newLoc =  tail.getLocation().add(moveVector);
			tail.setLocation(newLoc);
		}
	}
	private void shiftMove() { 
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
	}
	public void move() {
		if(Main.paused) return;
		shiftMove();
		//nextMove=null;		
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
			TextUtils.growl(Arrays.asList("Wrong color.  You need to eat " + activeBody.getColor().name + " food"));
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
	
	public void checkForSelfCollision() {
		int sectionIdx=0;
		for(SnakeBody body : bodyList) {
			sectionIdx++;
			if(sectionIdx<=2)continue;
			if(body.intersectsWith(head)) {
				eat(body); 
			}
		}
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
