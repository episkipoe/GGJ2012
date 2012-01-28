package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	public static int level=0;
	
	@Override
	public void onModuleLoad() {
		Game.begin(new GameStorage());
	}
}
