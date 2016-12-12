package com.picklegames.managers;

import java.util.Stack;

import com.picklegames.game.FishGame;
import com.picklegames.gameStates.End;
import com.picklegames.gameStates.FlashScreen;
import com.picklegames.gameStates.GameState;
import com.picklegames.gameStates.Intro;
import com.picklegames.gameStates.Level1;
import com.picklegames.gameStates.Level2;
import com.picklegames.gameStates.Level3;
import com.picklegames.gameStates.Menu;

// Miguel Garnica
// Dec 9, 2016
public class GameStateManager {
	private FishGame game;
	private Stack<GameState> gameStates;

	public static final int MENU = 123;
	public static final int PLAY = 101;
	public static final int LEVEL1 = 420;
	public static final int LEVEL2 = 360;
	public static final int LEVEL3 = 1337;
	public static final int FLASH = 104;
	public static final int INTRO = 711;
	public static final int END = 6969;

	public static final int DIALOGUE = 411;

	public GameStateManager(FishGame game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		
		pushState(MENU);


	}

	public FishGame game() {
		return game;
	}

	public void update(float dt) {

		gameStates.peek().update(dt);
	}

	public void render() {
		gameStates.peek().render();
	}

	private GameState getState(int state) {

		if (state == MENU) {
			return new Menu(this);
		}else if (state == LEVEL1) {
			return new Level1(this);
		}else if (state == LEVEL2) {
			return new Level2(this);
		}else if (state == LEVEL3) {
			return new Level3(this);
		} else if (state == FLASH) {
			return new FlashScreen(this);
		}else if (state == INTRO) {
			return new Intro(this);
		}else if (state == END) {
			return new End(this);
		}
		return null;

	}

	public void setState(int state) {
		popState();
		pushState(state);
	}

	public void pushState(int state) {
		gameStates.push(getState(state));
	}

	public void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}
}
