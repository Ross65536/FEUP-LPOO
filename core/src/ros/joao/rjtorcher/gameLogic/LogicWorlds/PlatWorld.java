package ros.joao.rjtorcher.gameLogic.LogicWorlds;

import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.gameLogic.Characters.EnemyInfo;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;
import ros.joao.rjtorcher.gameLogic.Characters.Platform;
import ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.LightRecharger;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLight;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.Platforms;

import java.util.List;

/**
 * This class manages the game's logic for the platforms mode of the game.
 */
public class PlatWorld extends GameWorld implements ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature, ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature, ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature, HeroLifesFeature {

    /**
     * Platforms Component, manages the logic regarding the platforms on the game.
     */
    protected Platforms platforms;

    /**
     * Platforms Component, manages the logic regarding the light rechargers on the game.
     */
    protected LightRecharger lightRecharger;

    private double cameraWidth;
    private double cameraHeight;

    /**
     * Minimun light the hero can have.
     */
    float leastLightPercentage = 0.1f;

    /**
     * Constructor.
     * @param cameraDim Dimensions of the camera
     * @param worldDims Dimensions of the world
     * @param hero the hero
     * @param stageDirector
     */
    public PlatWorld(final ros.joao.rjtorcher.Vector2D cameraDim, final ros.joao.rjtorcher.Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);

        this.cameraWidth = cameraDim.x;
        this.cameraHeight = cameraDim.y;

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector, this);

        light = new HeroLight(hero);
        light.getLightInfo().setVelocityDisappearance(1.0/120.0); //2 minutes to disapear

        platforms = new Platforms(hero, worldDims, new ros.joao.rjtorcher.Vector2D(this.cameraWidth,this.cameraHeight));

        lightRecharger = new LightRecharger(hero, platforms.getAllPlatforms(), light.getLightInfo(), cameraDim);

        //TODO remove
        if (CommonConsts.INPUT_DEBUG)
            createDummyEnemies();

    }

    /**
     * Empty constructor.
     */
    protected PlatWorld(){
        super();
    }

    /**
     * Keeps the hero from going over the edges.
     */
    protected void checkHeroAtWorldEdges() {
        final double heroXPos = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double worldXMax = worldDimensions.x - heroXDim * (1.0 - ros.joao.rjtorcher.LIBGDXwrapper.PathConstants.HERO_WORLD_EDGE_LEEWAY);
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
    protected void checkLost(){
        if(this.checkEnemyCollisions() > 0)
        {
            if(takeLife()==0) {
                gamePlayable = false;
                System.out.println("Game Lost");
            }
        }

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
    public void placeEnemy(final ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy){
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
    protected void placeEnemyYPos(ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy) {
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
    public ros.joao.rjtorcher.gameLogic.Characters.Light getLightInfo(){
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
    public ros.joao.rjtorcher.gameLogic.Characters.Recharger getItemInfo(){
        return lightRecharger.getItemInfo();
    }

    @Override
    public void updateRechargerItem(float deltaT){
        lightRecharger.updateRechargerItem(deltaT);
    }

    @Override
    public ros.joao.rjtorcher.gameLogic.Characters.Light getItemLight(){ return lightRecharger.getItemLight();}


    ////////Hero Lifes Implementation///////////
    @Override
    public int getNumberOfLifes(){ return ((ros.joao.rjtorcher.gameLogic.Characters.HeroLifesWrapper)hero).getNumberOfLifes();}

    @Override
    public boolean isImmune(){ return ((ros.joao.rjtorcher.gameLogic.Characters.HeroLifesWrapper)hero).isImmune();}

    @Override
    public int takeLife(){ return ((ros.joao.rjtorcher.gameLogic.Characters.HeroLifesWrapper)hero).takeLife();}

}
