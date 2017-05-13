package com.mygdx.game.gameLogic.LogicWorlds;


import com.mygdx.game.Constants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Lights;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightsFeature;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;
import java.util.Random;

public class DiscWorld extends GameWorld implements LightsFeature {
    protected static final double ENEMY_GENERATION_YMULT = 1.0;
    protected static Random random = new Random();

    public DiscWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector, this);

        light = new Lights(hero);

        //TODO remove
        if (Constants.INPUT_DEBUG)
            createDummyEnemies();
    }


    //// specific functions -------------
    protected void tryLoopHero() {
        //loop around level
        final double heroXPos = hero.getXPos();
        if (heroXPos < 0.0)
            hero.setXPos(worldDimensions.x + heroXPos);
        else if (heroXPos > worldDimensions.x)
            hero.setXPos(heroXPos - worldDimensions.x);
    }




    //// abstract implementations ---------------
    @Override
    public void update (float deltaT)
    {
        if (! isGamePlayable())
            return;

        updateEnemieStatistics(deltaT);


        updateHero(deltaT);
        updateLight(deltaT);
        tryLoopHero();

        if(this.checkEnemyCollisions() > 0)
        {
            gamePlayable = false;
            System.out.println("Game Lost");
        }

        if (! Constants.INPUT_DEBUG)
            tryGenerateEnemy();
    }

    @Override
    protected void checkHeroJump()
    {
        if (hero.isJumping() && hero.getYPos() < 0.0) //hero jumping hit ground
        {
            hero.stopJump();
            hero.setYPos(0.0);
        }
    }


    ////////Lights Implementation///////////
    @Override
    public void updateLight(float deltaT){
        light.updateLight(deltaT);
    }

    @Override
    public Light getLightInfo(){
        return light.getLightInfo();
    }

    @Override
    public float getRadiousPercentage(){
        return light.getRadiousPercentage();
    }


    ////////Dummny Enemy Implementation/////////
    @Override
    public void createDummyEnemies(){
        dummyEnemies.createDummyEnemies();
    }


    @Override
    public long checkEnemyCollisions(){
        return dummyEnemies.checkEnemyCollisions();
    }

    @Override
    public void tryGenerateEnemy(){
        dummyEnemies.tryGenerateEnemy();
    }

    @Override
    public List<EnemyInfo> getEnemiesInfo(){
       return dummyEnemies.getEnemiesInfo();
    }

    static final double FLYING_HEIGHT_MIN = 0.5;
    static final double FLYING_HEIGHT_MAX = 0.9;
    static final double FLYING_HEIGHT_DELTA = FLYING_HEIGHT_MAX - FLYING_HEIGHT_MIN;
    public void placeEnemy(Enemy enemy) {
        if (enemy.isFlyingType())
        {
            final double heightRatio = random.nextDouble() * FLYING_HEIGHT_DELTA;
            final double enYHeight = worldDimensions.y * (FLYING_HEIGHT_MIN + heightRatio);
            enemy.setYPos(enYHeight);
        }
        else
            enemy.setYPos(0.0); //ground

        final double enXDelta = worldDimensions.y * ENEMY_GENERATION_YMULT;
        final double heroXPos = hero.getXPos();

        final boolean bool = random.nextBoolean(); //random

        if (bool) //left side spawn
        {
            enemy.setXPos(heroXPos - enXDelta);
            enemy.setMovementDirection(true);
        }
        else //right
        {
            enemy.setXPos(heroXPos + enXDelta);
            enemy.setMovementDirection(false);
        }
    }
}