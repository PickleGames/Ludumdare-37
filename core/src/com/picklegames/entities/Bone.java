package com.picklegames.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Boundary;

// Miguel Garnica
// Dec 12, 2016
public class Bone extends Entity {

	private Texture tex;
	private TextureRegion texR;
	private String[] aliveT;
	private String currentDialouge;
	private BitmapFont font;
	private float timerText;
	private Random rand;

	public Bone() {
		super();
		init();
	}

	public Bone(Body body, Boundary bound) {
		super(body, bound);
		init();
	}

	public void init() {
		rand = new Random();
		aliveT = new String[2];
		aliveT[0] = "IMMA HAUNT UR LIFE";
		aliveT[1] = "Y U EAT ME BRUh";
		currentDialouge = "";
		////////////////////////////////////////////////////////
		font = new BitmapFont();

		FishGame.res.loadTexture("images/fish_bone.png", "bone");
		tex = FishGame.res.getTexture("bone");

		texR = new TextureRegion(tex);
		setAnimation(texR, 0);
	}

	boolean isSetDialouge = false;
	@Override
	public void update(float dt) {
		super.update(dt);
		drop();
		if (isClicked() && !isSetDialouge) {
			currentDialouge = getDialouge();
			isSetDialouge = true;
		}

		if (!currentDialouge.isEmpty()) {
			timerText += dt;
		}

		if (timerText > 2f) {
			timerText = 0;
			isSetDialouge = false;
			currentDialouge = "";
		}
	}

	private String getDialouge() {
		Color color = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(), 1);
		font.setColor(color);
		font.getData().setScale(rand.nextInt(3) + 1);
		int num = rand.nextInt(aliveT.length);
		return aliveT[num];
	}

	@Override
	public void render(SpriteBatch batch) {

		if (getBody() != null) {
			batch.draw(getAnimation().getFrame(), getWorldPosition().x - getWidth() / 2,
					getWorldPosition().y - getHeight() / 2, getWidth(), getHeight());
		}
		font.draw(batch, currentDialouge, getWorldPosition().x - 20 , getWorldPosition().y);
	}

	float veloY;
	float veloX;

	public void drop() {

		if (getWorldPosition().y < 50) {
			veloY = 0;
			veloX = 0;
		} else {
			veloX = (float) Math.random() * 2 - 1;
			veloY = -.6f;
		}
		getBody().setLinearVelocity(veloX, veloY);
	}

}
