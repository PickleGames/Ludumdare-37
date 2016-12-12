package com.picklegames.entities;

import java.util.Stack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.entities.FishAI.FishState;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Boundary;

// Miguel Garnica
// Dec 10, 2016
public class Fish extends Entity {
	public enum FishState{
		ALIVE, DEAD
	}
	public FishState fishState;


	public static final float DELAY_STOP = 1/3f;
	public static final float DELAY_MOVE = 1/12f;
	private int facing;
	private Texture tex;
	private TextureRegion[] texR;
	private float rotation = 0;
	private final float MAX_SPEED = 4f;
	private float speed = 4;
	private Stack<Vector2> targets;
	
	private final float MAX_HP = 200;
	private float health = 0;
	private float energy = 100;
	
	private BitmapFont font = new BitmapFont();
	
	public Fish(FishState state) {

		super();
		this.fishState = FishState.ALIVE;
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
		if(fishState == FishState.ALIVE){
			setTex(FishGame.res.getTexture("fish"));
		}else{
			setTex(FishGame.res.getTexture("fish_dead"));
		}


		setTexR(TextureRegion.split(getTex(), 250, 250)[0]);
		setAnimation(getTexR(), DELAY_STOP);
		
		setWidth(getWidth() * .45f);
		setHeight(getHeight() * .45f);
		
		
		setFacing(1);
		this.targets = new Stack<Vector2>();
		
		health = MAX_HP;
		speed = MAX_SPEED;
		
	}

	float timeElap = 0;;
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
			System.out.println("im alive");
			swimTo(targets);
		}
		//swim(); 
		
		// lower energy
		if(timeElap > .75f){
			energy -=5;
			timeElap = 0;
		}
		
		if(energy < 50){
			health -= .05f;
		}
		
		speed = (energy/100) * MAX_SPEED;
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
		
		font.draw(batch, "HP: " + health + "S: " + speed + "E: " + energy, getWorldPosition().x, getWorldPosition().y + getHeight()/2);
	
	}
		
	private void swimTo(Stack<Vector2> target){
		if(!target.isEmpty() && fishState == FishState.ALIVE){
			Vector2 tar = target.peek();
			if(!(tar.x - 5 < getWorldPosition().x && tar.x + 5 > getWorldPosition().x &&
			   tar.y - 5 < getWorldPosition().y && tar.y + 5 > getWorldPosition().y)){
				float X = (tar.x - getWorldPosition().x);
				float Y = (tar.y - getWorldPosition().y);
				float D = (float) Math.sqrt(X * X + Y * Y);
				if(getBound().isInBoundary((int)getWorldPosition().x, (int)getWorldPosition().y)){
					//System.out.println("swim");
					setVelocity(speed * (X / D), speed * (Y / D));			
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
	
//	private void swimTo(Stack<Entity> target, boolean lol){
//		if(!target.isEmpty()){
//			Vector2 tar = target.peek();
//			if(!(tar.x - 5 < getWorldPosition().x && tar.x + 5 > getWorldPosition().x &&
//			   tar.y - 5 < getWorldPosition().y && tar.y + 5 > getWorldPosition().y)){
//				float X = (tar.x - getWorldPosition().x);
//				float Y = (tar.y - getWorldPosition().y);
//				float D = (float) Math.sqrt(X * X + Y * Y);
//				if(getBound().isInBoundary((int)getWorldPosition().x, (int)getWorldPosition().y)){
//					//System.out.println("swim");
//					setVelocity(speed * (X / D), speed * (Y / D));			
//				}	
//				if(target.size() >= 50){
//					while(!target.isEmpty()){
//						target.pop();					
//					}
//					target.push(tar);
//				}
//			}else{
//				while(!target.isEmpty()){
//					target.pop();					
//				}
//			}
//				
//		}else{
//			chill();
//		}
//	}
	public void addTarget(float x, float y){
		//Vector2 newT = new Vector2(x,y);
		//if(!targets.isEmpty() && !targets.peek().equals(newT)){
			targets.add(new Vector2(x, y));
		//}
	}
	
	public void chill() {
		setVelocity(new Vector2(0, 0));
		//getBody().setLinearVelocity((float) 0, (float) 0);
	}

	public FishState getFishState() {
		return fishState;
	}

	public void setFishState(FishState fishState) {
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

	public float getMAX_HP() {
		return MAX_HP;
	}

	public float getEnergy() {
		return energy;
	}

}
