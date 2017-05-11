package com.mygdx.game.gameLogic.LogicWorlds;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Renderable;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInput;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import static com.mygdx.game.gameLogic.Characters.Platform.fractionOfScreenHeightForPlatform;

public class PlatWorld extends GameWorld {

    protected static final double ENEMY_GENERATION_YMULT = 1.0;


    protected double cameraWidth;
    protected double cameraHeight;

    protected double posXExplored;
    protected double posYExplored;
    protected double negXExplored;
    protected double negYExplored;

    protected Light light;

    protected TreeMap<Double, TreeMap<Double,Platform>> platformsT;
    protected ArrayList<Platform> platformsInRange;
    protected Platform currentPlarform; //platform where hero is on


    public PlatWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero, stageDirector);
        currentPlarform = null;

        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        platformsT = new TreeMap<Double, TreeMap<Double,Platform>>();

        posXExplored = hero.getXPos() + this.cameraWidth/2;
        posYExplored = hero.getYPos() + this.cameraHeight;
        negXExplored = hero.getXPos() - this.cameraWidth/2;
        negYExplored = hero.getYPos();

        light = new Light(new Vector2D(hero.getXPos(),hero.getYPos()),hero.getYDim()*8,true,0.05);

        //TODO remove
        if (Constants.INPUT_DEBUG)
            createDummyEnemies();

        createPlarforms();
    }

    void createPlatformHere(double xPos,double yPos, double xWidth, double yHeight){
        //platforms.add(new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
        if(platformsT.containsKey(yPos)){
            platformsT.get(yPos).put(new Double(xPos),new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
            return;
        }
        TreeMap<Double,Platform> xTree = new TreeMap<Double,Platform>();
        xTree.put(new Double(xPos),new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
        platformsT.put(new Double(yPos),xTree);

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
        int platformAuxiliarMatrix[][] = new int[ySize][xSize];


        Vector2D spacingBetweenPlatforms = new Vector2D(0,(int)hero.getYDim()*2/platformHeight);
        int initFreqPlatforms = 10;

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
        platformsInRange = getPlatformsInRange();
        checkPlatformCollisions();

        updateHero(deltaT);
        updateLight(deltaT);

        if(this.checkHeroCollisions() > 0)
        {
            gamePlayable = false;

            //TODO remove
            System.out.println("Game Lost");
        }

        if (! Constants.INPUT_DEBUG)
            tryGenerateEnemy();
    }

    private void updateLight(float deltaT){
        light.setXPos(hero.getXPos() + (hero.getXDim()/2.0)-(light.getRadious()/2.0));
        light.setYPos(hero.getYPos()-(light.getRadious()/2.0) + hero.getYDim()/2.0);
        if(light.isOscilating()){
            light.updateOscilation(deltaT);
        }
        if(hero.isMoving())
            deltaT*=-1;
        light.updateRadious(deltaT);

    }

    public Light getLightInfo(){
        return light;
    }

    public ArrayList<Platform> getPlatforms(){
        return platformsInRange;
    }
    ArrayList<Platform> getPlatformsInRange(){

        ArrayList<Platform> res = new ArrayList<Platform>();

        double platformHeight = fractionOfScreenHeightForPlatform*this.cameraHeight;
        double platformWidth =  platformHeight*Constants.getEnemyConstants(Platform.class).aspectRatio;
        double topRightCornerX = hero.getXPos()+(cameraWidth/2)+platformWidth;
        double topRightCornerY = hero.getYPos()+(cameraHeight/2)+platformHeight*2;
        double bottomLeftCornerX = hero.getXPos()-(cameraWidth/2)-platformWidth;
        double bottomLeftCornerY = hero.getYPos()-(cameraHeight/2)-platformHeight*2;

        Double topYKey = platformsT.floorKey(topRightCornerY);
        Double bottomYKey = platformsT.ceilingKey(bottomLeftCornerY);

        if(topYKey!=null && bottomYKey!=null && topYKey>=bottomYKey)
            for(Map.Entry<Double,TreeMap<Double,Platform>> xTree: platformsT.subMap(bottomYKey,true,topYKey,true).entrySet()){
                Double leftXKey = xTree.getValue().ceilingKey(bottomLeftCornerX);
                Double rightXKey = xTree.getValue().floorKey(topRightCornerX );
                if(rightXKey!=null && leftXKey!=null && rightXKey>=leftXKey)
                    for(Map.Entry<Double,Platform> platforms : xTree.getValue().subMap(leftXKey,true,rightXKey,true).entrySet()){
                        res.add(platforms.getValue());
                    }
            }
        return res;
    }

    public void checkPlatformCollisions(){
        if(currentPlarform!= null && currentPlarform.isCharacterOnThisPlatform(hero)){
            return;
        }
        for(Platform plat: platformsInRange){
            if(plat.checkCollision(hero)){
                currentPlarform = plat;
                return;
            }

        }
        //left platform
        if(!hero.isFlying()&& (currentPlarform!=null)) {
            hero.fall(1.0);
        }
        currentPlarform = null;
    }

    @Override
    protected void checkHeroJump()
    {
        double yValue;
        if(currentPlarform==null)
            yValue = 0;
        else
            yValue = currentPlarform.getTopY();

        if (hero.isJumping() && hero.getYPos() < yValue)
        {
            hero.stopJump();
            hero.setYPos(yValue);
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
