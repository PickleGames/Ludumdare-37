package com.picklegames.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.picklegames.entities.Fish;
// Harris Yun, Miguel Garnica
// Dec 10, 2016
public class HUD {

	private Fish fish;
	private Texture outEnergy;
	private Texture inEnergy;
	private Sprite flashy;
	private float energy;
	
	public HUD(Fish fish) {
		this.fish = fish;
		outEnergy = new Texture("images/outHealth.png");
		inEnergy = new Texture("images/inhealth.png");
		flashy = new Sprite(new Texture("images/red.png"));
		flashy.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		energy = 180;
	}

	float a;
	public void update(float dt){
		energy = 180 * (fish.getEnergy() / 100);
		if(fish.getEnergy() < fish.getMAX_ENERGY() * .2f){
			flashy.setAlpha(1 - (fish.getHealth() / fish.getMAX_HP()));
		}
	}
	
	public void renderHUD(SpriteBatch batch) {
		
		batch.draw(outEnergy, 0, 0);
		batch.draw(inEnergy, 10, 10, energy, 30);
		
		if(fish.getEnergy() < 5){
			flashy.draw(batch);
		}
	}
}
