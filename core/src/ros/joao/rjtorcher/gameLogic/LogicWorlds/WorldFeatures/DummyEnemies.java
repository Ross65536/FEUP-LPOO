package ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures;

import ros.joao.rjtorcher.gameLogic.Characters.EnemyInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Enemy feature implemented has default for all worlds.
 */
public class DummyEnemies implements DummyEnemyFeature {


    protected static final double ENEMY_DELETION_RANGE_MULT = 3.0;

    protected static Random random = new Random();

    private ArrayList<ros.joao.rjtorcher.gameLogic.Characters.Enemy> enemies;
    private ros.joao.rjtorcher.gameLogic.Characters.Hero hero;
    protected ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector stageDirector;
    protected ros.joao.rjtorcher.Vector2D worldDimensions;

    private ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameWorld;


    /**
     * updates all enemies with delta time
     * @param deltaT delta time
     * @param statisticsInput statistics input
     */
    protected void updateEnemies(float deltaT, ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.StatisticsInput statisticsInput) {
        final double enemyDeletionRange = ENEMY_DELETION_RANGE_MULT * worldDimensions.y;
        int numGroundDeletions=0;
        int numFlyingDeletions=0;

        Iterator<ros.joao.rjtorcher.gameLogic.Characters.Enemy> itr = enemies.iterator();
        while(itr.hasNext())
        {
            ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy = itr.next();
            enemy.update(deltaT);

            final double deltaXPos = Math.abs(enemy.getXPos() - hero.getXPos());
            if (deltaXPos > enemyDeletionRange) //remove enemy if out of range
            {
                if (enemy.isFlyingType())
                    numFlyingDeletions++;
                else
                    numGroundDeletions++;

                itr.remove();

            }
        }


        statisticsInput.updateNumberOfGroundEnemies(- numGroundDeletions);
        statisticsInput.updateNumberOfFlyingEnemies(- numFlyingDeletions);

        return;
    }

    /**
     * Constructor
     * @param hero the hero
     * @param worldDims world dimensions
     * @param stageDirector the stage director to manage dificulty
     * @param gameWorld the current game mode/world
     */
    public DummyEnemies(ros.joao.rjtorcher.gameLogic.Characters.Hero hero, ros.joao.rjtorcher.Vector2D worldDims, ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector stageDirector, ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameWorld){
        enemies = new ArrayList<ros.joao.rjtorcher.gameLogic.Characters.Enemy>();
        this.hero = hero;
        this.stageDirector = stageDirector;
        worldDimensions = new ros.joao.rjtorcher.Vector2D(worldDims);

        this.gameWorld = gameWorld;
    }

    /**
     * Creates all enemies.
     * For debug.
     */
    public void createDummyEnemies () //testing function
    {
        final double heroX = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double heroYDim = hero.getYDim();

        final ros.joao.rjtorcher.CommonConsts.CharacterConstants characterConstants = ros.joao.rjtorcher.CommonConsts.getEnemyConstants(ros.joao.rjtorcher.CommonConsts.ENEMY_GROUND_ARRAY_INDEX);

        final double enYDim = characterConstants.dimYMult * heroYDim;
        final double enXDim = enYDim * characterConstants.aspectRatio;

        ros.joao.rjtorcher.Vector2D dims = new ros.joao.rjtorcher.Vector2D(enXDim, enYDim);

        ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy1 = new ros.joao.rjtorcher.gameLogic.Characters.EnemyGround(new ros.joao.rjtorcher.Vector2D(heroX + 20, 0), dims, new ros.joao.rjtorcher.Vector2D(0,0), false);
        enemies.add(enemy1);
        ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy2 = new ros.joao.rjtorcher.gameLogic.Characters.EnemyGround(new ros.joao.rjtorcher.Vector2D(heroX - 20, 0), dims, new ros.joao.rjtorcher.Vector2D(0,0), false);
        enemies.add(enemy2);

    }

    /**
     * tries generating an enemy.
     */
    public void tryGenerateEnemy()
    {
        final ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy = stageDirector.tryGenerateEnemy();
        if (enemy != null)
        {
            gameWorld.placeEnemy(enemy); //specific placement
            enemies.add(enemy);
        }
    }


    /**
     * Updtes the enemy statistics.
     * @param deltaT
     */
    public void updateEnemieStatistics(float deltaT)
    {

        ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.StatisticsInput statisticsInput = stageDirector.getStatsticsInput();
        statisticsInput.update(deltaT);

        updateEnemies(deltaT, statisticsInput);
    }

    /**
     * Returns the stage director.
     * @return the stage director.
     */
    public ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector getStageDirector()
    {
        return stageDirector;
    }



    /**
     * return an unmodifiable collection of enemies containing information about them to be able to draw them
     */
    public List<EnemyInfo> getEnemiesInfo()
    {
        List<EnemyInfo> ret = (List) enemies;
        return Collections.unmodifiableList(ret);
    }

    /**
     * @return number of collisions
     */
    public long checkEnemyCollisions() {
        long numCollisions =0;
        for (ros.joao.rjtorcher.gameLogic.Characters.Enemy en : enemies)
            if (hero.checkCollision(en))
                numCollisions ++;

        return numCollisions;
    }

    public ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.StatisticsInput getStatsticsInput() {
        return stageDirector.getStatsticsInput();
    }
}


