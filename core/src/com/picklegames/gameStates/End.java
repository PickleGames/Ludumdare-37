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
	
	private Texture[] backgrounds;
	private int currentTextureId =1;
	
	public End(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(5);
		layout = new GlyphLayout();
		
		FishGame.res.loadMusic("musics/youdead.mp3", "end");
		FishGame.res.getMusic("end").setLooping(true);
		FishGame.res.getMusic("end").play();
		
		FishGame.res.loadTexture("images/end1.png", "end1");
		FishGame.res.loadTexture("images/end2.png", "end2");
		FishGame.res.loadTexture("images/end3.png", "end3");
		
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
		if(timeElapsed > 4f){
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
	}

	@Override
	public void dispose() {
		font.dispose();
		FishGame.res.getMusic("end").stop();
	}

}
