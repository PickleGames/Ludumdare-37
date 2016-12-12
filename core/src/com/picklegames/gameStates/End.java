package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.picklegames.managers.GameStateManager;

// Miguel Garnica
// Dec 11, 2016
public class End extends GameState{
	
	private BitmapFont font;
	public GlyphLayout layout;
	
	public End(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(15);
		
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
		layout.setText(font, "THE END");
		font.draw(batch, "THE END", cam.viewportWidth/2 - layout.width/2, cam.viewportHeight/2 + layout.height /2);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
