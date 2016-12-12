package com.picklegames.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.picklegames.entities.Fish;
import com.picklegames.game.FishGame;
// Harris Yun, Miguel Garnica
// Dec 10, 2016
public class HUD {
	public Stage stage;
	private Viewport viewport;
	private SpriteBatch batch;
	public int energy = 20;
	public int hunger = 20;
	private Fish fish;
	private Table table = new Table();
	private Label.LabelStyle style1 = new Label.LabelStyle(new BitmapFont(), Color.BLACK);
	
	private Label energyLabel, energyTitleLabel, hungerLabel, hungerTitleLabel;
	
	public HUD(SpriteBatch batch, Fish fish) {
		this.fish = fish;
		this.batch = batch;
		//create a viewport the size of the screen
		viewport = new FitViewport(FishGame.V_WIDTH, FishGame.V_HEIGHT, FishGame.hudCam);
		stage = new Stage(viewport, batch);
		
		table.top();
		table.setFillParent(true);
		
		//set labels and formatting
		energyLabel = new Label(String.format("%02d", energy), style1);
		energyTitleLabel = new Label("ENERGY", style1);
		hungerLabel = new Label(String.format("%02d", hunger), style1);
		hungerTitleLabel = new Label("HUNGER", style1);
		
		//add elements to table
		table.setWidth(200);
		table.left();
		table.top();
		table.add(energyTitleLabel).center().width(100).pad(10);
		table.add(energyLabel).center().width(100).pad(10);
		table.row();
		table.add(hungerTitleLabel).center().width(100);
		table.add(hungerLabel).center().width(100);
		

		//add table to stage
		stage.addActor(table);
	}
	
	public void addImage(TextureRegion region, int x, int y) { //add image to hud using a textureregion and x,y pos
		Image actor = new Image(region);
		stage.addActor(actor);
		actor.moveBy(x, y);
	}
	
	public void renderHUD() {
		Label tempLabel = table.getCell(energyLabel).getActor();
		tempLabel.setText(String.format("%02d", energy));
		tempLabel = table.getCell(hungerLabel).getActor();
		tempLabel.setText(String.format("%02d", hunger));
		stage.draw();
	}
}
