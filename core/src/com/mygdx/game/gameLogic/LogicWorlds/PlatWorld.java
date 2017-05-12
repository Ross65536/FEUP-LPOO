package com.mygdx.game.gameLogic.LogicWorlds;

import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemyFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Lights;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightsFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Platforms;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;

public class PlatWorld extends GameWorld implements PlatformFeature, LightsFeature {

    protected double cameraWidth;
    protected double cameraHeight;

    Platforms platforms;


    public PlatWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);
        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector);

        light = new Lights(hero);

        platforms = new Platforms(hero, worldDims, new Vector2D(this.cameraWidth,this.cameraHeight));

        //TODO remove
        if (Constants.INPUT_DEBUG)
            createDummyEnemies();

    }


    protected void tryLoopHero() {
        final double heroXPos = hero.getXPos();
        if (heroXPos < 0.0)
            hero.setXPos(worldDimensions.x + heroXPos);
        else if (heroXPos > worldDimensions.x)
            hero.setXPos(heroXPos - worldDimensions.x);
    }


    public float getDangerLevel(){
        return 1-light.getRadiousPercentage();
    }


    //// abstract implementations ---------------
    @Override
    public void update (float deltaT)
    {
        if (! isGamePlayable())
            return;

        updateEnemieStatistics(deltaT);

        updatePlatformsInRange();
        checkPlatformCollisions();

        updateHero(deltaT);
        updateLight(deltaT);
        tryLoopHero();

        if(this.checkEnemyCollisions() > 0)
        {
            gamePlayable = false;

            //TODO remove
            System.out.println("Game Lost");
        }

        if (! Constants.INPUT_DEBUG)
            tryGenerateEnemy();
    }

    @Override
    protected void checkHeroJump()
    {
        double yValue = getLandingYValue();

        if (hero.isJumping() && hero.getYPos() < yValue)
        {
            hero.stopJump();
            hero.setYPos(yValue);
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

    ////////Platforms Implementation////////////
    @Override
    public double getLandingYValue(){
        return platforms.getLandingYValue();
    }

    @Override
    public List<Platform> getPlatforms(){
        return platforms.getPlatforms();
    }

    @Override
    public void updatePlatformsInRange(){
        platforms.updatePlatformsInRange();
    }

    @Override
    public void checkPlatformCollisions(){
        platforms.checkPlatformCollisions();
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
}
