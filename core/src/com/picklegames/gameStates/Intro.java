package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.picklegames.game.FishGame;
import com.picklegames.managers.GameStateManager;

// Miguel Garnica
// Dec 11, 2016
public class Intro extends GameState{
	
	private Texture tex;
	private String[] words;
	private int wordsId = 0;
	
	private BitmapFont font;
	private GlyphLayout layout;
	
	public Intro(GameStateManager gsm) {
		super(gsm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
		FishGame.res.loadTexture("images/intro1.png", "intro");
		tex = FishGame.res.getTexture("intro");
		
		words = new String[3];
		words[0] = "Oh no, LudumDare 37 is tommorrow";
		words[1] = "My owner is going to work nonstop";
		words[2] = "I hope he doesn't forget to feed us";
		
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(3);
		
		layout = new GlyphLayout();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}
	
	float timeElap = 0;
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		timeElap+= dt;
		if(timeElap > 4f){
			if(wordsId < words.length - 1){
				wordsId++;
			}else{
				FlashScreen.day = 1;
				gsm.setState(GameStateManager.FLASH);
			}
			timeElap = 0;
		}
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
		batch.draw(tex, 0, 0, cam.viewportWidth, cam.viewportHeight);
		
		layout.setText(font, words[wordsId]);
		font.draw(batch, words[wordsId], cam.viewportWidth/2 - layout.width/2, layout.height + 50);
	}

	@Override
	public void dispose() {
		FishGame.res.getMusic("menu").stop();
		
	}

}
