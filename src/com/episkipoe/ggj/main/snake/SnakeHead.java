package com.episkipoe.ggj.main.snake;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.ggj.main.Color;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;

public class SnakeHead extends ImageDrawable {
	public Point previousMove;
	public SnakeHead() { 
		setFilename("SnakeHead-Right.png");
	}
	@Override
	public void click() throws Exception { }
	
	public void setMove(Point moveVector) {
		boolean open = Random.nextBoolean();
		if(moveVector.x > 1) {
			if(open) {
				setFilename("SnakeHead-Right-Open.png");
			} else {
				setFilename("SnakeHead-Right.png");
			}
		} else if(moveVector.x < -1) {
			if(open) {
				setFilename("SnakeHead-Left-Open.png");
			} else {
				setFilename("SnakeHead-Left.png");
			}
		} else if(moveVector.y < -1) {
			setFilename("SnakeHead-Up.png");
		} else if(moveVector.y > 1) {
			setFilename("SnakeHead-Down.png");
		}
	}

	@SuppressWarnings("unused")
	private Color color;
	public void setColor(Color color) { this.color = color; }
	public void postDraw(Context2d context) {

	}
}
