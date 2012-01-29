package com.episkipoe.ggj.main.snake;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.ggj.main.Color;
import com.episkipoe.ggj.main.food.Egg;
import com.episkipoe.ggj.main.food.Food;
import com.google.gwt.canvas.dom.client.Context2d;

public class SnakeBody extends ImageDrawable {
	private Color color;
	public Color getColor() { return color; }
	public Point previousMove;
	
	private Food food = null;
	
	public SnakeBody() { 
		setFilename("SnakeBody-Horizontal.png");
		color = Color.getRandomColor();
		food = new Egg(color);
		food.eat();
	}
	
	public SnakeBody(Food food) {
		setFilename("SnakeBody-Horizontal.png");
		this.food = food;
		this.color = food.getColor();
	}
	
	public void setMove(Point moveVector) {
		if(moveVector.y > 1 || moveVector.y < -1) {
			setFilename("SnakeBody-Vertical.png");
		} else {
			setFilename("SnakeBody-Horizontal.png");
		}
	}
	
	@Override
	public void click() throws Exception { }

	public boolean colorMatches(Food food) {
		return food.getColor().equals(color);
	}
	
	public void eat(Food food) {
		if(colorMatches(food)) 
		this.food = food;
	}
	public void postDraw(Context2d context) {
		if(food != null) {
			food.setLocation(getLocation());
			food.draw(context);
		} 
	}

}
