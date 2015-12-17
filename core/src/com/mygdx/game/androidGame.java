package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;

public class androidGame extends Game {
	public SpriteBatch batch;
	public SpriteBatch FPSbatch;

	// screens
	public PlayScreen playS;
	public MenuScreen menuS;


	@Override
	public void create () {
		batch = new SpriteBatch();
		FPSbatch = new SpriteBatch();

		// screens creation
		menuS = new MenuScreen(this);
		playS = new PlayScreen(this);
		setScreen(menuS);
	}

	@Override
	public void render () {
		super.render();
	}
}
