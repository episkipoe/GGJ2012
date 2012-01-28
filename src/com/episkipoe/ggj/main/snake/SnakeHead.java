package com.episkipoe.ggj.main.snake;

import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.ggj.main.Color;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;

public class SnakeHead extends ImageDrawable {
	public Point previousMove;
	public SnakeHead() { 
		setFilename("SnakeHead.png");
	}
	@Override
	public void click() throws Exception { }

	private Color color;
	public void setColor(Color color) { this.color = color; }
	public void postDraw(Context2d context) {
		if(color==null) return;
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
