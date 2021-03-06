package ros.joao.rjtorcher.gameLogic.LogicWorlds;


import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.gameLogic.Characters.EnemyInfo;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;
import ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLight;

import java.util.List;

/**
 * This class manages the games's logic for the survivel mode of the game.
 */
public class SurvivalWorld extends GameWorld implements ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature {

    /**
     *
     * @param worldDims
     * @param hero
     * @param stageDirector
     */
    public SurvivalWorld(final ros.joao.rjtorcher.Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector, this);

        light = new HeroLight(hero);

        //TODO remove
        if (CommonConsts.INPUT_DEBUG)
            createDummyEnemies();
    }

    protected SurvivalWorld(){
        super();
    }



    //// specific functions -------------
    private static final double LOOP_OFFSET = 0.2;
    protected void checkHeroAtWorldEdges() {
        final double scaler = 1.0 - LOOP_OFFSET;
        //loop around level
        final double heroXPos = hero.getXPos();
        if (heroXPos < 0.0)
            hero.setXPos(scaler * worldDimensions.x + heroXPos);
        else if (heroXPos > worldDimensions.x)
            hero.setXPos(heroXPos - scaler * worldDimensions.x);
    }




    //// abstract implementations ---------------

    /**
     * Specific pollOrientation implementation of this world type. Calls the updates methods of it's objects. and checks collisisons, etc.
     * @param deltaT
     */
    @Override
    public void update (float deltaT)
    {
        if (! isGamePlayable())
            return;

        updateEnemieStatistics(deltaT);



        updateHero(deltaT);

        int sign = 1;
        if(hero.isMoving())
            sign*=-1;
        updateLight(sign*deltaT);

        checkHeroAtWorldEdges();

        score+=deltaT;
        tryGenerateEnemy();

        if(this.checkEnemyCollisions() > 0)
            gamePlayable = false;
    }

    /**
     *
     * @return the score which is the time since level start
     */
    public String getScore(){
        String seconds = (((int)score) % 60)+"";
        String minutes  = ((int)(score / 60f)%60)+"";
        String hours = ((int)((int)(score / 60f)/60))+"";
        return hours+":"+minutes+":"+seconds;
    }

    /**
     * Checks if the hero has hit the ground after jumping, and if so, stops the hero's jump
     */
    @Override
    protected void checkHeroJump()
    {
        if (hero.isJumping() && hero.getYPos() < 0.0) //hero jumping hit ground
        {
            hero.stopJump();
            hero.setYPos(0.0);
        }
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
    static final double FLYING_HEIGHT_MAX = 0.85;
    static final double FLYING_HEIGHT_DELTA = FLYING_HEIGHT_MAX - FLYING_HEIGHT_MIN;
    protected void placeEnemyYPos(ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy) {
        if (enemy.isFlyingType())
        {
            final double heightRatio = random.nextDouble() * FLYING_HEIGHT_DELTA;
            final double enYHeight = worldDimensions.y * (FLYING_HEIGHT_MIN + heightRatio);
            enemy.setYPos(enYHeight);
        }
        else
            enemy.setYPos(0.0); //ground
    }

    /**
     * Places the enemy in the appropriate place, with X and Y coordinates which are appropriate
     * @param enemy enemy to be placed
     */
    public void placeEnemy(final ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy)
    {
        placeEnemyYPos(enemy);

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