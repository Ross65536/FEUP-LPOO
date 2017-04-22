package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;


public class MyGame extends Game {
    private GameScreen gameScreen;

    @Override
	public void create ()
    {
        startGameTest();
	}


	private void startGameTest()
	{
        gameScreen = new GameScreen(this);
        gameScreen.createTestLevel();

        setScreen(gameScreen);
	}



}
