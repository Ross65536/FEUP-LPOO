package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds;

import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.SurvivalGameWorldAdapter;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.PlatGameWorldAdapter;
import ros.joao.rjtorcher.LIBGDXwrapper.PathConstants;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;
import ros.joao.rjtorcher.gameLogic.GameDirector.DifficultyCurves.BalancedCurve;
import ros.joao.rjtorcher.gameLogic.GameDirector.DifficultyCurves.Curve;
import ros.joao.rjtorcher.gameLogic.GameDirector.DifficultyCurves.IncreasingDifficultyCurve;
import ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes.EnemyTypes;
import ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector;
import ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes.EnemyTypesLinear;
import ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.Statistics;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.SurvivalWorld;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.PlatWorld;

import java.util.Arrays;
import java.util.Collection;

/**
 * Class that facillitates the building of levels
 */
public class LevelBuilder {


    //// utilities -----------
    private static void loadAssets(final Collection<String> assets)
    {
        GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        gameAssetHandler.unloadUnneededLevelAssets(assets);
        gameAssetHandler.loadLevelAssets(assets);
    }


    //// core -------------
    private static final double TIME_MEMORY = 2.0; // in seconds
    private static final double STATISTICS_DEVICE_JUMP = 2.0;
    private static final double STATISTICS_DEVICE_MOV = 5.0;
    private static final StageDirector createStageDirector (Curve generator, final EnemyTypes iEnemyTypes)
    {
        final Statistics statistics = new Statistics(STATISTICS_DEVICE_JUMP, STATISTICS_DEVICE_MOV, TIME_MEMORY);
        final Curve curve = generator;
        return new StageDirector(curve, statistics, iEnemyTypes);
    }




    private static final double GROUND_CUTTOFF_DISC = 0.6;
    private static final double FLYING_CUTTOFF_DISC = 0.8;
    private static final double GROUND_BOSS_CUTTOFF_DISC = 0.9;
    private static final double FLYING_BOSS_CUTTOFF_DISC = 1.0;
    private static final double ENEMY_CREATION_DELTAT = 1.0; //seconds between each generate
    private static final int MAX_NUM_ENEMIES = 15;

    /**
     * Builds the survival level
     * @return the level
     */
    public static SurvivalGameWorldAdapter createSurvivalLevel() {
        loadAssets(Arrays.asList(PathConstants.discLevelAssetNames));

        ros.joao.rjtorcher.Vector2D worldDims = SurvivalLevelBuild.createWorldDims();
        Hero hero = SurvivalLevelBuild.createHero(worldDims);

        final Curve generator = new BalancedCurve(ENEMY_CREATION_DELTAT, MAX_NUM_ENEMIES);
        final EnemyTypes enemyTypes = new EnemyTypesLinear(hero.getYDim(), GROUND_CUTTOFF_DISC, FLYING_CUTTOFF_DISC, GROUND_BOSS_CUTTOFF_DISC, FLYING_BOSS_CUTTOFF_DISC);
        StageDirector stageDirector = createStageDirector(generator, enemyTypes);

        ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld = new SurvivalWorld(worldDims, hero, stageDirector);
        SurvivalGameWorldAdapter ret = new SurvivalGameWorldAdapter(worldDims, gameLogicWorld);

        return ret;
    }


    private static final double GROUND_CUTTOFF_PLAT = 0.2;
    private static final double FLYING_CUTTOFF_PLAT = 0.7;
    private static final double GROUND_BOSS_CUTTOFF_PLAT = 0.7;
    private static final double FLYING_BOSS_CUTTOFF_PLAT = 1.0;
    private static final double HALF_TIME_RANGE = 60; //in seconds
    private static final double DIFFICULTY_DELTA = 0.2;
    private static final double ENEMY_CREATION_DELTAT_PLAT = 1.5; //seconds between each generate
    private static final int MAX_NUM_ENEMIES_PLAT = 25;

    /**
     * Buidls the platforms level
     * @return the level
     */
    public static PlatGameWorldAdapter createPlatformTestLevel() {

        loadAssets(Arrays.asList(PathConstants.platformTestLevelAssetNames));

        ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild.reloadBackground();

        ros.joao.rjtorcher.Vector2D cameraDims = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild.createCameraDimsPlat();
        ros.joao.rjtorcher.Vector2D worldDims = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild.createWorldDimsPlat();
        Hero hero = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild.createHero2(worldDims,cameraDims);

        final Curve generator = new IncreasingDifficultyCurve(DIFFICULTY_DELTA, HALF_TIME_RANGE, ENEMY_CREATION_DELTAT_PLAT, MAX_NUM_ENEMIES_PLAT);
        final EnemyTypes enemyTypes = new EnemyTypesLinear(hero.getYDim(), GROUND_CUTTOFF_PLAT, FLYING_CUTTOFF_PLAT, GROUND_BOSS_CUTTOFF_PLAT, FLYING_BOSS_CUTTOFF_PLAT);
        StageDirector stageDirector = createStageDirector(generator, enemyTypes);

        ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld = new PlatWorld(cameraDims, worldDims, hero, stageDirector);
        PlatGameWorldAdapter ret = new PlatGameWorldAdapter(cameraDims, worldDims, gameLogicWorld);
        return ret;
    }


}
