package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.Const;
import com.mygdx.game.androidGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "AndroidGame";
		config.width = Const.V_WIDTH;
		config.height = Const.V_HEIGHT;
		config.fullscreen = false;
		config.vSyncEnabled = false;
		new LwjglApplication(new androidGame(), config);
	}
}
