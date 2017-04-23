package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelAssetsConstants;
import com.mygdx.game.LIBGDXwrapper.LevelAdapters.TestLevel;


import java.util.Collection;


public class MyGame extends Game {
    private GameScreen gameScreen;
    private AssetManager gameAssetManager;


    @Override
	public void create ()
    {
        GameSettings gameSettings = new GameSettings();
        gameSettings.setApplicationPlatform(Gdx.app.getType());

        gameScreen = new GameScreen(this, gameSettings);
        gameAssetManager = new AssetManager(new InternalFileHandleResolver());

        //screen decisions
        startGameTest();
	}


    private void unloadUnneededLevelAssets(final Collection<String> assetPaths)
    {
        final Array<String> currentAssets = gameAssetManager.getAssetNames(); //Array is from LIBGDX
        for (String currPath : currentAssets) //unloads uneeded assets
        {
            if (! assetPaths.contains(currPath))
            {
                gameAssetManager.unload(currPath);
            }
        }
    }

    /**
     * Doesnt load duplicates if they exist
     * @param assetPaths
     */
    private void loadLevelAssets (final Collection<String> assetPaths)
    {
        for (final String path : assetPaths) //loads missing assets
        {
            gameAssetManager.load(path, (java.lang.Class<Object>) LevelAssetsConstants.mapPathToType.get(path));
        }

    }


	private void startGameTest()
	{
        unloadUnneededLevelAssets(TestLevel.levelAssetPaths);
        loadLevelAssets(TestLevel.levelAssetPaths);


        TestLevel testLevel = new TestLevel();

        gameScreen.LoadLevel(testLevel);
        testLevel.getLevelAssets(gameAssetManager);

        setScreen(gameScreen);
	}



}
