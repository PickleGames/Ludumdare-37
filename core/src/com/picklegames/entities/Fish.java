package com.picklegames.entities;

import java.util.Stack;

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

	public Stack<Vector2> getTargets() {
		return targets;
	}

	public void setTargets(Stack<Vector2> targets) {
		this.targets = targets;
	}

	public static final float DELAY_STOP = 1/3f;
	public static final float DELAY_MOVE = 1/12f;
	private int facing;
	private Texture tex;
	private TextureRegion[] texR;
	private float rotation = 0;
	private float speed = 2;
	private Stack<Vector2> targets;
	
	private Vector2 target;
	
	public Fish() {
		super();
	}

	public Fish(Body body, Boundary bound) {
		super(body, bound);
	}

	public void init() {

		FishGame.res.loadTexture("images/fish1.png", "fish");
		setTex(FishGame.res.getTexture("fish"));

		setTexR(TextureRegion.split(getTex(), 250, 250)[0]);
		setAnimation(getTexR(), DELAY_STOP);
		
		setWidth(getWidth() * .45f);
		setHeight(getHeight() * .45f);
		
		
		setFacing(1);
		this.targets = new Stack<Vector2>();
		
		setTarget(new Vector2(300,200));

	}


	@Override
	public void update(float dt) {
		super.update(dt);

		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			rotation += 10;
		}
		
//		if(Gdx.input.isButtonPressed(Buttons.LEFT)){
//			getTarget().x = Gdx.input.getX();
//			getTarget().y = Gdx.graphics.getHeight() - Gdx.input.getY();
//		}
		
		if(getVelocity().x != 0 || getVelocity().y != 0){
			getAnimation().setDelay(DELAY_MOVE);
		}else{
			getAnimation().setDelay(DELAY_STOP);
		}
		
		//System.out.println(target);
		//swimTo(getTarget().x, getTarget().y);
		swimTo(targets);
		//swim(); 
	}

	@Override
	public void render(SpriteBatch batch) {

		if (getBody().getLinearVelocity().x > 0) {
			setFacing(1);
		} else if (getBody().getLinearVelocity().x < 0 ){
			setFacing(-1);
		}
		batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
				getWorldPosition().y - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
				getFacing(), 1, rotation);
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
			chill();
			//getBody().setLinearVelocity(0 , 0);	
		}
	}
	
	public void swimTo(Stack<Vector2> target){
		if(!target.isEmpty()){
			Vector2 tar = target.peek();
			if(!(tar.x - 5 < getWorldPosition().x && tar.x + 5 > getWorldPosition().x &&
			   tar.y - 5 < getWorldPosition().y && tar.y + 5 > getWorldPosition().y)){
				float X = (tar.x - getWorldPosition().x);
				float Y = (tar.y - getWorldPosition().y);
				float D = (float) Math.sqrt(X * X + Y * Y);
				if(getBound().isInBoundary((int)getWorldPosition().x, (int)getWorldPosition().y)){
					//System.out.println("swim");
					getBody().setLinearVelocity(speed * (X / D), speed * (Y / D));			
				}	
				if(target.size() >= 50){
					while(!target.isEmpty()){
						target.pop();					
					}
					target.push(tar);
				}
			}else{
				while(!target.isEmpty()){
					target.pop();					
				}
			}
				
		}else{
			chill();
		}
	}
	
	public void addTarget(float x, float y){
		targets.add(new Vector2(x, y));
	}
	
	public void chill() {
		getBody().setLinearVelocity((float) 0, (float) 0);
	}

	public int getFacing() {
		return facing;
	}

	public void setFacing(int facing) {
		this.facing = facing;
	}

	public Texture getTex() {
		return tex;
	}

	public void setTex(Texture tex) {
		this.tex = tex;
	}

	public TextureRegion[] getTexR() {
		return texR;
	}

	public void setTexR(TextureRegion[] texR) {
		this.texR = texR;
	}

	public Vector2 getTarget() {
		return target;
	}

	public void setTarget(Vector2 target) {
		this.target = target;
	}

}
