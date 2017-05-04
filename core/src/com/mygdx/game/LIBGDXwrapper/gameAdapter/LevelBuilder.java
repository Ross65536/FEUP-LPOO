package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.DiscWorld;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurves;
import com.mygdx.game.gameLogic.GameDirector.Statistics;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInfo;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.Arrays;
import java.util.Collection;


public class LevelBuilder {
    private static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static final double WORLD_X_DIM = 100;
    private static final String[] testLevelAssetNames = {GameAssetHandler.imagePathHero, GameAssetHandler.imagePathEnemyGround};

    //// utilities -----------
    private static void loadAssets(final Collection<String> assets)
    {
        GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        gameAssetHandler.unloadUnneededLevelAssets(assets);
        gameAssetHandler.loadLevelAssets(assets);
    }

    private static final Vector2D createWorldDims ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = DeviceConstants.INVERTED_SCREEN_RATIO * worldXDim / 10;
        return new Vector2D(worldXDim, worldYDim);
    }

    private static final Hero createHero(final Vector2D worldDims)
    {
        final double heroHeight = worldDims.y/HERO_HEIGHT_BY_SCREEN_HEIGHT;

        final Constants.CharacterConstants heroConsts = Constants.getEnemyConstants(Hero.class);
        final Vector2D heroPos = new Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final Vector2D heroDims = new Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new Hero(heroPos, heroDims, heroConsts.speedMult);
    }

    private static final StageDirector createStageDirector (DifficultyCurves.generator generator, final double heroYDIm)
    {
        final Statistics statistics = new Statistics();
        final DifficultyCurves.generator curve = generator;
        return new StageDirector(curve, statistics, heroYDIm);
    }


    //// core -------------
    public static GameWorldAdapter createTestLevel() {
        loadAssets(Arrays.asList(testLevelAssetNames));

        Vector2D worldDims = createWorldDims();
        Hero hero = createHero(worldDims);

        final DifficultyCurves.generator generator = (final StatisticsInfo stats) -> DifficultyCurves.randomGenerator(stats); // pass function as argument
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim());

        GameWorld gameLogicWorld = new DiscWorld(worldDims, hero, stageDirector);
        GameWorldAdapter ret = new GameWorldAdapter(worldDims, gameLogicWorld);

        return ret;
    }






}
