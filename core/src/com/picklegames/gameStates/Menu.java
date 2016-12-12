package com.picklegames.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.picklegames.game.FishGame;
import com.picklegames.managers.GameStateManager;

// Miguel Garnica
// Dec 9, 2016
public class Menu extends GameState{
	
	private Texture tex;
	private BitmapFont font;
	private GlyphLayout layout;
	
	public Menu(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		FishGame.res.loadTexture("images/menu.png", "menu");
		tex = FishGame.res.getTexture("menu");
		
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(5);
		
		layout = new GlyphLayout();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		if(Gdx.input.isKeyPressed(Keys.ENTER)){
			FlashScreen.day +=1;
			gsm.setState(gsm.FLASH);
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.draw(tex, 0, 0, cam.viewportWidth, cam.viewportHeight);
		
		layout.setText(font, "PRESS ENTER BROSKI");
		font.draw(batch, "PRESS ENTER BROSKI", cam.viewportWidth/2 - layout.width/2, cam.viewportHeight - layout.height/2);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
