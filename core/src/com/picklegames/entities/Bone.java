package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Boundary;

// Miguel Garnica
// Dec 12, 2016
public class Bone extends Entity {

	private Texture tex;
	private TextureRegion texR;

	public Bone() {
		super();
		init();
	}

	public Bone(Body body, Boundary bound) {
		super(body, bound);
		init();
	}

	public void init() {

		FishGame.res.loadTexture("images/fish_bone.png", "bone");
		tex = FishGame.res.getTexture("bone");

		texR = new TextureRegion(tex);
		setAnimation(texR, 0);
	}

	@Override
	public void update(float dt) {
		drop();
	}

	@Override
	public void render(SpriteBatch batch) {

		if (getBody() != null) {
			batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
					getWorldPosition().y - getHeight() / 2, getWidth(), getHeight());
		}
	}
	
	float veloY;
	float veloX;
	public void drop(){
		
		if(getWorldPosition().y < 50){
			veloY = 0;
			veloX = 0;
		}else{
			veloX = (float) Math.random() * 2 -1;
			veloY = -.6f;
		}
		getBody().setLinearVelocity(veloX, veloY );
	}

}
