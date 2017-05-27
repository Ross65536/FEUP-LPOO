package com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.PathConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.HeroLifesWrapper;

import java.util.Random;

public class PlatLevelBuild{
    public static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 4;
    private static final double WORLD_X_DIM = 1000;
    private static double WORLD_Y_DIM;
    public static int numberOfLifes = 3;
    private static Random random = null;

    /**
     * Creates the camera dimensions for this mode.
     * @return
     */
    public static final Vector2D createCameraDimsPlat ()
    {
        final double cameraXDim = WORLD_X_DIM/10f;
        final double cameraYDim = cameraXDim* DeviceConstants.INVERTED_SCREEN_RATIO;
        WORLD_Y_DIM = cameraYDim*10f;
        return new Vector2D(cameraXDim, cameraYDim);
    }

    /**
     * Creates the world dimensions for this mode.
     * @return
     */
    public static final Vector2D createWorldDimsPlat ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = WORLD_Y_DIM;
        return new Vector2D(worldXDim, worldYDim);
    }

    /**
     * Creates the hero for this world.
     * @param worldDims world dimensions
     * @param cameraDims camera dimensions
     * @return the created hero
     */
    public static final Hero createHero2(final Vector2D worldDims, final Vector2D cameraDims)
    {
        final double heroHeight = cameraDims.y/(HERO_HEIGHT_BY_SCREEN_HEIGHT);

        final CommonConsts.CharacterConstants heroConsts = CommonConsts.getCharacterConstants(Hero.class);
        final Vector2D heroPos = new Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final Vector2D heroDims = new Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new HeroLifesWrapper(heroPos, heroDims, heroConsts.speedMult, numberOfLifes);
    }

    /**
     * Generates a random background for the game.
     */
    public static void  reloadBackground(){
        if(random == null)
            random = new Random();
        PathConstants.NIGHTBACKGROUND_IMAGE = PathConstants.SPRITE_PATH + "backgroundren" + random.nextInt(PathConstants.NUMBEROFBACKGROUNDS) +  ".png";
        GameAssetHandler.getGameAssetHandler().reloadNightBackground();
    }

}
