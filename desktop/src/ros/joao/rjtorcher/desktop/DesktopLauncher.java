package ros.joao.rjtorcher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ros.joao.rjtorcher.LIBGDXwrapper.MyGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		System.setProperty("user.name","username");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 405;
		config.vSyncEnabled = true;
		config.useHDPI = true;
		config.samples = 32;
		new LwjglApplication(new MyGame(), config);
	}
}
