package com.mygdx.game.gameLogic.LogicWorlds;

import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInput;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemyFeature;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

//added
public abstract class GameWorld implements IGameWorld, IGameWorldHeroInputs, DummyEnemyFeature {
    //data
    protected Vector2D worldDimensions;
    protected Hero hero;
    protected boolean gamePlayable;
    DummyEnemies dummyEnemies;


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

    public boolean isGamePlayable()
    {
        return this.gamePlayable;
    }

    public IGameWorldHeroInputs getWorldInputs()
    {
        return this;
    }

    //// abstract ----------------
    /**
     * Updates all characters in the world, checks collisions
     *
     * @param deltaT
     */
    abstract public void update (float deltaT);
    abstract protected void checkHeroJump();


    protected void updateHero(float deltaT)
    {
        hero.update(deltaT);
        checkHeroJump();
    }

    //// hero inputs -------
    @Override
    public void moveHeroHorizontal(final double heroXMovement)
    {
        final StatisticsInput statisticsInput = dummyEnemies.getStatsticsInput();
        statisticsInput.registerMovement();

        hero.setXMovement(heroXMovement);
    }

    @Override
    public void heroJump(final double gravityStrength) {
        final StatisticsInput statisticsInput = dummyEnemies.getStatsticsInput();
        statisticsInput.registerJump();

        hero.jump(gravityStrength);
    }

}
