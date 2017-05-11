package com.mygdx.game.gameLogic.LogicWorlds;


import com.mygdx.game.Constants;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemyFeature;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;

public class DiscWorld extends GameWorld implements DummyEnemyFeature {
    protected static final double ENEMY_GENERATION_YMULT = 1.0;

    DummyEnemies dummyEnemies;

    public DiscWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector);

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



    //// hero inputs -------
    @Override
    public void moveHeroHorizontal(final double heroXMovement)
    {
        hero.setXMovement(heroXMovement);
    }

    @Override
    public void heroJump(final double gravityStrength) {
        hero.jump(gravityStrength);
    }



    ////////Dummny Enemy Implementation/////////
    @Override
    public void createDummyEnemies(){
        dummyEnemies.createDummyEnemies();
    }

    @Override
    public void updateEnemieStatistics(float deltaT){
        dummyEnemies.updateEnemieStatistics(deltaT);
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

}