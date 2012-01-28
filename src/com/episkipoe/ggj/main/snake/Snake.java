package com.episkipoe.ggj.main.snake;

import java.util.ArrayList;
import java.util.List;

import com.episkipoe.common.draw.ImageDrawable;
import com.google.gwt.canvas.dom.client.Context2d;

public class Snake extends ImageDrawable {
	private SnakeHead head = new SnakeHead();
	private SnakeTail tail = new SnakeTail();
	private List<SnakeBody> bodyList = new ArrayList<SnakeBody>();
	
	public Snake() { 
		
	}
	
	@Override
	public void click() throws Exception { }

	public void postDraw(Context2d context) {
		head.draw(context);
		for(SnakeBody body : bodyList) body.draw(context);
		tail.draw(context);
	}
	
}
