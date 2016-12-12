package com.picklegames.entities;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
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

	public int fishState;

	public static final float DELAY_STOP = 1 / 3f;
	public static final float DELAY_MOVE = 1 / 12f;
	private int facing;
	private Texture tex;
	private TextureRegion[] texR;
	private float rotation = 0;
	private float speed = 1.5f;
	private Stack<Vector2> targets;

	private float MAX_SPEED;
	private float MAX_HP;
	private float MAX_ENERGY;

	private float health = 0;
	private float energy = 100;

	public Fish(int state) {

		super();
		this.fishState = state;
		init();

	}

	public Fish(Body body, Boundary bound) {
		super(body, bound);
		init();
	}

	public void init() {

		FishGame.res.loadTexture("images/fish1.png", "fish");
		FishGame.res.loadTexture("images/fish1_dead.png", "fish_dead");

		System.out.println(fishState);
		if (fishState == FishState.ALIVE) {
			setTex(FishGame.res.getTexture("fish"));
		} else {
			setTex(FishGame.res.getTexture("fish_dead"));
		}

		setTexR(TextureRegion.split(getTex(), 250, 250)[0]);
		setAnimation(getTexR(), DELAY_STOP);

		setWidth(getWidth() * .45f);
		setHeight(getHeight() * .45f);

		setFacing(1);
		this.targets = new Stack<Vector2>();
		
		setMAX_HP(30); 
		setMAX_SPEED(1.5f);
		setMAX_ENERGY(100);
		health = MAX_HP;
		energy = MAX_ENERGY;
		speed = MAX_SPEED;

	}
	public void setMAX_HP(float mAX_HP) {
		MAX_HP = mAX_HP;
	}
	public float getMAX_HP() {
		return MAX_HP;
	}
	
	public float getMAX_ENERGY() {
		return MAX_ENERGY;
	}

	public void setMAX_ENERGY(float mAX_ENERGY) {
		MAX_ENERGY = mAX_ENERGY;
	}
	
	public float getMAX_SPEED() {
		return MAX_SPEED;
	}

	public void setMAX_SPEED(float mAX_SPEED) {
		MAX_SPEED = mAX_SPEED;
	}

	float timeElap = 0;



	@Override
	public void update(float dt) {
		super.update(dt);
		timeElap += dt;
		
		if (Gdx.input.isKeyPressed(Keys.SPACE)) {
			rotation += 10;
		}
				
		if(getVelocity().x != 0 || getVelocity().y != 0){
			getAnimation().setDelay(DELAY_MOVE);
		}else{
			getAnimation().setDelay(DELAY_STOP);
		}
		
		//System.out.println(target);
		//swimTo(getTarget().x, getTarget().y);
		if(fishState == FishState.ALIVE){
			//System.out.println("im alive");
			swimTo(targets);
		}
		//swim(); 
		
		// lower energy
		if(timeElap > .75f){
			if(energy > 2){
				energy-=2;
			}
			timeElap = 0;
		}
//		System.out.println("health " + health);
//		System.out.println("energy " + energy);
//		System.out.println("max energy " + MAX_ENERGY);
//		System.out.println("max energy % " + (MAX_ENERGY * .2f));
		if(energy < MAX_ENERGY * .20f){
			health -= .05f;
		}
		
		setSpeed((energy/MAX_ENERGY) * MAX_SPEED + .5f);
	}
 
	@Override
	public void render(SpriteBatch batch) {

		if (getBody().getLinearVelocity().x > 0) {
			setFacing(1);
		} else if (getBody().getLinearVelocity().x < 0) {
			setFacing(-1);
		}
		batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
				getWorldPosition().y - getHeight() / 2, getWidth() / 2, getHeight() / 2, getWidth(), getHeight(),
				getFacing(), 1, rotation);

	}
	private boolean isTargetReach;;
	
	public boolean isTargetReach() {
		return isTargetReach;
	}

	public void setTargetReach(boolean isTargetReach) {
		this.isTargetReach = isTargetReach;
	}

	private boolean isReach(Vector2 tar){
		return (tar.x - 5 < getWorldPosition().x && tar.x + 5 > getWorldPosition().x
				  && tar.y - 5 < getWorldPosition().y && tar.y + 5 > getWorldPosition().y);
	}
	private void swimTo(Stack<Vector2> target) {
		if (!target.isEmpty() && fishState == FishState.ALIVE) {
			Vector2 tar = target.peek();
			if (!isReach(tar)) {
				float X = (tar.x - getWorldPosition().x);
				float Y = (tar.y - getWorldPosition().y);
				float D = (float) Math.sqrt(X * X + Y * Y);
				if (getBound().isInBoundary((int) getWorldPosition().x, (int) getWorldPosition().y)) {
					// System.out.println("swim");
					setVelocity(getSpeed() * (X / D), getSpeed() * (Y / D));
				}
				if (target.size() >= 50) {
					while (!target.isEmpty()) {
						target.pop();
					}
					target.push(tar);
				}
				isTargetReach = false;
			} else {
				while (!target.isEmpty()) {
					target.pop();
				}
				isTargetReach = true;
			}
		} else {
			chill();
		}
	}

	// private void swimTo(Stack<Entity> target, boolean lol){
	// if(!target.isEmpty()){
	// Vector2 tar = target.peek();
	// if(!(tar.x - 5 < getWorldPosition().x && tar.x + 5 > getWorldPosition().x
	// &&
	// tar.y - 5 < getWorldPosition().y && tar.y + 5 > getWorldPosition().y)){
	// float X = (tar.x - getWorldPosition().x);
	// float Y = (tar.y - getWorldPosition().y);
	// float D = (float) Math.sqrt(X * X + Y * Y);
	// if(getBound().isInBoundary((int)getWorldPosition().x,
	// (int)getWorldPosition().y)){
	// //System.out.println("swim");
	// setVelocity(speed * (X / D), speed * (Y / D));
	// }
	// if(target.size() >= 50){
	// while(!target.isEmpty()){
	// target.pop();
	// }
	// target.push(tar);
	// }
	// }else{
	// while(!target.isEmpty()){
	// target.pop();
	// }
	// }
	//
	// }else{
	// chill();
	// }
	// }
	public void addTarget(float x, float y) {
		// Vector2 newT = new Vector2(x,y);
		// if(!targets.isEmpty() && !targets.peek().equals(newT)){
		targets.add(new Vector2(x, y));
		// }
	}

	public void chill() {
		setVelocity(new Vector2(0, 0));
		// getBody().setLinearVelocity((float) 0, (float) 0);
	}

	public int getFishState() {
		return fishState;
	}

	public void setFishState(int fishState) {
		this.fishState = fishState;
	}

	public Stack<Vector2> getTargets() {
		return targets;
	}

	public void setTargets(Stack<Vector2> targets) {
		this.targets = targets;
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

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getEnergy() {
		return energy;
	}

	public void setEnergy(float f) {
		energy = f;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

}
