package com.picklegames.gameStates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.picklegames.game.FishGame;
import com.picklegames.managers.GameStateManager;

public class FlashScreen extends GameState {

	private BitmapFont font;
	public static int day = 0;

	public GlyphLayout layout;

	public FlashScreen(GameStateManager gsm) {
		super(gsm);

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		font = new BitmapFont();
		font.setColor(Color.GOLD);
		font.getData().setScale(20);

		layout = new GlyphLayout();
		FishGame.res.loadMusic("musics/thisfitnowhere.mp3", "flash");
		FishGame.res.getMusic("flash").setVolume(0.5f);
		FishGame.res.getMusic("flash").play();
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	float timeElap = 0;

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

		timeElap += dt;
		if (timeElap > 8f) {
			if (day == 1) {
				gsm.setState(GameStateManager.LEVEL1);
			} else if(day == 2){
				gsm.setState(GameStateManager.LEVEL2);
			}else{
				gsm.setState(GameStateManager.LEVEL3);
			}
			timeElap = 0;
		}

	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		layout.setText(font, "DAY " + day);
		font.draw(batch, "DAY " + day, cam.viewportWidth / 2 - layout.width / 2,
				cam.viewportHeight / 2 + layout.height / 2);
	}

	@Override
	public void dispose() {
		font.dispose();
		FishGame.res.getMusic("flash").stop();

	}

}
