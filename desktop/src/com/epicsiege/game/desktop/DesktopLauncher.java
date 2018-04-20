package com.epicsiege.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.epicsiege.game.MyGdxGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "EPIC SIEGE";
        config.width = 960;
        config.height = 540;
		new LwjglApplication(new MyGdxGame(), config);
	}
}
