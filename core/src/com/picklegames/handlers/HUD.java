package com.picklegames.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.picklegames.game.FishGame;
// Harris Yun, Miguel Garnica
// Dec 10, 2016
public class HUD {
	public Stage stage;
	private Viewport viewport;
	
	public int energy = 20;
	public int hunger = 20;
	
	private Label energyLabel, energyTitleLabel, hungerLabel, hungerTitleLabel;
	
	public HUD(SpriteBatch batch) {
		
		//create a viewport the size of the screen
		viewport = new FitViewport(FishGame.V_WIDTH, FishGame.V_HEIGHT, FishGame.hudCam);
		stage = new Stage(viewport, batch);
		
		Table table = new Table();
		table.top();
		table.setFillParent(true);
		
		//set labels and formatting
		energyLabel = new Label(String.format("%02d", energy), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		energyTitleLabel = new Label("ENERGY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		hungerLabel = new Label(String.format("%02d", hunger), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		hungerTitleLabel = new Label("HUNGER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		
		//add elements to table
		table.add(energyLabel).expandX().padTop(10);
		table.add(hungerLabel).expandX().padTop(10);
		table.row();
		table.add(energyTitleLabel).expandX();
		table.add(hungerTitleLabel).expandX();
		
		//add table to stage
		stage.addActor(table);
	}
	
	public void addImage(TextureRegion region, int x, int y) { //add image to hud using a textureregion and x,y pos
		Image actor = new Image(region);
		stage.addActor(actor);
		actor.moveBy(x, y);
	}
}
