package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.mygdx.game.CommonConstants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Characters.Hero;

import java.util.Arrays;


public class LevelBuilder {
    public static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static final String[] testLevelAssetNames = {GameAssetHandler.heroImagePath, GameAssetHandler.gremlinImagePath};

    public static GameWorldAdapter createTestLevel() {

        GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        gameAssetHandler.unloadUnneededLevelAssets(Arrays.asList(testLevelAssetNames));
        gameAssetHandler.loadLevelAssets(Arrays.asList(testLevelAssetNames));

        final double worldXDim = 100;
        final double worldYDim = DeviceConstants.INVERTED_SCREEN_RATIO * worldXDim / 10;
        final double heroHeight = worldYDim/HERO_HEIGHT_BY_SCREEN_HEIGHT;

        Hero hero = new Hero(worldXDim / 2f, 0, heroHeight * CommonConstants.heroAspectRatio, heroHeight);
        GameWorld gameLogicWorld = new GameWorld(worldXDim, worldYDim, hero);
        GameWorldAdapter ret = new GameWorldAdapter(worldXDim, worldYDim, gameLogicWorld);

        return ret;
    }






}
