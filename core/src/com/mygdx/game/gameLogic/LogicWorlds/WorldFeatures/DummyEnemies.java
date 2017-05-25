package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.StageDirectors.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInfo;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInput;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.Vector2D;

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

    private ArrayList<Enemy> enemies;
    private Hero hero;
    protected StageDirector stageDirector;
    protected Vector2D worldDimensions;

    private GameWorld gameWorld;


    /**
     * updates all enemies with delta time
     * @param deltaT delta time
     * @param statisticsInput statistics input
     */
    private void updateEnemies(float deltaT, StatisticsInput statisticsInput) {
        final double enemyDeletionRange = ENEMY_DELETION_RANGE_MULT * worldDimensions.y;
        int numGroundDeletions=0;
        int numFlyingDeletions=0;

        Iterator<Enemy> itr = enemies.iterator();
        while(itr.hasNext())
        {
            Enemy enemy = itr.next();
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
    public DummyEnemies(Hero hero, Vector2D worldDims, StageDirector stageDirector, GameWorld gameWorld){
        enemies = new ArrayList<Enemy>();
        this.hero = hero;
        this.stageDirector = stageDirector;
        worldDimensions = new Vector2D(worldDims);

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

        final CommonConsts.CharacterConstants characterConstants = CommonConsts.getEnemyConstants(CommonConsts.ENEMY_GROUND_ARRAY_INDEX);

        final double enYDim = characterConstants.dimYMult * heroYDim;
        final double enXDim = enYDim * characterConstants.aspectRatio;

        Vector2D dims = new Vector2D(enXDim, enYDim);

        Enemy enemy1 = new EnemyGround(new Vector2D(heroX + 20, 0), dims, new Vector2D(0,0), false);
        enemies.add(enemy1);
        Enemy enemy2 = new EnemyGround(new Vector2D(heroX - 20, 0), dims, new Vector2D(0,0), false);
        enemies.add(enemy2);

    }

    /**
     * tries generating an enemy.
     */
    public void tryGenerateEnemy()
    {
        final Enemy enemy = stageDirector.tryGenerateEnemy();
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


        StatisticsInput statisticsInput = stageDirector.getStatsticsInput();
        statisticsInput.update(deltaT);

        updateEnemies(deltaT, statisticsInput);
    }

    /**
     * Returns the stage director.
     * @return the stage director.
     */
    public StageDirector getStageDirector()
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
        for (Enemy en : enemies)
            if (hero.checkCollision(en))
                numCollisions ++;

        return numCollisions;
    }

    public StatisticsInput getStatsticsInput() {
        return stageDirector.getStatsticsInput();
    }
}


