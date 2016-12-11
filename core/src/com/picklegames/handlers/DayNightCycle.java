package com.picklegames.handlers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.picklegames.game.FishGame;

public class DayNightCycle {
	
	private Texture cycleCircle;
	private TextureRegion cycleTexR;
	private SpriteBatch batch;
	private float x, y;
	
	public float cycleRotation;
	
	public DayNightCycle(float tempX, float tempY, SpriteBatch sBatch) {
		FishGame.res.loadTexture("images/testcircle.png", "cycle");
		
		cycleCircle = FishGame.res.getTexture("cycle");
		cycleTexR = new TextureRegion(cycleCircle);		
		batch = sBatch;
		x = tempX;
		y = tempY;
		batch.draw(cycleTexR, (float)x, (float)y, (float)cycleCircle.getWidth()/2, (float)cycleCircle.getHeight()/2, cycleCircle.getWidth(), cycleCircle.getHeight(), 1.5f, 1.5f, 0);
	}
	
	public void renderCycle(float rotation) {
		batch.draw(cycleTexR, (float)x, (float)y, (float)cycleCircle.getWidth()/2, (float)cycleCircle.getHeight()/2, cycleCircle.getWidth(), cycleCircle.getHeight(), 1.5f, 1.5f, rotation);
	}
	
}
