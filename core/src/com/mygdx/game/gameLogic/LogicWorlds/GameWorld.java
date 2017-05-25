package com.mygdx.game.gameLogic.LogicWorlds;

import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.GameDirector.Statistic.StatisticsInput;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemyFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLight;
import com.mygdx.game.Vector2D;

import java.util.Random;

/**
 * Base Class that functions represents a generic game world, with it's logic, physics, and characters
 */
public abstract class GameWorld implements IGameWorld, IGameWorldHeroInputs, DummyEnemyFeature {
    //data
    protected Vector2D worldDimensions;
    protected Hero hero;
    protected boolean gamePlayable;
    protected static Random random = new Random();
    protected DummyEnemies dummyEnemies;
    protected HeroLight light;
    protected double score = 0;

    /**
     * Empty constructor
     */
    public GameWorld() {

    }


    public float getDangerLevel(){
        return 1-light.getRadiousPercentage();
    }

    /**
     * constructor
     * @param worldDims dimensions of the world
     * @param hero hero character
     */
    public GameWorld(final Vector2D worldDims, Hero hero)
    {
        worldDimensions = new Vector2D(worldDims);
        this.hero = hero;

        this.gamePlayable = true;
    }

    //// Queries --------------
    /**
     * Return hero information (position, dimension, etc).
     * @return
     */
    public final HeroInfo getHeroInfo() {
        return hero;
    }

    /**
     *
     * @return true if game is playable
     */
    public boolean isGamePlayable()
    {
        return this.gamePlayable;
    }

    /**
     *
     * @return object that contains method that can be called to controll the hero
     */
    public IGameWorldHeroInputs getWorldInputs()
    {
        return this;
    }

    //// abstract ----------------
    /**
     * Updates all characters in the world, checks collisions, etc
     *
     * @param deltaT
     */
    abstract public void update (float deltaT);
    abstract protected void checkHeroJump();

    protected static final double ENEMY_GENERATION_YMULT = 1.0;

    /**
     * places a generated enemy in the appropriate place, updating it's internal coordinates
     * @param enemy enemy to be placed
     */
    abstract public void placeEnemy(final Enemy enemy);

    abstract protected void placeEnemyYPos(Enemy enemy);

    abstract protected void checkHeroAtWorldEdges();

    /**
     *
     * @return the score of the level type
     */
    abstract public String getScore();

    //// hero inputs -------

    /**
     * Moves the hero on the X axis.
     * The argument is the intensity of the speed of the max hero velocity
     * @param heroXMovement should go from -1.0 to 1.0, which makes the hero move to the left and right respectively, and 0.0 stops the hero
     */
    @Override
    public void moveHeroHorizontal(final double heroXMovement)
    {
        if (! gamePlayable)
            return ;

        final StatisticsInput statisticsInput = dummyEnemies.getStatsticsInput();
        statisticsInput.registerMovement();

        hero.setXMovement(heroXMovement);
    }

    /**
     * Makes the hero jump
     * Can be called on each pollOrientation to create a longer or shorter jump depending on gravity strenght
     * @param gravityStrength sets the strength of the gravity. should go from 0.5 to 1.0
     */
    @Override
    public void heroJump(final double gravityStrength) {
        final StatisticsInput statisticsInput = dummyEnemies.getStatsticsInput();
        statisticsInput.registerJump();

        hero.jump(gravityStrength);
    }


    @Override
    public void updateEnemieStatistics(float deltaT){
        dummyEnemies.updateEnemieStatistics(deltaT);
        StatisticsInput statisticsInput = dummyEnemies.getStatsticsInput();
        statisticsInput.setLightLevel(light.getRadiousPercentage());

    }



    protected void updateHero(float deltaT)
    {
        hero.update(deltaT);
        checkHeroJump();
    }
}
