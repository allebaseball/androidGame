package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MainMenu;

public class androidGame extends Game {
	// batches
	public static SpriteBatch batch;
	public SpriteBatch FPSbatch;


	@Override
	public void create () {
		batch = new SpriteBatch();
		FPSbatch = new SpriteBatch();

		// screens creation

		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		FPSbatch.dispose();
	}
}
