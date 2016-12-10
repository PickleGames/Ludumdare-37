package com.picklegames.gameStates;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.picklegames.entities.Fish;
import com.picklegames.handlers.B2DVars;
import com.picklegames.handlers.CreateBox2D;
import com.picklegames.managers.GameStateManager;

// Miguel Garnica
// Dec 9, 2016
public class Play extends GameState{
	
	private Fish fisho;
	
	public Play(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		fisho = new Fish();
		createFishBody();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		fisho.update(dt);
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		batch.setProjectionMatrix(cam.combined);
		fisho.render(batch);
	}
	
	public void createFishBody(){
		
		BodyDef bdef = CreateBox2D.createBodyDef(300, 300, BodyType.DynamicBody);
		Shape shape = CreateBox2D.createCircleShape(fisho.getWidth() / 2);
		FixtureDef fdef = CreateBox2D.createFixtureDef(shape, B2DVars.BIT_PLAYER, B2DVars.BIT_WALL);
		
		// set body to be fish body
		fisho.setBody(CreateBox2D.createBody(game.getWorld(), bdef, fdef, "fish"));
	}


	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
