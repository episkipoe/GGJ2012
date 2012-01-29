package com.episkipoe.ggj.main.bonus;

import com.episkipoe.common.Game;
import com.episkipoe.common.Point;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.user.client.Random;

public class RottenEgg extends Bonus {
	int speedX, speedY;
	int finalY;
	
	public RottenEgg() {
		if(getImageElement() == null) return;
		
		speedX=Random.nextInt(10)+5;
		speedY=Random.nextInt(10)+5;
		int y = 0;
		int x;
		if(Random.nextBoolean()){//Left to right
			x = 0;
		} else {//Right to Left
			x = Game.canvasWidth;
			speedX *= -1;
		}
		
		setLocation(new Point(x,y));
	}

	@Override
	public void click() throws Exception {}

	@Override
	public void postDraw(Context2d context) {
		if(eaten) return;
		if(getLocation().y > Game.canvasHeight) {
			Game.room.removeDrawable(this);
			return;
		}
		move(speedX,speedY);
	}

}
