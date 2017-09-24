package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds;

import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;

/**
 * Class that helps build the Surival level type
 */
public class SurvivalLevelBuild {
    private static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static final double WORLD_X_DIM = 1000;


    /**
     *
     * @return world dimensions
     */
    public static final ros.joao.rjtorcher.Vector2D createWorldDims ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * worldXDim / 100;
        return new ros.joao.rjtorcher.Vector2D(worldXDim, worldYDim);
    }

    /**
     * creates an appropriate hero
     * @param worldDims the dimensiosn of the world
     * @return the created hero
     */
    public static final Hero createHero(final ros.joao.rjtorcher.Vector2D worldDims)
    {
        final double heroHeight = worldDims.y/HERO_HEIGHT_BY_SCREEN_HEIGHT;

        final CommonConsts.CharacterConstants heroConsts = CommonConsts.getCharacterConstants(Hero.class);
        final ros.joao.rjtorcher.Vector2D heroPos = new ros.joao.rjtorcher.Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final ros.joao.rjtorcher.Vector2D heroDims = new ros.joao.rjtorcher.Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new Hero(heroPos, heroDims, heroConsts.speedMult);
    }
}
