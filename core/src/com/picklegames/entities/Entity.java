package com.picklegames.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.B2DVars;

public class Entity {
	private Body body;
	private Animation animation;
	private Vector2 velocity;
	private float width;
	private float height;
	
	public Entity(){
		this(null);
	}
	
	public Entity(Body body){
		this.body = body;
		this.animation = new Animation();
		this.velocity = new Vector2();
	}
	
	public void setBody(Body body){
		this.body = body;
	}
	
	public void setAnimation(TextureRegion reg, float delay) {
		setAnimation(new TextureRegion[] { reg }, delay);
	}

	public void setAnimation(TextureRegion[] reg, float delay) {
		this.animation.setFrames(reg, delay);
		this.width = reg[0].getRegionWidth();
		this.height = reg[0].getRegionHeight();
	}

	public void update(float dt) {
		this.animation.update(dt);
		this.body.setLinearVelocity(this.velocity);
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
			//System.out.println(e.getMessage());
		}
	}
	
	
	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}
	
	public Vector2 getPosition(){
		return body.getPosition();
	}
	
	public Vector2 getWorldPosition(){
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

	public void dispose(){
		this.body.getFixtureList().clear();
		this.animation.dispose();
	}
	
	
}
