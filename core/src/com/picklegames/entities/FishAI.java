package com.picklegames.entities;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.picklegames.game.FishGame;

public class FishAI extends Fish {

	private Food foodTarget;

	public void init() {
		FishGame.res.loadTexture("images/fish2.png", "fish2");
		setTex(FishGame.res.getTexture("fish2"));

		setTexR(TextureRegion.split(getTex(), 250, 250)[0]);
		setAnimation(getTexR(), DELAY_STOP);
		setWidth(getWidth() * .25f);
		setHeight(getHeight() * .25f);

		setFacing(1);
		setTargets(new Stack<Vector2>());
	}

	public void update(float dt) {
		super.update(dt);

		System.out.println("food target " + getTargets().size());
		if(!getTargets().isEmpty()){
			System.out.println("food target " + getTargets().peek());			
		}
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
