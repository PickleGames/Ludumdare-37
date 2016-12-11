package com.picklegames.handlers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

// Miguel Garnica
// Dec 10, 2016
public class Background {

	private ArrayList<TextureRegion> images;
	private OrthographicCamera gameCam;

	private ArrayList<Vector2> positions;
	private ArrayList<Vector2> widthHeights;

	public Background(OrthographicCamera gameCam) {
		this.gameCam = gameCam;
		images = new ArrayList<TextureRegion>();
		positions = new ArrayList<Vector2>();
		widthHeights = new ArrayList<Vector2>();

	}

	public void addImage(TextureRegion region, float x, float y, float width, float height) {
		images.add(region);
		positions.add(new Vector2(x, y));
		widthHeights.add(new Vector2(width, height));
	}

	public void update(float dt) {

	}

	public void render(SpriteBatch batch) {
		
		batch.setProjectionMatrix(gameCam.combined);
		for (int i = 0; i < images.size(); i++) {
			batch.draw(images.get(i), positions.get(i).x, positions.get(i).y, widthHeights.get(i).x,
					widthHeights.get(i).y);
		}

	}

	public void dispose() {

	}
}
