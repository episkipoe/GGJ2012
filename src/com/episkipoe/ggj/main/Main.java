package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {
	@Override
	public void onModuleLoad() {
		System.out.println("onModuleLoad");
		Game.begin(new GameStorage());
	}
}
