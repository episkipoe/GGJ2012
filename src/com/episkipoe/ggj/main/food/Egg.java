package com.episkipoe.ggj.main.food;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.episkipoe.common.draw.ImageDrawable;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;

public class Egg extends ImageDrawable {
	int speedX, speedY;
	int finalY;	
	
	public Egg() { 
		setFilename("Egg.png");

		if(getImageElement() == null) return;
		int width = getImageElement().getWidth();
		int numColumns = Game.canvasWidth / width;
		int column = Random.nextInt(numColumns);
		int x = (int) (column*width + width*0.5);

		int y = 0;
		setLocation(new Point(x,y));
		speedX=0;
		if(Random.nextBoolean()) speedX *= -1;
		speedY=Random.nextInt(8)+2;
		finalY=(int) (Random.nextInt((int)(Game.canvasHeight*0.5))+Game.canvasHeight*0.4);
	}

	@Override
	public void click() throws Exception {

	}
	
	public void postDraw(Context2d context) {
		if(getLocation().y<=finalY) move(speedX,speedY);
	}

}
