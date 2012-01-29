package com.episkipoe.ggj.main.snake;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.Drawable;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.common.draw.TextUtils;
import com.episkipoe.ggj.main.Color;
import com.episkipoe.ggj.main.Main;
import com.episkipoe.ggj.main.bonus.Bonus;
import com.episkipoe.ggj.main.bonus.GoldenEgg;
import com.episkipoe.ggj.main.bonus.RottenEgg;
import com.episkipoe.ggj.main.food.Egg;
import com.episkipoe.ggj.main.food.Food;
import com.episkipoe.ggj.main.rooms.GameOverRoom;
import com.episkipoe.ggj.main.rooms.NextLevelRoom;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Timer;

public class Snake extends ImageDrawable {
	private SnakeHead head = new SnakeHead();
	private SnakeTail tail = new SnakeTail();
	private List<SnakeBody> bodyList = new ArrayList<SnakeBody>();
	
	private int sequenceToClear=3;
	private int movementDelay =500;
	
	private int sectionsTilNextLevel=4;
	public int getSectionsTilNextLevel() { return sectionsTilNextLevel; }
	
	Timer moveTimer;
	public Snake() {
		moveTimer = new Timer() {
	    	@Override public void run() {
	    		move();
	    		moveTimer.schedule(movementDelay);
	    	}
		};
		moveTimer.schedule(movementDelay);
	}

	/**
	 * Called every time a game or level starts
	 */
	public void reset() {
		bodyList.clear();
		sectionsTilNextLevel = 1 + Main.level*2;
		int width = new SnakeBody().getWidth();
		if(width<=0) width = 64; 
		int x = width*sectionsTilNextLevel;
		int y = (int) (Game.canvasHeight*0.5);
		nextMove=moveRight;
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
		for(SnakeBody body : getBodyParts()) {
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
		Point newLoc = applyMovementToPoint(getLocation(), nextMove);
		setLocation(newLoc);
		head.setLocation(newLoc);

		for(SnakeBody body : getBodyParts()) {
			newLoc = nextLoc;
			nextLoc = body.getLocation();
			body.setLocation(newLoc);
		}
		tail.setLocation(nextLoc);		
	}
	
	private Point applyMovementToPoint(Point location, Point moveVector) {
		Point newLocation = location.add(nextMove);
		if(newLocation.x < 0) newLocation.x = Game.canvasWidth;
		if(newLocation.x > Game.canvasWidth) newLocation.x = 0;
		if(newLocation.y < 0) newLocation.y = Game.canvasHeight;
		if(newLocation.y > Game.canvasHeight) newLocation.y = 0;	
		return newLocation;
	}
	
	public void move() {
		if(Main.paused) return;
		movementDelay = 2000/bodyList.size();
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
		else if(d instanceof SnakeTail) {
			handleEatingTail(); 
			return;
		}
		else if(d instanceof Food) {
			Food f = (Food) d;
			handleEatingFood(f); 
			return;
		}
		else if(d instanceof GoldenEgg) {
			Bonus b = (Bonus) d;
			handleEatingGoldenEgg(b);
			return;
		}
		else if(d instanceof RottenEgg) {
			Bonus b = (Bonus) d;
			handleEatingRottenEgg(b);
			return;
		}
		
		//Obstacle, etc?
	}

	private void handleEatingGoldenEgg(Bonus b) {
		handleEatingFood(new Egg(bodyList.get(bodyList.size()-1).getColor()));
		Game.room.removeDrawable(b);
	}
	
	private void handleEatingRottenEgg(Bonus b) {
		bodyList.remove(bodyList.size()-1);
		Game.room.removeDrawable(b);
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
		if(bodyList.isEmpty()) return null;
		return bodyList.get(bodyList.size()-1);
	}
	
	private boolean sequenceComplete() {
		Color colorToCheck=getActiveBody().getColor();
		int colorsPresent=0;
		for(SnakeBody body : getBodyParts()) {
			if(body.getColor().equals(colorToCheck)) {
				colorsPresent++;
			} else {
				return (colorsPresent>=sequenceToClear);
			}
		}
		return (colorsPresent>=sequenceToClear);
	}
	
	List<SnakeBody> getBodyParts() {
		List<SnakeBody> parts = new ArrayList<SnakeBody>();
		for(int i = bodyList.size()-1 ; i >=0 ; i--) parts.add(bodyList.get(i));
		return parts;
	}
	
	private void removeSequence() {
		Color colorToRemove=getActiveBody().getColor();
		List<SnakeBody> toRemove = new ArrayList<SnakeBody>();
		for(SnakeBody body : getBodyParts()) {
			if(!body.getColor().equals(colorToRemove)) { break ; }
			toRemove.add(body);
		}
		bodyList.removeAll(toRemove);
	}
	
	private void handleEatingFood(Food food) {
		if(bodyList.isEmpty()) {
			bodyList.add(new SnakeBody(food.getColor()));
			return;
		}
		
		SnakeBody newSegment = new SnakeBody(food.getColor());
		newSegment.setLocation(head.getLocation());
		head.setColor(food.getColor());
		bodyList.add(newSegment);
		Point newLoc = head.getLocation().add(nextMove);
		setLocation(newLoc);
		head.setLocation(newLoc);
	
		completeSequence();

		Game.room.removeDrawable(food);
	}
	
	private void completeSequence() {
		if(sequenceComplete()) {
			removeSequence();
			if(bodyList.isEmpty()) {
				Game.switchRoom(NextLevelRoom.class);
				return;
			}	
			head.setColor(getActiveBody().getColor());
		}
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
		for(SnakeBody body : getBodyParts()) {
			body.draw(context);
		}
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
