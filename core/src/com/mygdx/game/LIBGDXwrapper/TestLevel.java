package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Hero;

public class TestLevel extends GameLevel {
    public static final String heroImagePath = "person.png";
    public TestLevel()
    {
        super();
        createTestLevel();
    }

    public void createTestLevel() {
        worldXDim = 100;
        worldYDim = SCREEN_RATIO * worldXDim / 10;
        Hero hero = new Hero(worldXDim / 2f, 0, worldYDim/8, worldYDim/4); //worldYDim/8, worldYDim/4 are wrong
        gameLogicWorld = new GameWorld(worldXDim, worldYDim, hero);
    }


    public void loadLevelAssets(AssetManager gameAssetManager) {
        gameAssetManager.load(  heroImagePath , Texture.class);
    }

    public void finishLoadingAssets(AssetManager gameAssetManager) {
        gameAssetManager.finishLoading();

        heroTex = gameAssetManager.get(heroImagePath);
    }

    public void unloadLevelAssets(AssetManager gameAssetManager) {
        gameAssetManager.unload(heroImagePath);
    }

}
