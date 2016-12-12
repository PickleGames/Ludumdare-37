package com.picklegames.gameStates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.picklegames.entities.Fish;
import com.picklegames.entities.FishAI;
import com.picklegames.entities.FishState;
import com.picklegames.game.FishGame;
import com.picklegames.handlers.Animation;
import com.picklegames.handlers.B2DVars;
import com.picklegames.handlers.Background;
import com.picklegames.handlers.Boundary;
import com.picklegames.handlers.CreateBox2D;
import com.picklegames.handlers.DayNightCycle;
import com.picklegames.handlers.HUD;
import com.picklegames.handlers.MyContactListener;
import com.picklegames.managers.GameStateManager;

// Miguel Garnica
// Dec 10, 2016
public class Level3 extends GameState {
	private Fish fisho;

	private BitmapFont font;
	private Vector3 mousePos;
	private MyContactListener cl;

	private Background bg;
	private int fishtankID;

	private DayNightCycle dayNight;
	private float dayNightRotation = 0;
	private Boundary bound;
	private Array<FishAI> fishAIs;
	private boolean isLevelFinish = false;

	private Sprite trans;

	private HUD hud;

	public Level3(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {

		bound = new Boundary(50, 50, (int) (Gdx.graphics.getWidth() * .90f), (int) (Gdx.graphics.getHeight() * .70f));
		fishAIs = new Array<FishAI>();

		fishtankID = 3;

		// load fish
		fisho = new Fish(FishState.ALIVE);
		createFishBody();
		fisho.setBound(bound);
		fisho.addTarget(300, 200);
		// fisho.target = fisho.getWorldPosition();

		for (int i = 0; i < 10; i++) {
			fishAIs.add(
					createFishAI((int) (Math.random() * 500) + 100, (int) (Math.random() * 400) + 100, FishState.DEAD));
		}

		// load font
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(3);

		// load background
		bg = new Background(game.getHudCam());
		Texture tex;

		// load layer 1
		Animation anime1 = new Animation();
		FishGame.res.loadTexture("images/background.png", "bg");
		tex = FishGame.res.getTexture("bg");
		anime1.setFrames(TextureRegion.split(tex, tex.getWidth(), tex.getHeight())[0], 0);
		bg.addImage(anime1, 0, 0, hudCam.viewportWidth, hudCam.viewportHeight);

		// load layer 2
		Animation anime2 = new Animation();
		FishGame.res.loadTexture("images/Desk.png", "desk");
		tex = FishGame.res.getTexture("desk");
		// texR = new TextureRegion(tex);
		anime2.setFrames(TextureRegion.split(tex, tex.getWidth(), tex.getHeight())[0], 0);
		bg.addImage(anime2, 650, 330, anime2.getFrame().getRegionWidth(), anime2.getFrame().getRegionHeight());

		// FishGame.res.loadTexture("images/Computer.png", "comp");
		Animation anime3 = new Animation();
		FishGame.res.loadTexture("images/desktop.png", "comp");
		tex = FishGame.res.getTexture("comp");
		TextureRegion[] regs = TextureRegion.split(tex, tex.getWidth() / 4, tex.getHeight())[0];
		anime3.setFrames(regs, 5);
		bg.addImage(anime3, 680, 435, anime3.getFrame().getRegionWidth(), anime3.getFrame().getRegionHeight());

		Animation anime4 = new Animation();
		FishGame.res.loadTexture("images/owner.png", "owner");
		tex = FishGame.res.getTexture("owner");
		// texR = new TextureRegion(tex);
		anime4.setFrames(TextureRegion.split(tex, tex.getWidth(), tex.getHeight())[0], 0);
		bg.addImage(anime4, 670, 350, anime4.getFrame().getRegionWidth() * 1.5f,
				anime4.getFrame().getRegionHeight() * 1.5f);

		Animation anime5 = new Animation();
		FishGame.res.loadTexture("images/Chair.png", "chair");
		tex = FishGame.res.getTexture("chair");
		anime5.setFrames(TextureRegion.split(tex, tex.getWidth(), tex.getHeight())[0], 0);
		bg.addImage(anime5, 720, 300, anime5.getFrame().getRegionWidth(), anime5.getFrame().getRegionHeight());

		// Animation anime7 = new Animation();
		// FishGame.res.loadTexture("images/daynight.png", "daynight");
		// tex = FishGame.res.getTexture("daynight");
		// anime5.setFrames(TextureRegion.split(tex, tex.getWidth(),
		// tex.getHeight())[0], 0);
		// bg.addImage(anime5, 0, 0, hudCam.viewportWidth,
		// hudCam.viewportHeight);

		// load layer 2
		Animation anime6 = new Animation();
		FishGame.res.loadTexture("images/Fishtank" + fishtankID + ".png", "fishtank1");
		tex = FishGame.res.getTexture("fishtank1");
		anime6.setFrames(TextureRegion.split(tex, tex.getWidth(), tex.getHeight())[0], 0);
		bg.addImage(anime6, 0, 0, hudCam.viewportWidth * 1.1f, hudCam.viewportHeight * 1.3f);

		FishGame.res.loadTexture("images/daynight.png", "daynight");
		tex = FishGame.res.getTexture("daynight");
		trans = new Sprite(tex);
		trans.setSize(hudCam.viewportWidth, hudCam.viewportHeight);

		// load and set world contact listener
		cl = new MyContactListener();
		game.getWorld().setContactListener(cl);

		batch.begin();
		dayNight = new DayNightCycle(20, 180, batch);
		batch.end();

		mousePos = new Vector3();

		CreateBox2D.createBoxBoundary(game.getWorld(), new Vector2(10, 20), 1000, 520, B2DVars.BIT_WALL,
				B2DVars.BIT_PLAYER);
		hud = new HUD(fisho);
		FishGame.res.loadMusic("musics/level3music.mp3", "level3");
		FishGame.res.getMusic("level3").setLooping(true);
		FishGame.res.getMusic("level3").play();
	}

	@Override
	public void handleInput() {
		System.out.println(bound);
		if (Gdx.input.isButtonPressed(Buttons.LEFT)) {
			fisho.addTarget(mousePos.x, mousePos.y);
		}

	}

	float timeElapsed = 0;
	float alpha;

	@Override
	public void update(float dt) {
		timeElapsed += dt;
		bg.update(dt);

		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		cam.unproject(mousePos);
		// System.out.println(mousePos);
		this.handleInput();
		// update mouse position

		// update fish
		fisho.update(dt);
		// System.out.println("targets : " + fisho.getTargets().size());

		for (FishAI f : fishAIs) {
			f.update(dt);
			if (f.getHealth() <= 0) {
				f.setFishState(FishState.DEAD);
			}
		}

		Array<Body> bodies = cl.getBodiesToRemove();
		System.out.println(bodies.size);
		for (int i = 0; i < bodies.size; i++) {
			// System.out.println("before remove food size : " + foods.size);
			Body b = bodies.get(i);
			if (b.getUserData() instanceof FishAI) {
				FishAI fish = (FishAI) b.getUserData();
				if (fish.isClicked()) {
					// fish.dispose();
					if (fisho.getEnergy() + 5 < 100) {
						fisho.setEnergy(fisho.getEnergy() + 5);
					}
					fishAIs.removeValue(fish, false);
					game.getWorld().destroyBody(fish.getBody());
					bodies.clear();
					i--;
				}
			}

		}

		// for (int i = 0; i < fishAIs.size(); i++) {
		// FishAI f;
		// f = fishAIs.get(i);
		//
		// if(f.isClicked()){
		// //f.dispose();
		// fishAIs.remove(f);
		// game.getWorld().destroyBody(f.getBody());
		// }
		// }

		// update cycle rotation
		dayNightRotation += 0.08f;

		// GOOD ENOUGH
		if (dayNightRotation < 180) {
			alpha += 0.0003f;
		} else {
			alpha -= 0.0003f;
		}
		///////////

		System.out.println("alpha : " + alpha);
		trans.setAlpha(alpha);

		// update cycle rotation
		dayNightRotation += 0.04f;

		// GOOD ENOUGH
		if (dayNightRotation < 180) {
			if (alpha + 0.00016 < 1) {
				alpha += 0.00016f;
			}
		} else {
			alpha -= 0.00016f;
		}
		///////////

		System.out.println("alpha : " + alpha);
		trans.setAlpha(alpha);

		System.out.println(dayNightRotation);
		if (dayNightRotation >= 358) {
			isLevelFinish = true;
		}

		if (isLevelFinish) {
			gsm.setState(GameStateManager.END);
		}

		if (fisho.getHealth() <= 0) {
			GameStateManager.level = GameStateManager.LEVEL3;
			gsm.setState(GameStateManager.DEAD);
		}

		hud.update(dt);

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(hudCam.combined);
		dayNight.renderCycle(dayNightRotation);
		bg.render(batch);

		batch.setProjectionMatrix(cam.combined);
		// render fish
		fisho.render(batch);

		for (FishAI f : fishAIs) {
			f.render(batch);
		}

		trans.draw(batch);

		if (fisho.isClicked()) {
			font.draw(batch, "STOP!!", fisho.getWorldPosition().x - fisho.getWidth() / 2,
					fisho.getWorldPosition().y + fisho.getHeight());
		}
		hud.renderHUD(batch);
	}

	public FishAI createFishAI(int x, int y, int state) {
		FishAI fish = new FishAI(state);
		BodyDef bdef = CreateBox2D.createBodyDef(x, y, BodyType.DynamicBody);
		Shape shape = CreateBox2D.createCircleShape(fish.getWidth() / 4);
		FixtureDef fdef = CreateBox2D.createFixtureDef(shape, B2DVars.BIT_PLAYER, B2DVars.BIT_WALL);
		fdef.filter.maskBits = B2DVars.BIT_WALL | B2DVars.BIT_FOOD | B2DVars.BIT_PLAYER;
		// set body to be fish body
		fish.setBody(CreateBox2D.createBody(game.getWorld(), bdef, fdef, "fish"));
		fish.setBound(bound);
		fish.getBody().setUserData(fish);
		return fish;
	}

	public void createFishBody() {
		BodyDef bdef = CreateBox2D.createBodyDef(300, 200, BodyType.DynamicBody);
		Shape shape = CreateBox2D.createCircleShape(fisho.getWidth() / 2);
		FixtureDef fdef = CreateBox2D.createFixtureDef(shape, B2DVars.BIT_PLAYER, B2DVars.BIT_WALL);
		fdef.filter.maskBits = B2DVars.BIT_WALL | B2DVars.BIT_FOOD | B2DVars.BIT_PLAYER;
		// set body to be fish body
		fisho.setBody(CreateBox2D.createBody(game.getWorld(), bdef, fdef, "player"));
		fisho.getBody().setUserData(fisho);
	}

	@Override
	public void dispose() {
		bg.dispose();

		for (int i = 0; i < fishAIs.size; i++) {
			fishAIs.get(i).dispose();
			fishAIs.removeIndex(i);
			i--;
		}
		fisho.dispose();
		FishGame.res.getMusic("level3").stop();
	}
	
}
