package com.episkipoe.ggj.main;

import com.episkipoe.common.Game;
import com.episkipoe.common.player.MovePlayer;
import com.episkipoe.ggj.main.rooms.*;

public class GameStorage implements com.episkipoe.common.GameStorage {

	@Override
	public String[] getCommonImages() {
		String[] imgs = {"Loading.png", "Main.png", "SnakeHead.png", "SnakeBody.png", "SnakeTail.png", 
		"BlueEgg.png", "OrangeEgg.png", "Egg.png", "GreenEgg.png", "MagentaEgg.png", "RedEgg.png", "YellowEgg.png"
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
		Game.registerRoom(CreditRoom.class, new CreditRoom());
		Game.registerRoom(HowToPlay.class, new HowToPlay());
		Game.registerRoom(OptionRoom.class, new OptionRoom());
		if(!loadGame()) newGame(); 
	}


}
