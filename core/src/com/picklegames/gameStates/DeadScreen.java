package com.picklegames.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.picklegames.managers.GameStateManager;

public class DeadScreen extends GameState{

	private Texture bg;
	
	public DeadScreen(GameStateManager gsm) {
		super(gsm);
		
	}
	
	@Override
	public void init() {
		bg = new Texture("images/deadscreen.png");
		
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)){
			gsm.setState(GameStateManager.level);
		}
		
	}

	@Override
	public void render() {
		batch.draw(bg, 0, 0, hudCam.viewportWidth, hudCam.viewportHeight);
		
	}

	@Override
	public void dispose() {
		bg.dispose();
		
	}

}
