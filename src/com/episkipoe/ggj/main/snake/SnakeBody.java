package com.episkipoe.ggj.main.snake;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.ggj.main.Color;
import com.episkipoe.ggj.main.food.Food;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

public class SnakeBody extends ImageDrawable {
	private Color color;
	public Color getColor() { return color; }
	public Point previousMove;
	
	private Food food=null;
	public boolean full() { return (food != null); }
	
	public SnakeBody() { 
		setFilename("SnakeBody.png");
		color = Color.getRandomColor();
	}
	public SnakeBody(Color color) {
		setFilename("SnakeBody.png");
		this.color = color;
	}
	
	@Override
	public void click() throws Exception { }

	public boolean colorMatches(Food food) {
		return food.getColor().equals(color);
	}
	public void eat(Food food) {
		if(colorMatches(food)) this.food = food;
	}
	public void postDraw(Context2d context) {
		if(food != null) {
			food.setLocation(getLocation());
			food.draw(context);
		} else {
			context.setFillStyle(color.value);
			context.setGlobalAlpha(0.4);
			Point loc = getLocation();
			ImageElement img = getImageElement();
			int width=img.getWidth();
			int height=img.getHeight();
			context.fillRect(loc.x-width*0.5, loc.y-height*0.5, width, height);
			context.setGlobalAlpha(1.0);
		}
	}

}
