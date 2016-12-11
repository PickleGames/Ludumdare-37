package com.picklegames.handlers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;

// Miguel Garnica
// Dec 10, 2016
public class MyContactListener implements ContactListener {

	private Array<Body> bodiesToRemove;

	public MyContactListener() {
		bodiesToRemove = new Array<Body>();
	}

	@Override
	public void beginContact(Contact c) {
		Fixture fa = c.getFixtureA();
		Fixture fb = c.getFixtureB();

		if (fa.getUserData() != null && fb.getUserData() != null) {
			if (fa.getUserData().equals("food") && fb.getUserData().equals("fish")) {
				bodiesToRemove.add(fa.getBody());
			}
			if (fb.getUserData().equals("food") && fa.getUserData().equals("fish")) {
				bodiesToRemove.add(fb.getBody());
			}
		}
		// if(fb.getUserData() != null && fb.getUserData().equals("food") &&
		// fa.getUserData().equals("fish")){
		// bodiesToRemove.add(fb.getBody());
		// }
	}

	@Override
	public void endContact(Contact c) {
		// TODO Auto-generated method stub

	}

	public Array<Body> getBodiesToRemove() {
		return bodiesToRemove;
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
