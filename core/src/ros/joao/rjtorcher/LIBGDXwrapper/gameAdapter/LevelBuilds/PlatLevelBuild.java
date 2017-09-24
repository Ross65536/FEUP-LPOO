package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds;

import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;

import java.util.Random;

/**
 * Class that helps build the Platform level type.
 */
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
    public static final ros.joao.rjtorcher.Vector2D createCameraDimsPlat ()
    {
        final double cameraXDim = WORLD_X_DIM/10f;
        final double cameraYDim = cameraXDim* ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO;
        WORLD_Y_DIM = cameraYDim*10f;
        return new ros.joao.rjtorcher.Vector2D(cameraXDim, cameraYDim);
    }

    /**
     * Creates the world dimensions for this mode.
     * @return
     */
    public static final ros.joao.rjtorcher.Vector2D createWorldDimsPlat ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = WORLD_Y_DIM;
        return new ros.joao.rjtorcher.Vector2D(worldXDim, worldYDim);
    }

    /**
     * Creates the hero for this world.
     * @param worldDims world dimensions
     * @param cameraDims camera dimensions
     * @return the created hero
     */
    public static final Hero createHero2(final ros.joao.rjtorcher.Vector2D worldDims, final ros.joao.rjtorcher.Vector2D cameraDims)
    {
        final double heroHeight = cameraDims.y/(HERO_HEIGHT_BY_SCREEN_HEIGHT);

        final CommonConsts.CharacterConstants heroConsts = CommonConsts.getCharacterConstants(Hero.class);
        final ros.joao.rjtorcher.Vector2D heroPos = new ros.joao.rjtorcher.Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final ros.joao.rjtorcher.Vector2D heroDims = new ros.joao.rjtorcher.Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new ros.joao.rjtorcher.gameLogic.Characters.HeroLifesWrapper(heroPos, heroDims, heroConsts.speedMult, numberOfLifes);
    }

    /**
     * Generates a random background for the game.
     */
    public static void  reloadBackground(){
        if(random == null)
            random = new Random();
        ros.joao.rjtorcher.LIBGDXwrapper.PathConstants.NIGHTBACKGROUND_IMAGE = ros.joao.rjtorcher.LIBGDXwrapper.PathConstants.SPRITE_PATH + "backgroundren" + random.nextInt(ros.joao.rjtorcher.LIBGDXwrapper.PathConstants.NUMBEROFBACKGROUNDS) +  ".png";
        ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().reloadNightBackground();
    }

}
