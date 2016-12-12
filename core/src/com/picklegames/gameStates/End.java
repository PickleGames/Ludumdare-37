package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.picklegames.game.FishGame;
import com.picklegames.managers.GameStateManager;

// Miguel Garnica
// Dec 11, 2016
public class End extends GameState{
	
	private BitmapFont font;
	public GlyphLayout layout;
	
	private int currentTextureId =1;
	private String[] words;
	
	public End(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		font = new BitmapFont();
		font.getData().setScale(3);
		font.setColor(Color.GOLD);
		layout = new GlyphLayout();
		
		FishGame.res.loadMusic("musics/youdead.mp3", "end");
		FishGame.res.getMusic("end").setLooping(true);
		FishGame.res.getMusic("end").play();
		
		FishGame.res.loadTexture("images/end1.png", "end1");
		FishGame.res.loadTexture("images/end2.png", "end2");
		FishGame.res.loadTexture("images/end3.png", "end3");
		
		words = new String[3];
		words[0] = "Oh no, my owner is dead, the jam killed him";
		words[1] = "This is it for me...";
		words[2] = "Goodbye, cruel world";
		///backgrounds = new Texture[3];
		//backgrounds[];
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	
	float timeElapsed =0;
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		timeElapsed +=dt;
		if(timeElapsed > 5f){
			if(currentTextureId < 3){
				currentTextureId++;
			}
			timeElapsed = 0;
		}
	}
	
	Texture tex;
	@Override
	public void render() {
		// TODO Auto-generated method stub
		layout.setText(font, "THE END");
		
		tex = FishGame.res.getTexture("end" + currentTextureId);
		batch.draw(tex, 0, 0, cam.viewportWidth, cam.viewportHeight);
		if(currentTextureId == 3){
			font.draw(batch, "THE END", cam.viewportWidth/2 + layout.width/2, cam.viewportHeight/2 + layout.height /2);
		}
		layout.setText(font, words[currentTextureId -1]);
		font.draw(batch, words[currentTextureId -1] , cam.viewportWidth/2 - layout.width/2, cam.viewportHeight - layout.height);
	}

	@Override
	public void dispose() {
		font.dispose();
		FishGame.res.getMusic("end").stop();
	}

}
