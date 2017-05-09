package com.mygdx.game.gameLogic.LogicWorlds;

import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInput;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.ArrayList;
import java.util.Random;

import static com.mygdx.game.gameLogic.Characters.Platform.fractionOfScreenHeightForPlatform;

public class PlatWorld extends GameWorld {
    protected static final double ENEMY_GENERATION_YMULT = 1.0;

    protected ArrayList<Platform> platforms;
    protected double yPlatform;



    protected double cameraWidth;
    protected double cameraHeight;

    protected double posXExplored;
    protected double posYExplored;
    protected double negXExplored;
    protected double negYExplored;


    public PlatWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero, stageDirector);
        yPlatform = 0.0;

        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        platforms = new ArrayList<Platform>();

        posXExplored = hero.getXPos() + this.cameraWidth/2;
        posYExplored = hero.getYPos() + this.cameraHeight;
        negXExplored = hero.getXPos() - this.cameraWidth/2;
        negYExplored = hero.getYPos();

        //TODO remove
        if (Constants.INPUT_DEBUG)
            createDummyEnemies();


        double platformHeight = fractionOfScreenHeightForPlatform*this.cameraHeight;
        double platformWidth =  platformHeight*Constants.getEnemyConstants(Platform.class).aspectRatio;

        createPlarforms();
    }

    void createPlatformHere(double xPos,double yPos, double xWidth, double yHeight){
        platforms.add(new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
    }

    void surrondMatrix(int platformAuxiliarMatrix[][], int i, int e, Vector2D spacing, Vector2D matrixSize){
        double startingI;
        double startingE;
        if((startingI = i - spacing.y) < 0){
            startingI = 0;
        }
        if((startingE = e - spacing.x) < 0){
            startingE = 0;
        }

        double finalI;
        double finalE;
        if((finalI = i + spacing.y)>=matrixSize.y){
            finalI = matrixSize.y-1;
        }
        if((finalE = e + spacing.x)>=matrixSize.x){
            finalE = matrixSize.x-1;
        }

        for(int y = (int)startingI ; y<=finalI; y++){
            for(int x = (int)startingE ; x<=finalE; x++){
                platformAuxiliarMatrix[y][x] = 1;
            }
        }
    }

    void createPlarforms(){



        double platformHeight = fractionOfScreenHeightForPlatform*this.cameraHeight;
        double platformWidth =  platformHeight*Constants.getEnemyConstants(Platform.class).aspectRatio;

        int xSize = (int)(worldDimensions.x / platformWidth);
        int ySize = (int)(worldDimensions.y / platformHeight);
        int platformAuxiliarMatrix[][];


        Vector2D spacingBetweenPlatforms = new Vector2D(1,(int)hero.getYDim()*2/platformHeight);
        int initFreqPlatforms = 10;

        platformAuxiliarMatrix = new int[ySize][xSize];


        Random randomizer = new Random();
        int chance = 1;
        for(int i = (int)(hero.getYDim()/platformHeight); i < ySize; i++){
            for(int e = 0; e < xSize; e++){
                if(platformAuxiliarMatrix[i][e] == 0){
                    if(randomizer.nextInt(initFreqPlatforms) < chance++){
                          createPlatformHere(e*platformWidth,i*platformHeight,platformWidth,platformHeight);
                          surrondMatrix(platformAuxiliarMatrix, i, e, spacingBetweenPlatforms, new Vector2D(xSize,ySize));
                          chance = 1;
                    }
                }else
                    chance = 1;
            }
        }
    }


    protected void createDummyEnemies () //testing function
    {
        final double heroX = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double heroYDim = hero.getYDim();

        final Constants.CharacterConstants characterConstants = Constants.getEnemyConstants(EnemyGround.class);

        final double enYDim = characterConstants.dimYMult * heroYDim;
        final double enXDim = enYDim * characterConstants.aspectRatio;

        Vector2D dims = new Vector2D(enXDim, enYDim);

        Enemy enemy1 = new EnemyGround(new Vector2D(heroX + 2, 0), dims, new Vector2D(0,0));
        enemies.add(enemy1);
        Enemy enemy2 = new EnemyGround(new Vector2D(heroX - 5, 0), dims, new Vector2D(0,0));
        enemies.add(enemy2);

//        Enemy enemy3 = new EnemyGround(new Vector2D(heroX - 8, 0), dims, new Vector2D(heroYDim * characterConstants.speedMult,0));
//        enemies.add(enemy3);
    }

    //// abstract implementations ---------------
    @Override
    public void update (float deltaT)
    {
        if (! isGamePlayable())
            return;

        final int numDestroyedEnemies = updateEnemies(deltaT);

        StatisticsInput statisticsInput = stageDirector.getStatsticsInput();
        statisticsInput.updateNumberOfGroundEnemies(- numDestroyedEnemies);
        statisticsInput.update(deltaT);

        //updateExploredXY();

        checkPlatformCollisions();

        updateHero(deltaT);

        if(this.checkHeroCollisions() > 0)
        {
            gamePlayable = false;

            //TODO remove
            System.out.println("Game Lost");
        }

        if (! Constants.INPUT_DEBUG)
            tryGenerateEnemy();
    }
/*
    public void tryCreatePlatform(double diffPosX,double diffPosY,double diffNegX, double diffNegY){

        Platform platform;
        if(null != (platform = Platform.tryCreate(
                diffPosX
                ,diffPosY
                ,diffNegX
                ,diffNegY
                ,hero.getXPos() + hero.getXDim()/2
                ,hero.getYPos()
                ,new Vector2D(cameraWidth, cameraHeight)
        ))){
            platforms.add(platform);
        }
    }
*/
    public ArrayList<Platform> getPlatforms(){
        return platforms;
    }
/*
    private void updateExploredXY(){

        System.out.println(posXExplored + " " + posYExplored + " " + negXExplored + " " + negYExplored);

        double prevPosXExplored = posXExplored;
        double prevPosYExplored = posYExplored;
        double prevNegXExplored = negXExplored;
        double prevNegYExplored = negYExplored;

        if((hero.getXPos() + this.cameraWidth/2)>posXExplored){
            posXExplored = hero.getXPos() + this.cameraWidth/2;
        }

        if((hero.getYPos() + this.cameraHeight)>posYExplored){
            posYExplored = hero.getYPos() + this.cameraHeight;
        }

        if((hero.getXPos() - this.cameraWidth/2)<negXExplored){
            negXExplored = hero.getXPos() + this.cameraWidth/2;
        }

        if(hero.getYPos()<negYExplored){
            negYExplored = hero.getYPos();
        }

        this.tryCreatePlatform(prevPosXExplored - posXExplored
                ,prevPosYExplored - posYExplored
                ,prevNegXExplored - negXExplored
                ,prevNegYExplored - negYExplored
                );

    }
*/
    public void checkPlatformCollisions(){
        for(Platform plat: platforms){
            if(plat.checkCollision(hero)){
                yPlatform = plat.getTopY();
                return;
            }
            if(plat.isCharacterOnThisPlatform(hero)){
                return;
            }
        }
        if(!hero.isJumping()&& (yPlatform!=0.0)) {
            System.out.println("asdasd");
            hero.fall(1.0);
        }
        yPlatform = 0.0;
    }

    @Override
    protected void checkHeroJump()
    {
        if (hero.isJumping() && hero.getYPos() < yPlatform)
        {
            hero.stopJump();
            hero.setYPos(yPlatform);
        }
    }

    @Override
    protected void placeEnemy(Enemy enemy) {
        enemy.setYPos(0.0);

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

    //// hero inputs -------
    public void moveHeroHorizontal(final double heroXMovement)
    {
        hero.setXMovement(heroXMovement);
    }

    @Override
    public void heroJump(final double gravityStrength) {
        hero.jump(gravityStrength);
    }
}
