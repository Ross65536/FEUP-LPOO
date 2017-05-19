package com.mygdx.game.gameLogic.LogicWorlds;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.PathConstants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.HeroLifesWrapper;
import com.mygdx.game.gameLogic.Characters.Recharger;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.GameDirector.StageDirectors.StageDirector;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRecharger;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLight;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Platforms;
import com.mygdx.game.Vector2D;
import java.util.List;
import java.util.Random;

public class PlatWorld extends GameWorld implements PlatformFeature, HeroLightFeature, LightRechargerFeature, HeroLifesFeature {

    Platforms platforms;

    LightRecharger lightRecharger;

    private double cameraWidth;
    private double cameraHeight;

    public PlatWorld(final Vector2D cameraDim, final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);

        this.cameraWidth = cameraDim.x;
        this.cameraHeight = cameraDim.y;

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector, this);

        light = new HeroLight(hero);
        light.getLightInfo().setVelocityDisappearance(1.0/100.0);

        platforms = new Platforms(hero, worldDims, new Vector2D(this.cameraWidth,this.cameraHeight));

        lightRecharger = new LightRecharger(hero, platforms.getAllPlatforms(), light.getLightInfo(), cameraDim);

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

        updateRechargerItem(deltaT);

        checkLost();

        if (! CommonConsts.INPUT_DEBUG)
            tryGenerateEnemy();
    }


    private void checkLost(){
        if(this.checkEnemyCollisions() > 0)
        {
            if(takeLife()==0) {
                gamePlayable = false;
                System.out.println("Game Lost");
            }
        }

        float leastLightPercentage = 0.1f;
        if(light.getRadiousPercentage()<=leastLightPercentage){

            gamePlayable = false;
            //TODO remove
            System.out.println("Game Lost");
        }
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

    @Override
    public void placeEnemy(final Enemy enemy){
        placeEnemyYPos(enemy);

        final double enXDelta = cameraWidth * ENEMY_GENERATION_YMULT;
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

    @Override
    protected void placeEnemyYPos(Enemy enemy) {
        if (enemy.isFlyingType())
        {
            final double extraHeight = random.nextDouble() * (cameraHeight*2f);
            double minHeight = hero.getYPos()-(cameraHeight);
            if(minHeight<hero.getYDim()){
                minHeight=hero.getYDim();
            }
            enemy.setYPos(extraHeight + minHeight);
        }
        else
            enemy.setYPos(0.0); //ground
    }


    ////////HeroLight Implementation///////////
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

    /////////Light Recharger Implementation/////////

    @Override
    public Recharger getItemInfo(){
        return lightRecharger.getItemInfo();
    }

    @Override
    public void updateRechargerItem(float deltaT){
        lightRecharger.updateRechargerItem(deltaT);
    }

    @Override
    public Light getItemLight(){ return lightRecharger.getItemLight();}


    ////////Hero Lifes Implementation///////////
    @Override
    public int getNumberOfLifes(){ return ((HeroLifesWrapper)hero).getNumberOfLifes();}

    @Override
    public boolean isImmune(){ return ((HeroLifesWrapper)hero).isImmune();}

    @Override
    public int takeLife(){ return ((HeroLifesWrapper)hero).takeLife();}

}
