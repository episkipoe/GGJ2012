package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.episkipoe.ggj.main.rooms.*;

public class GameStorage implements com.episkipoe.common.GameStorage {

	@Override
	public String[] getCommonImages() {
		String[] imgs = {"Loading.png", "Main.png", "SnakeHead-Left.png", "SnakeHead-Left-Open.png",
		"SnakeHead-Right.png", "SnakeHead-Right-Open.png", "SnakeHead-Down.png", "SnakeHead-Up.png",
		"SnakeBody-Horizontal.png", "SnakeBody-Vertical.png", 
		"SnakeTail-Left.png", "SnakeTail-Right.png",
		"BlueEgg.png", "OrangeEgg.png", "GreenEgg.png", "MagentaEgg.png", "RedEgg.png", "YellowEgg.png"
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
		Game.registerRoom(CreditRoom.class, new CreditRoom());
		Game.registerRoom(HowToPlay.class, new HowToPlay());
		Game.registerRoom(OptionRoom.class, new OptionRoom());
		if(!loadGame()) newGame(); 
	}


}
