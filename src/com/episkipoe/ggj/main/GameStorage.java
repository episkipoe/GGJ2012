package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.episkipoe.common.player.MovePlayer;
import com.episkipoe.ggj.main.rooms.*;

public class GameStorage implements com.episkipoe.common.GameStorage {

	@Override
	public String[] getCommonImages() {
		String[] imgs = {"Main.png", "SnakeHead.png", "SnakeTail.png", "SnakeTail.png",
		"BlueEgg.png", "CyanEgg.png", "Egg.png", "GreenEgg.png", "MagentaEgg.png", "RedEgg.png", "YellowEgg.png"
	};
		return imgs;
	}

	@Override
	public boolean loadGame() throws Exception {
		return false;
	}

	@Override
	public void newGame() throws Exception {
		Game.switchRoom(MainRoom.class);
		Game.setMouseMode(new MovePlayer());
	}

	@Override
	public void saveGame() {

	}

	@Override
	public void startup() throws Exception {
		Game.registerRoom(MainRoom.class, new MainRoom());
		Game.registerRoom(GameRoom.class, new GameRoom());
		Game.registerRoom(GameOverRoom.class, new GameOverRoom());
		Game.registerRoom(NextLevelRoom.class, new NextLevelRoom());
		if(!loadGame()) newGame(); 
	}


}
