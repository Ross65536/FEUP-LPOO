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

/**
 * Class that implements the logic behind platforms.
 * Used as a component the the gameWorlds.
 */
public class Platforms implements PlatformFeature{

    //platforms properties
    private int freqPlatforms;
    private Vector2D spacingBetweenPlatforms;

    private TreeMap<Double, TreeMap<Double,Platform>> platformsT;
    private ArrayList<Platform> platformsInRange;
    private Platform currentPlarform; //platform where hero is on

    protected Hero hero;

    protected Vector2D worldDimensions;

    protected double cameraHeight;
    protected double cameraWidth;

    private double platformHeight;
    private double platformWidth;

    /**
     * Constructor.
     * @param hero The hero.
     * @param worldDimensions The world dimensions.
     * @param cameraSizes The camera dimensions
     */
    public Platforms(Hero hero,Vector2D worldDimensions,Vector2D cameraSizes){


        this.hero = hero;
        this.worldDimensions = worldDimensions;
        this.cameraHeight = cameraSizes.y;
        this.cameraWidth = cameraSizes.x;

        initConstants();
        createPlarforms();
        updatePlatformsInRange();
    }

    /**
     * Sets the padding of the platforms and the frequency at which they show up.
     * @param padding The x and y padding
     * @param freq The frequency at which the platforms show up.
     */
    protected void setPadingANDFrequecy(Vector2D padding, int freq){

        spacingBetweenPlatforms = padding;
        freqPlatforms = freq;
    }

    /**
     * Initializes constants.
     */
    protected void initConstants(){
        currentPlarform = null;
        platformsT = new TreeMap<Double, TreeMap<Double,Platform>>();

        platformHeight = fractionOfScreenHeightForPlatform*this.cameraHeight;
        platformWidth =  platformHeight* CommonConsts.getCharacterConstants(Platform.class).aspectRatio;

        setPadingANDFrequecy(new Vector2D(0,(int)hero.getYDim()*1.5/platformHeight),5);
    }

    /**
     * Empty constructor.
     */
    public Platforms(){
    }

    /**
     * Returns all the platforms that can be seen on screen.
     * @return platforms that can be seen on screen.
     */
    public List<Platform> getPlatforms(){
        List<Platform> unmList = Collections.unmodifiableList(platformsInRange);
        return unmList;
    }

    /**
     * Returns all platforms.
     * @return all platforms.
     */
    public TreeMap<Double, TreeMap<Double,Platform>> getAllPlatforms(){
        return platformsT;
    }

    /**
     * Calculates the plarforms that can be seen on screen.
     */
    public void updatePlatformsInRange(){
        platformsInRange = getPlatformsInRange();
    }

    /**
     * Returns the minimum Y position of the hero, until the hero falls of the platform or jumps.
     * @return
     */
    public double getLandingYValue() {
        if (currentPlarform == null)
            return 0;
        else
            return currentPlarform.getTopY();
    }

    /**
     * Checks for collisions on any of the platforms in range.
     */
    public void checkPlatformCollisions(){
        updatePlatformsInRange();
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

    /**
     * Checks if the hero is droping of a platform.
     */
    private void checkDropFromPlatform(){
        if(!hero.isMovingY()&& (currentPlarform!=null)) {
            hero.fall(1.0);
        }
        currentPlarform = null;
    }

    /**
     * Creates a platform.
     * @param xPos X Position where the platform will be created.
     * @param yPos Y Position where the platform will be created.
     * @param xWidth width of the platform to be created.
     * @param yHeight height of the platform to be created.
     */
    private void createPlatformHere(double xPos,double yPos, double xWidth, double yHeight){
        if(platformsT.containsKey(yPos)){
            platformsT.get(yPos).put(new Double(xPos),new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
            return;
        }
        TreeMap<Double,Platform> xTree = new TreeMap<Double,Platform>();
        xTree.put(new Double(xPos),new Platform(new Vector2D(xPos, yPos),new Vector2D(xWidth,yHeight)));
        platformsT.put(new Double(yPos),xTree);

    }

    /**
     * Creates world all platforms.
     */
    protected void createPlarforms(){

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

    /**
     * Calculates all platforms in range.
     * @return all platforms in range.
     */
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

    /**
     * Assures spacing between platforms based of the padding set in the spacingBetweenPlatforms field of this class,
     * and assures the frequency at which the platforms show up based on the set freqPlatforms field of this class.
     * @param platformAuxiliarMatrix Auxiliar matrix to know where the platforms are and where there can or can't be a platform.
     * @param i x position of platform to pad
     * @param e y position of platform to pad
     * @param spacing the spacing
     * @param matrixSize the matrix size
     */
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
