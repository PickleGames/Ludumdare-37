package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.picklegames.managers.GameStateManager;

public class FlashScreen extends GameState{
	
	private BitmapFont font;
	public static int day = 1;
	
	public GlyphLayout layout;
	
	public FlashScreen(GameStateManager gsm) {
		super(gsm);
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(20);
		
		layout = new GlyphLayout();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		layout.setText(font, "DAY " + day);
		font.draw(batch, "DAY " + day, cam.viewportWidth/2 - layout.width/2, cam.viewportHeight/2 + layout.height /2);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
