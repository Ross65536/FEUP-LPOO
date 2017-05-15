package com.mygdx.game.gameLogic.LogicWorlds;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.GameDirector.StageDirectors.StageDirector;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Lights;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightsFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Platforms;
import com.mygdx.game.Vector2D;
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

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector, this);

        light = new Lights(hero);

        platforms = new Platforms(hero, worldDims, new Vector2D(this.cameraWidth,this.cameraHeight));

        //TODO remove
        if (CommonConsts.INPUT_DEBUG)
            createDummyEnemies();

    }


    protected void checkHeroAtWorldEdges() {
        final double heroXPos = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double worldXMax = worldDimensions.x - heroXDim * (1.0 - PathConstants.HERO_WORLD_EDGE_LEEWAY);
        if (heroXPos < 0.0)
            hero.setXPos(0.0);
        else if (heroXPos > worldXMax)
            hero.setXPos(worldXMax);
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
        checkHeroAtWorldEdges();

        if(this.checkEnemyCollisions() > 0)
        {
            gamePlayable = false;

            //TODO remove
            System.out.println("Game Lost");
        }

        if (! CommonConsts.INPUT_DEBUG)
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

    static final double FLYING_HEIGHT_MIN = 0.5;
    static final double FLYING_HEIGHT_MAX = 1.3;
    static final double FLYING_HEIGHT_DELTA = FLYING_HEIGHT_MAX - FLYING_HEIGHT_MIN;
    @Override
    public void placeEnemyYPos(Enemy enemy) {
        if (enemy.isFlyingType())
        {
            final double heightRatio = random.nextDouble() * FLYING_HEIGHT_DELTA;
            final double enYHeight = worldDimensions.y * (FLYING_HEIGHT_MIN + heightRatio);
            enemy.setYPos(enYHeight);
        }
        else
            enemy.setYPos(0.0); //ground
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
