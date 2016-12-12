package com.picklegames.handlers;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

// Miguel Garnica
// Dec 10, 2016
public class Background {

	private List<Animation> images;
	private OrthographicCamera gameCam;

	private List<Vector2> positions;
	private List<Vector2> widthHeights;

	public Background(OrthographicCamera gameCam) {
		this.gameCam = gameCam;
		images = new ArrayList<Animation>();
		positions = new ArrayList<Vector2>();
		widthHeights = new ArrayList<Vector2>();

	}

	public void addImage(Animation region, float x, float y, float width, float height) {
		images.add(region);
		positions.add(new Vector2(x, y));
		widthHeights.add(new Vector2(width, height));
	}

	public void update(float dt) {
		for (int i = 0; i < images.size(); i++) {
			images.get(i).update(dt);
		}
	}

	public void replaceLayer(int layer, Animation animate){
		this.images.set(layer - 1, images.get(layer - 1));
	}
	
	public void setAlphaTexture(int layer, int a){
		Sprite s = new Sprite(this.images.get(layer - 1).getFrame());
		s.setAlpha(a);
		this.images.get(layer - 1).getFrame().setTexture(s.getTexture());
	}
	
	public void render(SpriteBatch batch) {
		
		batch.setProjectionMatrix(gameCam.combined);
		for (int i = 0; i < images.size(); i++) {
			batch.draw(images.get(i).getFrame(), positions.get(i).x, positions.get(i).y, widthHeights.get(i).x,
					widthHeights.get(i).y);
		}

	}

	public void dispose() {
		for (int i = 0; i < images.size(); i++) {
			images.get(i).dispose();;
		}
	}
}
