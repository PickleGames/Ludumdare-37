package com.picklegames.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Boundary;

// Miguel Garnica
// Dec 10, 2016
public class Fish extends Entity {

	public static final float DELAY_STOP = 1/3f;
	public static final float DELAY_MOVE = 1/12f;
	private int facing;
	private Texture tex;
	private TextureRegion[] texR;
	private float rotation = 0;
	private float speed = 2;
	private Vector2 target;
	
	public Fish() {
		super();
	}

	public Fish(Body body, Boundary bound) {
		super(body, bound);
	}

	public void init() {

		FishGame.res.loadTexture("images/fish1.png", "fish");
		tex = FishGame.res.getTexture("fish");

		texR = TextureRegion.split(tex, 250, 250)[0];
		setAnimation(texR, DELAY_STOP);
		setWidth(getWidth() * .45f);
		setHeight(getHeight() * .45f);
		

		facing = 1;
		target = new Vector2(300,200);

	}


	@Override
	public void update(float dt) {
		super.update(dt);

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			rotation += 10;
		}
		
		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
			target.x = Gdx.input.getX();
			target.y = Gdx.graphics.getHeight() - Gdx.input.getY();
		}
		
		if(getVelocity().x > 0 || getVelocity().y > 0){
			getAnimation().setDelay(DELAY_MOVE);
		}else{
			getAnimation().setDelay(DELAY_STOP);
		}
		
		System.out.println(target);
		swimTo(target.x, target.y);
		
		//swim(); 
	}

	@Override
	public void render(SpriteBatch batch) {

		if (getBody().getLinearVelocity().x > 0) {
			facing = 1;
		} else if (getBody().getLinearVelocity().x < 0 ){
			facing = -1;
		}
		batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
				getWorldPosition().y - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
				facing, 1, rotation);
	}
	
	
	public void swimTo(float x2, float y2){
		if(!(x2 - 5 < getWorldPosition().x && x2 + 5 > getWorldPosition().x &&
			 y2 - 5 < getWorldPosition().y && y2 + 5 > getWorldPosition().y )){
			
			float X = (x2 - getWorldPosition().x);
			float Y = (y2 - getWorldPosition().y);
			float D = (float) Math.sqrt(X * X + Y * Y);
			if(getBound().isInBoundary((int)getWorldPosition().x, (int)getWorldPosition().y)){
				//System.out.println("swim");
				getBody().setLinearVelocity(speed * (X / D), speed * (Y / D));			
			}			
		}else{
			getBody().setLinearVelocity(0 , 0);	
		}
		
	}
	
	
	public void chill() {
		getBody().setLinearVelocity((float) Math.random() - 0.5f, (float) Math.random() - 0.5f);
	}

}
