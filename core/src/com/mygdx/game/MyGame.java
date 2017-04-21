package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screen.GameScreen;

public class MyGame extends Game {
	private AssetManager assetManager;
	private GameScreen screen;

	private SpriteBatch batch;
	/**
	 * Creates the game. Initializes the sprite batch and asset manager.
	 * Also starts the game until we have a main menu.
	 */
	@Override
	public void create () {
		assetManager = new AssetManager(new InternalFileHandleResolver());

		startGame();
	}


	/**
	 * Starts the game.
	 */
	private void startGame() {
		GameScreen screen = new GameScreen(this);

		setScreen(screen);
	}


	@Override
	public void dispose () {
		assetManager.dispose();
	}

	/**
	 * Returns the asset manager used to load all textures and sounds.
	 *
	 * @return the asset manager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

}
