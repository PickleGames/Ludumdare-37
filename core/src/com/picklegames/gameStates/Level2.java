package com.picklegames.gameStates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
import com.picklegames.entities.Food;
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
public class Level2 extends GameState {
	private Fish fisho;
	private List<FishAI> fishAIs;

	private BitmapFont font;
	private Vector3 mousePos;
	private MyContactListener cl;

	private Background bg;
	private int fishtankID;

	private DayNightCycle dayNight;
	private float dayNightRotation = 0;
	private Boundary bound;
	private Array<Food> foods;
	private boolean isLevelFinish = false;

	private Sprite trans;
	private HUD hud;

	public Level2(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		bound = new Boundary(10, 10, (int) (Gdx.graphics.getWidth() * .95f), (int) (Gdx.graphics.getHeight() * .70f));
		fishAIs = new ArrayList<FishAI>();

		fishtankID = 2;

		// load fish
		fisho = new Fish(FishState.ALIVE);
		createFishBody();
		fisho.setBound(bound);
		fisho.addTarget(300, 200);
		// fisho.target = fisho.getWorldPosition();

		for (int i = 0; i < 5; i++) {
			fishAIs.add(createFishAI((int) (Math.random() * 500) + 100, (int) (Math.random() * 400) + 100,
					FishState.ALIVE));
			
		}
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
		FishGame.res.loadTexture("images/desk.png", "desk");
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
		FishGame.res.loadTexture("images/chair.png", "chair");
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
		FishGame.res.loadTexture("images/fishtank2.png", "fishtank2");
		tex = FishGame.res.getTexture("fishtank2");
		anime6.setFrames(TextureRegion.split(tex, tex.getWidth(), tex.getHeight())[0], 0);
		bg.addImage(anime6, 0, 0, hudCam.viewportWidth * 1.1f, hudCam.viewportHeight * 1.3f);

		FishGame.res.loadTexture("images/daynight.png", "daynight");
		tex = FishGame.res.getTexture("daynight");
		trans = new Sprite(tex);
		trans.setSize(hudCam.viewportWidth, hudCam.viewportHeight);

		// load food
		foods = new Array<Food>();

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
		FishGame.res.loadMusic("musics/level2music.mp3", "level2");
		FishGame.res.getMusic("level2").setLooping(true);
		FishGame.res.getMusic("level2").play();
		
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

			if (foods.size > 0) {
				//if (f.getTargets().isEmpty()) {
					Food foo = getRandFood();
					if (foo != null) {
						f.addFoodTarget(foo);
					}
				//}
			}
		}

		// dirty

		Array<Body> bodies = cl.getBodiesToRemove();
		Array<Body> bodiesFish = cl.getBodiesToHelp();
		for (int i = 0; i < bodies.size; i++) {
			// System.out.println("before remove food size : " + foods.size);
			Body b = bodies.get(i);
			Food f = (Food) b.getUserData();
			f.dispose();

			Body fishb = bodiesFish.get(i);
			Fish fish = (Fish) fishb.getUserData();

			if (fish.getEnergy() + 5 < fish.getMAX_ENERGY()) {
				fish.setEnergy(fish.getEnergy() + 5);
			}
			if (fish.getHealth() + 5 < fish.getMAX_HP()) {
				fish.setHealth(fish.getHealth() + 5);
			}

			bodiesFish.clear();
			foods.removeValue(f, true);
			game.getWorld().destroyBody(b);

			bodies.clear();
			i--;
		}

		// update food
		for (int i = 0; i < foods.size; i++) {
			Food f = foods.get(i);
			f.update(dt);

		}

		if (timeElapsed > .3f) {
			if (foods.size < 2) {
				createFood();
			}
			timeElapsed = 0;
		}

		// update cycle rotation
		dayNightRotation += dt * 5.35;

		// VERY GUD
		if (dayNightRotation < 180) {
			if(alpha < 1){
				alpha = (dayNightRotation / 180);
			}
		} else {
			alpha = 1 - ((dayNightRotation - 180)  / 180);
		}
		///////////

		System.out.println("alpha : " + alpha);
		trans.setAlpha(alpha);

		System.out.println(dayNightRotation);
		if (dayNightRotation >= 358) {
			isLevelFinish = true;
		}

		if (isLevelFinish) {
			FlashScreen.day = 3;
			gsm.setState(GameStateManager.FLASH);
		}

		if (fisho.getHealth() <= 0) {
			GameStateManager.level = GameStateManager.LEVEL2;
			gsm.setState(GameStateManager.DEAD);
		}

		hud.update(dt);
	}

	Random rand = new Random();

	private Food getRandFood() {
		if (foods.size > 0) {
			int num = rand.nextInt(foods.size);
			System.out.println("get food");
			return foods.get(num);
		}
		return null;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(hudCam.combined);
		dayNight.renderCycle(dayNightRotation);
		bg.render(batch);

		batch.setProjectionMatrix(cam.combined);
		// render food

		for (Food f : foods) {
			f.render(batch);
		}

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
		Shape shape = CreateBox2D.createCircleShape(fish.getWidth() / 2);
		FixtureDef fdef = CreateBox2D.createFixtureDef(shape, B2DVars.BIT_PLAYER, B2DVars.BIT_WALL);
		fdef.filter.maskBits = B2DVars.BIT_WALL | B2DVars.BIT_FOOD;
		// set body to be fish body
		fish.setBody(CreateBox2D.createBody(game.getWorld(), bdef, fdef, "fish"));
		fish.setBound(bound);
		fish.getBody().setUserData(fish);
		return fish;
	}

	public void createFishBody() {
		BodyDef bdef = CreateBox2D.createBodyDef(300, 200, BodyType.DynamicBody);
		Shape shape = CreateBox2D.createCircleShape(fisho.getWidth() / 3);
		FixtureDef fdef = CreateBox2D.createFixtureDef(shape, B2DVars.BIT_PLAYER, B2DVars.BIT_WALL);
		fdef.filter.maskBits = B2DVars.BIT_WALL | B2DVars.BIT_FOOD;
		// set body to be fish body
		fisho.setBody(CreateBox2D.createBody(game.getWorld(), bdef, fdef, "player"));
		fisho.getBody().setUserData(fisho);
	}

	public void createFood() {

		BodyDef bdef;
		Shape shape;
		FixtureDef fdef;
		Food f = new Food();
		bdef = CreateBox2D.createBodyDef((float) (Math.random() * 600) + 100, (float) (Math.random() * 100) + 400,
				BodyType.DynamicBody);
		shape = CreateBox2D.createCircleShape(f.getWidth() / 2);
		fdef = CreateBox2D.createFixtureDef(shape, B2DVars.BIT_FOOD, B2DVars.BIT_PLAYER);
		fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_WALL;
		f.setBody(CreateBox2D.createBody(game.getWorld(), bdef, fdef, "food"));
		f.getBody().setUserData(f);
		foods.add(f);

	}

	@Override
	public void dispose() {
		bg.dispose();
		for (int i = 0; i < fishAIs.size(); i++) {
			fishAIs.get(i).dispose();
			fishAIs.remove(i);
			i--;
		}
		fisho.dispose();

		for (int i = 0; i < foods.size; i++) {
			foods.get(i).dispose();
			foods.removeIndex(i);
			i--;
		}
		cl.getBodiesToHelp().clear();
		cl.getBodiesToRemove().clear();
		FishGame.res.getMusic("level2").stop();
	}
}
