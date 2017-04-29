package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelAssetsConstants;
import com.mygdx.game.LIBGDXwrapper.LevelAdapters.TestLevel;
import java.util.Collection;


public class MyGame extends Game {
    private GameScreen gameScreen;
    private AssetManager gameAssetManager;

    private MenuScreen menuScreen;

    private GameSettings gameSettings;

    public static enum GameInstr{RESUME, RESTART, START};
    public static enum MenuInstr{PAUSE, EXIT};

    @Override
	public void create ()
    {
        gameSettings = new GameSettings();
        gameSettings.setApplicationPlatform(Gdx.app.getType());

        gameScreen = new GameScreen(this, gameSettings);//gameSettings não devia de ser parametro
        menuScreen = new MenuScreen(this);

        setScreen(menuScreen);
        gameAssetManager = new AssetManager(new InternalFileHandleResolver());

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

	}

    public void SwicthToMenuScreen(MenuInstr instruction){
        switch (instruction){
            case PAUSE:
                menuScreen.pauseGame();
                break;
            case EXIT:
                gameScreen.nullifyLevel();
                menuScreen.backToMenu();
                break;
        }
        setScreen(menuScreen);
    }


    public void SwicthToGameScreen(GameInstr instruction){
        gameScreen.registerInputHandler();
        switch (instruction){
            case RESUME:

                break;
            case RESTART:
                startGameTest();
                break;
            case START:
                startGameTest();
                break;
        }
        setScreen(gameScreen);
    }


    public GameSettings getGameSettings(){
        return this.gameSettings;
    }
}
