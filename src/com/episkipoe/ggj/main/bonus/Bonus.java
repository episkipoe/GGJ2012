package com.episkipoe.ggj.main.bonus;

import com.episkipoe.common.draw.ImageDrawable;

public abstract class Bonus extends ImageDrawable {
	protected boolean eaten=false;
	public void eat() { eaten = true; }
}