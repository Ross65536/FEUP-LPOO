package com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.DiscGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.PlatGameWorldAdapter;
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


public class LevelBuilder {


    //// utilities -----------
    private static void loadAssets(final Collection<String> assets)
    {
        GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        gameAssetHandler.unloadUnneededLevelAssets(assets);
        gameAssetHandler.loadLevelAssets(assets);
    }


    //// core -------------
    private static final double ENEMY_CREATION_DELTAT = 1.5; //seconds between each generate
    private static final int MAX_NUM_ENEMIES = 10;
    private static final StageDirector createStageDirector (Curves generator, final double heroYDIm)
    {
        final Statistics statistics = new Statistics();
        final Curves curve = generator;
        StageDirectorEnemyTypesAdapter.IEnemyTypes iEnemyTypes = (StageDirector itself, double difficulty) -> StageDirectorEnemyTypesAdapter.testEnemyTypes(itself, difficulty);
        return new StageDirector(curve, statistics, heroYDIm, iEnemyTypes);
    }




    public static DiscGameWorldAdapter createTestLevel() {
        loadAssets(Arrays.asList(DiscLevelBuild.testLevelAssetNames));

        Vector2D worldDims = DiscLevelBuild.createWorldDims();
        Hero hero = DiscLevelBuild.createHero(worldDims);

        final Curves generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim());

        GameWorld gameLogicWorld = new DiscWorld(worldDims, hero, stageDirector);
        DiscGameWorldAdapter ret = new DiscGameWorldAdapter(worldDims, gameLogicWorld);

        return ret;
    }



    public static PlatGameWorldAdapter createPlatformTestLevel() {
        loadAssets(Arrays.asList(PlatLevelBuild.platformTestLevelAssetNames));

        Vector2D worldDims = PlatLevelBuild.createWorldDimsPlat();
        Vector2D cameraDims = PlatLevelBuild.createCameraDimsPlat();
        Hero hero = PlatLevelBuild.createHero2(worldDims,cameraDims);

        final Curves generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        StageDirector stageDirector = createStageDirector(generator, hero.getYDim());

        GameWorld gameLogicWorld = new PlatWorld(worldDims, hero, stageDirector);
        PlatGameWorldAdapter ret = new PlatGameWorldAdapter(worldDims, gameLogicWorld);
        return ret;
    }


}
