package com.picklegames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.B2DVars;
import com.picklegames.handlers.Boundary;

public abstract class Entity {
	private Body body;
	private Animation animation;
	private float width;
	private float height;
	private boolean clicked;
	private Vector3 mouseVec;
	private Boundary bound;

	
	public Entity() {
		this(null, null);
	}

	public Entity(Body body, Boundary bound) {
		this.body = body;
		this.animation = new Animation();
		this.mouseVec = new Vector3();
		this.bound = bound;
	}
	
	public void setBody(Body body) {
		this.body = body;
	}

	public void setAnimation(TextureRegion reg, float delay) {
		this.setAnimation(new TextureRegion[] { reg }, delay);
	}

	public void setAnimation(TextureRegion[] reg, float delay) {
		this.animation.setFrames(reg, delay);
		this.width = reg[0].getRegionWidth();
		this.height = reg[0].getRegionHeight();
	}

	public void update(float dt) {
		this.animation.update(dt);
		// this.body.setLinearVelocity(this.velocity);
		mouseVec.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		FishGame.hudCam.unproject(mouseVec);
		
		if (Gdx.input.isButtonPressed(Buttons.LEFT) && mouseVec.x > getWorldPosition().x - width / 2
				&& mouseVec.x  < getWorldPosition().x + width / 2
				&& mouseVec.y  > getWorldPosition().y - height / 2
				&& mouseVec.y  < getWorldPosition().y + height / 2) {
			clicked = true;

		} else {
			clicked = false;
		}
	}

	public void render(SpriteBatch batch) {
		try {
			if (animation.getFrame() != null) {
				batch.begin();
				batch.draw(this.animation.getFrame(), this.body.getPosition().x * B2DVars.PPM - this.width / 2,
						this.body.getPosition().y * B2DVars.PPM - this.height / 2);
				batch.end();
			}
		} catch (NullPointerException e) {
			// System.out.println(e.getMessage());
		}
	}

	public boolean isClicked(){
		return clicked;
	}

	public Vector2 getVelocity() {
		return getBody().getLinearVelocity();
	}

	public void setVelocity(Vector2 velocity) {
		getBody().setLinearVelocity(velocity);
	}

	public void setVelocity(float x, float y) {
		getBody().setLinearVelocity(new Vector2(x, y));
	}
	
	public Vector2 getPosition() {
		return body.getPosition();
	}

	public Vector2 getWorldPosition() {
		return body.getPosition().scl(B2DVars.PPM);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public Body getBody() {
		return body;
	}

	public Animation getAnimation() {
		return animation;
	}

	public Boundary getBound() {
		return bound;
	}

	public void setBound(Boundary bound) {
		this.bound = bound;
	}

	
	public void dispose() {
		this.body.getFixtureList().clear();
		this.animation.dispose();
	}

}
