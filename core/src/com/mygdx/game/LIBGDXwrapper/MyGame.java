package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;


public class MyGame extends Game {
    private GameScreen gameScreen;


    @Override
	public void create ()
    {

        GameSettings gameSettings = new GameSettings();
        gameScreen = new GameScreen(this, gameSettings);


        //screen decisions
        startGameTest();
	}


	private void startGameTest()
	{
        TestLevel testLevel = new TestLevel();
        gameScreen.LoadLevel(testLevel);

        setScreen(gameScreen);
	}



}
