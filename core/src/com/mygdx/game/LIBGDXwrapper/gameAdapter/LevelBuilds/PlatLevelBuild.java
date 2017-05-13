package com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds;

import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Vector2D;

public class PlatLevelBuild{
    private static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static final double WORLD_X_DIM = 1000;
    public static final String[] platformTestLevelAssetNames = {PathConstants.HERO_WALKING_IMAGE_PATH, PathConstants.ENEMY_GROUND_IMAGE_PATH, PathConstants.PLATFORM_IMAGE_PATH, PathConstants.LIGHT_IMAGE_PATH};

    public static final Vector2D createWorldDimsPlat ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = WORLD_X_DIM;
        return new Vector2D(worldXDim, worldYDim);
    }

    public static final Hero createHero2(final Vector2D worldDims, final Vector2D cameraDims)
    {
        final double heroHeight = cameraDims.y/(3*HERO_HEIGHT_BY_SCREEN_HEIGHT);

        final Constants.CharacterConstants heroConsts = Constants.getEnemyConstants(Hero.class);
        final Vector2D heroPos = new Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final Vector2D heroDims = new Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new Hero(heroPos, heroDims, heroConsts.speedMult);
    }

    public static final Vector2D createCameraDimsPlat ()
    {
        final double cameraXDim = WORLD_X_DIM/10;
        final double cameraYDim = cameraXDim* DeviceConstants.SCREEN_RATIO;
        return new Vector2D(cameraXDim, cameraYDim);
    }
}
