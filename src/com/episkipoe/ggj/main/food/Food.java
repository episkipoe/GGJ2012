package com.episkipoe.ggj.main.food;

import com.episkipoe.common.draw.ImageDrawable;
import com.episkipoe.ggj.main.Color;

public abstract class Food extends ImageDrawable {
	protected Color color;
	public Color getColor() { return color; }
	
	protected boolean eaten=false;
	public void eat() { eaten = true; }
}
