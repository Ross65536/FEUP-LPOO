package com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.Vector2D;

public class DiscLevelBuild {
    private static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static final double WORLD_X_DIM = 1000;


    public static final Vector2D createWorldDims ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = DeviceConstants.INVERTED_SCREEN_RATIO * worldXDim / 100;
        return new Vector2D(worldXDim, worldYDim);
    }

    public static final Hero createHero(final Vector2D worldDims)
    {
        final double heroHeight = worldDims.y/HERO_HEIGHT_BY_SCREEN_HEIGHT;

        final CommonConsts.CharacterConstants heroConsts = CommonConsts.getEnemyConstants(Hero.class);
        final Vector2D heroPos = new Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final Vector2D heroDims = new Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new Hero(heroPos, heroDims, heroConsts.speedMult);
    }
}
