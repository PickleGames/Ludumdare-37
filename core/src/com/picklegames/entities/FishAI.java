package com.picklegames.entities;

import java.util.Random;
import java.util.Stack;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.picklegames.game.FishGame;

public class FishAI extends Fish {
	public FishAI(int state) {
		super(state);
	}
	
	private Food foodTarget;
	private String[] aliveT;
	private String[] deadT;
	private String currentDialouge;
	
	private BitmapFont font;
	private float timerText;
	private Random rand;
	
	public void init() {
		rand = new Random();
		aliveT = new String[4];
		deadT = new String[2];
		aliveT[0] = "FIGHT FO UR LIFE";
		aliveT[1] = "WHAT DO U WANT?";
		aliveT[2] = "HEY, IM SWIMMING HERE!";
		aliveT[3] = "GO AWAY!!";
		
		deadT[0] = "IM DEAD!!";
		deadT[1] = "DONT EAT ME BRUH";
		
		currentDialouge = "";
		////////////////////////////////////////////////////////
		font = new BitmapFont();
		
		
		FishGame.res.loadTexture("images/fish2.png", "fish2");
		FishGame.res.loadTexture("images/fish2_dead.png", "fish2_dead");
		
		System.out.println(fishState);
		if(getFishState() == FishState.ALIVE){
			setTex(FishGame.res.getTexture("fish2"));
		}else{
			setTex(FishGame.res.getTexture("fish2_dead"));
		}
		
		TextureRegion texR = new TextureRegion(getTex());
		setTexR(TextureRegion.split(texR.getTexture(), 250, 250)[0]);
		setAnimation(getTexR(), DELAY_STOP);
		setWidth(getWidth() * .25f);
		setHeight(getHeight() * .25f);

		setFacing(1);
		setTargets(new Stack<Vector2>());
		
		setMAX_HP(100);
		setMAX_SPEED(1.5f);
		setHealth(getMAX_HP() / 2);
		setSpeed(getMAX_SPEED());
		
		FishGame.res.loadSound("sounds/ded.mp3", "dedFish");
	}

	boolean isDead = false;
	boolean isSetDialouge = false;
	public void update(float dt) {
		super.update(dt);
		
		System.out.println("isClciked: " + isClicked());
		
		if(isClicked() && !isSetDialouge){
			currentDialouge = getDialouge(getFishState());
			isSetDialouge = true;
		}
		
		if(!currentDialouge.isEmpty()){
			timerText += dt;
		}
		
		if(timerText > 2f){
			timerText = 0;
			isSetDialouge = false;
			currentDialouge = "";
		}
		
		System.out.println("food target " + getTargets().size());
		if(!getTargets().isEmpty()){
			System.out.println("food target " + getTargets().peek());			
		}
		
		if(getFishState() == FishState.DEAD && !isDead){
			setTex(FishGame.res.getTexture("fish2_dead"));
			setTexR(TextureRegion.split(getTex(), 250, 250)[0]);
			setAnimation(getTexR(), DELAY_STOP);
			setWidth(getWidth() * .25f);
			setHeight(getHeight() * .25f);
			isDead = true;
		}
		
		
		if(getHealth() < .10f && !isDead){
			FishGame.res.getSound("dedFish").setVolume(1, .25f);
			FishGame.res.getSound("dedFish").play();
		}
	}


	private String getDialouge(int state){
		Color color = new Color(rand.nextFloat(), rand.nextFloat(),rand.nextFloat(), 1);
		font.setColor(color);
		font.getData().setScale(rand.nextInt(2) + 1);
		if(state == FishState.ALIVE){
			int num = rand.nextInt(aliveT.length);
			return aliveT[num];
		}else{
			int num = rand.nextInt(deadT.length);
			return deadT[num];
		}
	}
	
	public void render(SpriteBatch batch){
		super.render(batch);
		font.draw(batch, currentDialouge, getWorldPosition().x - 20 , getWorldPosition().y);
	}
	
	public void addFoodTarget(Food target) {
		System.out.println("food aquired");
		if(this.foodTarget == null){
			this.foodTarget = target;
		}
		if (!this.foodTarget.equals(target)) {
			this.foodTarget = target;
			System.out.println(foodTarget);
			addTarget(foodTarget.getWorldPosition().x, foodTarget.getWorldPosition().y);
		}
	}
}
