package com.episkipoe.ggj.main.snake;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;

public class SnakeTail extends ImageDrawable {
	public SnakeTail() { 
		setFilename("SnakeTail-Right.png");
	}
	
	public void setMove(Point moveVector) {
		if(moveVector==null) return;
		if(moveVector.x>1) {
			setFilename("SnakeTail-Right.png");
		} else if(moveVector.x<-1) {
			setFilename("SnakeTail-Left.png");
		} else if(moveVector.y < -1) {
			setFilename("SnakeTail-Up.png");
		} else if(moveVector.y > 1) {	
			setFilename("SnakeTail-Down.png");
		}
	}

	@Override public void click() throws Exception { }

}
