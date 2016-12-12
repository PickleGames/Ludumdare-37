package com.picklegames.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Boundary;

// Miguel Garnica
// Dec 10, 2016
public class Food extends Entity {

	private TextureRegion texR;
	private Texture tex;
	private BitmapFont font;
	
	public Food() {
		super();
		init();
	}

	public Food(Body body, Boundary bound) {
		super(body, bound);
		init();
	}

	public void init() {
		font = new BitmapFont();
		FishGame.res.loadTexture("images/Food.png", "food");
		tex = FishGame.res.getTexture("food");

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
			batch.draw(texR, getWorldPosition().x - getWidth() / 2, getWorldPosition().y - getHeight() / 2);
			font.draw(batch, "food pos: "  + getWorldPosition(), getWorldPosition().x, getWorldPosition().y);
		}
	}
	
	float veloY = -.45f;
	public void drop(){
		if(getWorldPosition().y < 50){
			veloY = 0;
		}
		getBody().setLinearVelocity((float) Math.random() * 2 -1, veloY );
	}

}
