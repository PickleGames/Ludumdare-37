package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;

// Miguel Garnica
// Dec 10, 2016
public class Fish extends Entity {

	private Texture tex;
	private TextureRegion[] texR;

	public Fish() {
		super();
		init();
	}

	public Fish(Body body) {
		super(body);
		init();
	}

	public void init() {

		FishGame.res.loadTexture("images/fish1.png", "fish");
		tex = FishGame.res.getTexture("fish");

		texR = TextureRegion.split(tex, 250, 250)[0];
		setAnimation(texR, 1 / 12);

	}

	@Override
	public void update(float dt) {
		super.update(dt);
		getAnimation().update(dt);
	}

	@Override
	public void render(SpriteBatch batch) {

		batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
				getWorldPosition().y - getHeight() / 2, getWidth(), getHeight());
	}

	public void swim() {

	}
}
