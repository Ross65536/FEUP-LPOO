package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static com.mygdx.game.gameLogic.Characters.Platform.fractionOfScreenHeightForPlatform;

public class Platforms implements PlatformFeature{

    //platforms properties
    private static int freqPlatforms;
    private static Vector2D spacingBetweenPlatforms;

    private TreeMap<Double, TreeMap<Double,Platform>> platformsT;
    private ArrayList<Platform> platformsInRange;
    private Platform currentPlarform; //platform where hero is on

    private Hero hero;
    private Vector2D worldDimensions;
    private double cameraHeight;
    private double cameraWidth;

    private double platformHeight;
    private double platformWidth;

    public Platforms(Hero hero,Vector2D worldDimensions,Vector2D cameraSizes){
        currentPlarform = null;
        platformsT = new TreeMap<Double, TreeMap<Double,Platform>>();
        this.hero = hero;
        this.worldDimensions = worldDimensions;
        this.cameraHeight = cameraSizes.y;
        this.cameraWidth = cameraSizes.x;


        platformHeight = fractionOfScreenHeightForPlatform*this.cameraHeight;
        platformWidth =  platformHeight* CommonConsts.getCharacterConstants(Platform.class).aspectRatio;

        spacingBetweenPlatforms = new Vector2D(0,(int)hero.getYDim()*1.5/platformHeight);
        freqPlatforms = 5;

        createPlarforms();
        updatePlatformsInRange();
    }


    public List<Platform> getPlatforms(){
        List<Platform> unmList = Collections.unmodifiableList(platformsInRange);
        return unmList;
    }

    public TreeMap<Double, TreeMap<Double,Platform>> getAllPlatforms(){
        return platformsT;
    }

    public void updatePlatformsInRange(){
        platformsInRange = getPlatformsInRange();
    }

    public double getLandingYValue() {
        if (currentPlarform == null)
            return 0;
        else
            return currentPlarform.getTopY();
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
        checkDropFromPlatform();
    }


    private void checkDropFromPlatform(){
        if(!hero.isMovingY()&& (currentPlarform!=null)) {
            hero.fall(1.0);
        }
        currentPlarform = null;
    }

    private void createPlatformHere(double xPos,double yPos, double xWidth, double yHeight){
        if(platformsT.containsKey(yPos)){
            platformsT.get(yPos).put(new Double(xPos),new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
            return;
        }
        TreeMap<Double,Platform> xTree = new TreeMap<Double,Platform>();
        xTree.put(new Double(xPos),new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
        platformsT.put(new Double(yPos),xTree);

    }

    private void createPlarforms(){

        int xSize = (int)(worldDimensions.x / platformWidth);
        int ySize = (int)(worldDimensions.y / platformHeight);
        int platformAuxiliarMatrix[][] = new int[ySize][xSize];

        Random randomizer = new Random();
        int chance = 1;
        for(int i = (int)(hero.getYDim()/platformHeight); i < ySize; i++){
            for(int e = 0; e < xSize; e++){
                if(platformAuxiliarMatrix[i][e] == 0){
                    if(randomizer.nextInt(freqPlatforms) < chance++){
                        createPlatformHere(e*platformWidth,i*platformHeight,platformWidth,platformHeight);
                        padPlatform(platformAuxiliarMatrix, i, e, spacingBetweenPlatforms, new Vector2D(xSize,ySize));
                        chance = 1;
                    }
                }else
                    chance = 1;
            }
        }
    }


    private ArrayList<Platform> getPlatformsInRange(){

        ArrayList<Platform> res = new ArrayList<Platform>();

        double platformHeight = fractionOfScreenHeightForPlatform * this.cameraHeight;
        double platformWidth = platformHeight * CommonConsts.getCharacterConstants(Platform.class).aspectRatio;
        double topRightCornerX = hero.getXPos() + (cameraWidth / 2) + platformWidth*2;
        double topRightCornerY = hero.getYPos() + (cameraHeight / 2) + platformHeight * 3;
        double bottomLeftCornerX = hero.getXPos() - (cameraWidth / 2) - platformWidth*2;
        double bottomLeftCornerY = hero.getYPos() - (cameraHeight / 2) - platformHeight * 3;

        if(hero.getYPos() < cameraHeight/2f){
            topRightCornerY = cameraHeight + platformHeight * 2;
            bottomLeftCornerY = 0;
        }

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


    private void padPlatform(int platformAuxiliarMatrix[][], int i, int e, Vector2D spacing, Vector2D matrixSize){
        double startingI;
        double startingE;
        if((startingI = i - spacing.y) < 0){ startingI = 0; }
        if((startingE = e - spacing.x) < 0){ startingE = 0; }

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
}
