package com.episkipoe.ggj.main;

import com.episkipoe.ggj.main.snake.Snake;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;

class SnakeKeyboardHandler implements KeyPressHandler {
	Snake snake;
	SnakeKeyboardHandler(Snake snake) {
		this.snake = snake;
	}
	
	@Override
	public void onKeyPress(KeyPressEvent event) {
		if(event.getCharCode() == 'w'
		|| event.getCharCode() == '.') { 
			snake.moveUp();
		} else if(event.getCharCode() == 'a'
		|| event.getCharCode() == 'o') { 
			snake.moveLeft();
		} else if(event.getCharCode() == 's'
		|| event.getCharCode() == 'e') { 
			snake.moveDown();
		} else if(event.getCharCode() == 'd'
		|| event.getCharCode() == 'u') { 
			snake.moveRight();
		} else if(event.getCharCode() == 'p') {
			Main.paused = !Main.paused;
		} 
	}

}


