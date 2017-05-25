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
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
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

/**
 * This class manages the game's logic for the platforms mode of the game.
 */
public class PlatWorld extends GameWorld implements PlatformFeature, HeroLightFeature, LightRechargerFeature, HeroLifesFeature {

    /**
     * Platforms Component, manages the logic regarding the platforms on the game.
     */
    private Platforms platforms;

    /**
     * Platforms Component, manages the logic regarding the light rechargers on the game.
     */
    private LightRecharger lightRecharger;

    private double cameraWidth;
    private double cameraHeight;

    /**
     * Constructor.
     * @param cameraDim Dimensions of the camera
     * @param worldDims Dimensions of the world
     * @param hero the hero
     * @param stageDirector
     */
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

    /**
     * Keeps the hero from going over the edges.
     */
    protected void checkHeroAtWorldEdges() {
        final double heroXPos = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double worldXMax = worldDimensions.x - heroXDim * (1.0 - PathConstants.HERO_WORLD_EDGE_LEEWAY);
        if (heroXPos < 0.0)
            hero.setXPos(0.0);
        else if (heroXPos > worldXMax)
            hero.setXPos(worldXMax);
    }


    /**
     * Update method for the game's logic.
     * @param deltaT time between updates
     */
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

        checkScore();
        checkLost();

        if (! CommonConsts.INPUT_DEBUG)
            tryGenerateEnemy();
    }

    /**
     * Checks to see if the score changed.
     */
    protected void checkScore(){
        if(lightRecharger.wasRechargerCaught()){
            score = lightRecharger.totalRechargersCaught();
        }
    }

    /**
     * Returs the score of the game.
     * @return
     */
    @Override
    public String getScore(){
        return ((int)score)+"";
    }

    /**
     * Checks if the game was lost. Hit by enemy or ran out of light.
     */
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

    /**
     * Method used for the heros jump.
     */
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

    /**
     * Places enemy
     * @param enemy enemy to be placed
     */
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

    /**
     * Places an enemy.
     * @param enemy
     */
    @Override
    protected void placeEnemyYPos(Enemy enemy) {
        if (enemy.isFlyingType())
        {
            final double extraHeight = random.nextDouble() * (cameraHeight*3f);
            double minHeight = hero.getYPos()-(cameraHeight*1.5);
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
