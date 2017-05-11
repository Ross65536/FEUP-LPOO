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
import com.mygdx.game.gameLogic.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static com.mygdx.game.gameLogic.Characters.Platform.fractionOfScreenHeightForPlatform;

public class PlatWorld extends GameWorld implements DummyEnemyFeature {


    protected double cameraWidth;
    protected double cameraHeight;

    protected Light light;

    DummyEnemies dummyEnemies;

    protected TreeMap<Double, TreeMap<Double,Platform>> platformsT;
    protected ArrayList<Platform> platformsInRange;
    protected Platform currentPlarform; //platform where hero is on


    public PlatWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        super(worldDims, hero);
        currentPlarform = null;

        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        platformsT = new TreeMap<Double, TreeMap<Double,Platform>>();

        dummyEnemies = new DummyEnemies(hero,worldDims,stageDirector);

        light = new Light(new Vector2D(hero.getXPos(),hero.getYPos()),hero.getYDim()*8,true,0.05);

        //TODO remove
        if (Constants.INPUT_DEBUG)
            createDummyEnemies();

        createPlarforms();
    }

    public float getDangerLevel(){
        return 1-light.getRadiousPercentage();
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


    //// abstract implementations ---------------
    @Override
    public void update (float deltaT)
    {
        if (! isGamePlayable())
            return;

        updateEnemieStatistics(deltaT);

        platformsInRange = getPlatformsInRange();
        checkPlatformCollisions();

        updateHero(deltaT);
        updateLight(deltaT);

        if(this.checkEnemyCollisions() > 0)
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


    //// hero inputs -------
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
