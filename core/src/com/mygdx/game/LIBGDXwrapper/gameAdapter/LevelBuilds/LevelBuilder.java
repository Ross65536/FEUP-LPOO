package com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.DiscGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.PlatGameWorldAdapter;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.BalancedCurve;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;
import com.mygdx.game.gameLogic.GameDirector.StageDirectors.IEnemyTypes;
import com.mygdx.game.gameLogic.GameDirector.StageDirectors.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.StageDirectors.EnemyTypesLinear;
import com.mygdx.game.gameLogic.GameDirector.Statistics;
import com.mygdx.game.gameLogic.LogicWorlds.DiscWorld;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.PlatWorld;
import com.mygdx.game.Vector2D;

import java.util.Arrays;
import java.util.Collection;


public class LevelBuilder {


    //// utilities -----------
    private static void loadAssets(final Collection<String> assets)
    {
        GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        gameAssetHandler.unloadUnneededLevelAssets(assets);
        gameAssetHandler.loadLevelAssets(assets);
    }


    //// core -------------
    private static final double ENEMY_CREATION_DELTAT = 3.0; //seconds between each generate
    private static final int MAX_NUM_ENEMIES = 10;
    private static final double STATISTICS_DEVICE_JUMP = 2.0;
    private static final double STATISTICS_DEVICE_MOV = 5.0;
    private static final StageDirector createStageDirector (Curves generator, final double heroYDIm, final IEnemyTypes iEnemyTypes)
    {
        final Statistics statistics = new Statistics(STATISTICS_DEVICE_JUMP, STATISTICS_DEVICE_MOV);
        final Curves curve = generator;
        return new StageDirector(curve, statistics, heroYDIm, iEnemyTypes);
    }




    public static DiscGameWorldAdapter createTestLevel() {
        loadAssets(Arrays.asList(PathConstants.discLevelAssetNames));

        Vector2D worldDims = DiscLevelBuild.createWorldDims();
        Hero hero = DiscLevelBuild.createHero(worldDims);

        final Curves generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        final IEnemyTypes iEnemyTypes = new EnemyTypesLinear(0.75, 1.0);
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim(), iEnemyTypes);

        GameWorld gameLogicWorld = new DiscWorld(worldDims, hero, stageDirector);
        DiscGameWorldAdapter ret = new DiscGameWorldAdapter(worldDims, gameLogicWorld);

        return ret;
    }



    public static PlatGameWorldAdapter createPlatformTestLevel() {
        loadAssets(Arrays.asList(PathConstants.platformTestLevelAssetNames));

        Vector2D cameraDims = PlatLevelBuild.createCameraDimsPlat();
        Vector2D worldDims = PlatLevelBuild.createWorldDimsPlat();
        Hero hero = PlatLevelBuild.createHero2(worldDims,cameraDims);

        final Curves generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        final IEnemyTypes iEnemyTypes = new EnemyTypesLinear(0.25, 1.0);
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim(), iEnemyTypes);

        GameWorld gameLogicWorld = new PlatWorld(cameraDims, worldDims, hero, stageDirector);
        PlatGameWorldAdapter ret = new PlatGameWorldAdapter(cameraDims, worldDims, gameLogicWorld);
        return ret;
    }


}
