package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.LIBGDXwrapper.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","username");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MyGame(), config);
	}
}
