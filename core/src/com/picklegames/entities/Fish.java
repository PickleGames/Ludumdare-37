package com.picklegames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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
		setAnimation(texR, 1 / 3f);
		setWidth(getWidth() * .65f);
		setHeight(getHeight() * .65f);

	}

	float timer;
	float speedX = 5;
	float rotation = 0;

	@Override
	public void update(float dt) {
		super.update(dt);
		getAnimation().update(dt);
		timer += dt;
		if (timer > 1f) {
			speedX *= -1;
			timer = 0;

		}
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			rotation += 10;
		}
		swim();
	}

	@Override
	public void render(SpriteBatch batch) {

		if (getBody().getLinearVelocity().x > 0) {
			batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
					getWorldPosition().y - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1,
					1, rotation);
		} else {
			batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
					getWorldPosition().y - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
					-1, 1, rotation);
		}

	}

	public void chill() {
		getBody().setLinearVelocity((float) Math.random() - 0.5f, (float) Math.random() - 0.5f);
	}

	public void swim() {

		getBody().setLinearVelocity(speedX, (float) (Math.random() * 2 - 1));
	}
}
