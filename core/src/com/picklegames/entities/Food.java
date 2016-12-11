package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;

// Miguel Garnica
// Dec 10, 2016
public class Food extends Entity {

	private TextureRegion texR;
	private Texture tex;

	public Food() {
		super();
		init();
	}

	public Food(Body body) {
		super(body);
		init();
	}

	public void init() {

		FishGame.res.loadTexture("images/Food.png", "food");
		tex = FishGame.res.getTexture("food");

		texR = new TextureRegion(tex);

	}

	@Override
	public void update(float dt) {

	}

	@Override
	public void render(SpriteBatch batch) {
		if (getBody() != null) {
			batch.draw(texR, getWorldPosition().x - getWidth() / 2, getWorldPosition().y - getHeight() / 2);
		}
	}
	
	public void drop(){
		//getBody().
	}

}
