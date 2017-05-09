package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.BalancedCurve;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.StageDirectorEnemyTypesAdapter;
import com.mygdx.game.gameLogic.GameDirector.Statistics;
import com.mygdx.game.gameLogic.LogicWorlds.DiscWorld;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.PlatWorld;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.Arrays;
import java.util.Collection;
import java.util.Vector;


public class LevelBuilder {
    private static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static final double WORLD_X_DIM = 1000;
    private static final String[] testLevelAssetNames = {PathConstants.HERO_IMAGE_PATH, PathConstants.ENEMY_GROUND_IMAGE_PATH};

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

    private static final StageDirector createStageDirector (Curves generator, final double heroYDIm)
    {
        final Statistics statistics = new Statistics();
        final Curves curve = generator;
        StageDirectorEnemyTypesAdapter.IEnemyTypes iEnemyTypes = (StageDirector itself, double difficulty) -> StageDirectorEnemyTypesAdapter.testEnemyTypes(itself, difficulty);
        return new StageDirector(curve, statistics, heroYDIm, iEnemyTypes);
    }


    //// core -------------
    private static final double ENEMY_CREATION_DELTAT = 1.5; //seconds between each generate
    private static final int MAX_NUM_ENEMIES = 10;
    public static GameWorldAdapter createTestLevel() {
        loadAssets(Arrays.asList(testLevelAssetNames));

        Vector2D worldDims = createWorldDims();
        Hero hero = createHero(worldDims);

        final Curves generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim());

        GameWorld gameLogicWorld = new DiscWorld(worldDims, hero, stageDirector);
        GameWorldAdapter ret = new GameWorldAdapter(worldDims, gameLogicWorld);

        return ret;
    }



    private static final String[] platformTestLevelAssetNames = {PathConstants.HERO_IMAGE_PATH, PathConstants.ENEMY_GROUND_IMAGE_PATH, PathConstants.PLATFORM_IMAGE_PATH};

    private static final Vector2D createWorldDimsPlat ()
    {
        final double worldXDim = WORLD_X_DIM;
        final double worldYDim = WORLD_X_DIM/10;
        return new Vector2D(worldXDim, worldYDim);
    }

    private static final Hero createHero2(final Vector2D worldDims, final Vector2D cameraDims)
    {
        final double heroHeight = cameraDims.y/(3*HERO_HEIGHT_BY_SCREEN_HEIGHT);

        final Constants.CharacterConstants heroConsts = Constants.getEnemyConstants(Hero.class);
        final Vector2D heroPos = new Vector2D(worldDims.x / 2f, 0); //hero at middle of world dimensions
        final Vector2D heroDims = new Vector2D(heroHeight * heroConsts.aspectRatio, heroHeight);

        return new Hero(heroPos, heroDims, heroConsts.speedMult);
    }

    private static final Vector2D createCameraDimsPlat ()
    {
        final double cameraXDim = WORLD_X_DIM/10;
        final double cameraYDim = cameraXDim*DeviceConstants.SCREEN_RATIO;
        return new Vector2D(cameraXDim, cameraYDim);
    }

    public static GameWorldAdapter2 createPlatformTestLevel() {
        loadAssets(Arrays.asList(platformTestLevelAssetNames));

        Vector2D worldDims = createWorldDimsPlat();
        Vector2D cameraDims = createCameraDimsPlat();
        Hero hero = createHero2(worldDims,cameraDims);

        final Curves generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim());

        GameWorld gameLogicWorld = new PlatWorld(worldDims, hero, stageDirector);
        GameWorldAdapter2 ret = new GameWorldAdapter2(worldDims, gameLogicWorld);

        return ret;
    }


}
