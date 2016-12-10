package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;

// Miguel Garnica
// Dec 10, 2016
public class Fish extends Entity{
	
	private Texture tex;
	private TextureRegion[] texR;
	
	public Fish(){
		super();
		init();
	}
	
	public Fish(Body body){
		super(body);
		init();
	}
	
	public void init(){
		
		FishGame.res.loadTexture("textureFilePath", "fish");
		tex = FishGame.res.getTexture("fish");
		
		texR = TextureRegion.split(tex, 32, 32)[0];
		setAnimation(texR, 1/12);
		
		setWidth(getAnimation().getFrame().getRegionWidth());
		setHeight(getAnimation().getFrame().getRegionHeight());
		
		
	}
	
	@Override
	public void update(float dt){
		
	}
	
	@Override
	public void render(SpriteBatch batch){
		
	}
}
