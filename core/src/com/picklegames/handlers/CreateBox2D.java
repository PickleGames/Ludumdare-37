package com.picklegames.handlers;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

// Miguel Garnica
// Dec 10, 2016
public final class CreateBox2D {

	public static Body createBody(World world, BodyDef bdef, FixtureDef fdef, String userData) {

		// create body from body definition
		Body body = world.createBody(bdef);
		body.setUserData(userData);

		// add fixture to body from fixture definition
		body.createFixture(fdef).setUserData(userData);

		// change Mass
		MassData md = new MassData();
		md.mass = 1;
		body.setMassData(md);

		// return body
		return body;
	}

	public static BodyDef createBodyDef(float x, float y, BodyType bodyType) {
		BodyDef bdef = new BodyDef();
		bdef.position.set(x / B2DVars.PPM, y / B2DVars.PPM);
		bdef.type = bodyType;
		return bdef;
	}
	
	public static PolygonShape createBoxShape(float hx, float hy, float angle){
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(hx / B2DVars.PPM, hy / B2DVars.PPM);
		return shape;
	}
	
	public static Shape createCircleShape(float radius){
		Shape shape = new CircleShape();
		shape.setRadius(radius / B2DVars.PPM);
		return shape;
	}

	public static FixtureDef createFixtureDef(Shape shape, short categoryBits, short maskBits) {
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.filter.categoryBits = categoryBits;
		fdef.filter.maskBits = maskBits;
		return fdef;
	}

}
