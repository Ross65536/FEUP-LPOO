package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Entity;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.Characters.Recharger;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

/**
 * Class that implements the light recharger item feature that can be used in all gameWorlds.
 */
public class LightRecharger implements LightRechargerFeature{

    private Recharger item = null;

    protected HeroInfo heroInfo;

    protected Light heroLight;

    private Light itemLight = null;

    protected TreeMap<Double, TreeMap<Double,Platform>> allPlatforms;

    protected double cameraHeight;

    private double lightRadious;
    //private double minDiameter;

    private boolean newRechargerCaught = false;
    private int rechargersCaught = 0;
    private int distanteLevel = 0;

    protected Platform platWhereRechargerIs;

    /**
     * Constructor.
     * @param hero the hero
     * @param allPlatforms all platforms
     * @param heroLight the hero's light
     * @param cameraDim the camera dimensions
     */
    public LightRecharger(HeroInfo hero, TreeMap<Double, TreeMap<Double,Platform>> allPlatforms, Light heroLight, Vector2D cameraDim){
        this.cameraHeight = cameraDim.y;
        this.heroInfo = hero;
        this.heroLight = heroLight;
        this.allPlatforms = allPlatforms;
        //this.minDiameter = Math.sqrt(cameraDim.x*cameraDim.x + cameraDim.y*cameraDim.y)/2f;

        lightRadious = heroInfo.getYDim()*5;
        generateItem();
    }

    /**
     * Empty constructor.
     */
    protected LightRecharger(){
    }

    /**
     * Checks if the generated item was found.
     * @return
     */
    private boolean checkFound(){
        return item.checkCollision((Entity) heroInfo);
    }

    /**
     * Updates the lights properies and checks if it was found.
     * @param deltaT
     */
    public void updateRechargerItem(float deltaT){
        if(checkFound()){
            rechargersCaught++;
            increaseLevel();
            heroLight.resetRadious();
            generateItem();
            newRechargerCaught = true;
        }
        updateLight();
        itemLight.updateOscilation(deltaT);
    }

    /**
     * Increase the distance level.
     */
    public void increaseLevel(){
        distanteLevel++;
    }

    /**
     * Returns wheter or not the item was found.
     * @return wheter or not the item was found.
     */
    public boolean wasRechargerCaught(){
        if(newRechargerCaught == true)
        {
            newRechargerCaught = false;
            return true;
        }
        return false;
    }

    /**
     * Returns the current level of distance between the player and the recharger.
     * @return the current level of distance between the player and the recharger.
     */
    public int getLevel(){
        return distanteLevel;
    }

    /**
     * Returns the number of recharges caught.
     * @return
     */
    public int totalRechargersCaught(){
        return rechargersCaught;
    }

    /**
     * Returns information regarding the recharger item.
     * @return
     */
    public Recharger getItemInfo(){
        return item;
    }

    /**
     * Returns information regarding the recharger item's light.
     * @return
     */
    public Light getItemLight(){
        return itemLight;
    }

    /**
     * Generates a new recharger.
     */
    protected void generateItem(){
        Random randomPercentage = new Random();

        platWhereRechargerIs = null;
        while(platWhereRechargerIs==null){
            platWhereRechargerIs = getPlatformForItem();
            if(platWhereRechargerIs == null)
                distanteLevel--;
        }

        Vector2D posItem = new Vector2D();

        posItem.x = platWhereRechargerIs.getXPos() + randomPercentage.nextFloat()*platWhereRechargerIs.getXDim();
        posItem.y = platWhereRechargerIs.getYPos() + platWhereRechargerIs.getYDim();

        double itemSize = Recharger.fractionOfScreenHeightForPlatform*this.cameraHeight;

        if(item == null) {

            item = new Recharger(posItem, new Vector2D(itemSize, itemSize));
        }
        else {
            item.setXPos(posItem.x);
            item.setYPos(posItem.y);
        }

        updateLight();

    }

    /**
     * Updates the recharger's light.
     */
    private void updateLight(){

        double lightRadious;

        if(itemLight !=null){
            lightRadious= itemLight.getRadious();
        }else
            lightRadious = this.lightRadious;

        Vector2D posLight = new Vector2D();

        posLight.x = item.getXPos() + (item.getXDim()/2.0)-(lightRadious/2.0);
        posLight.y = item.getYPos() + (item.getYDim()/2.0) - (lightRadious/2.0);

        if(itemLight == null) {
            itemLight = new Light(posLight,lightRadious ,true,0.5);
        }
        else {
            itemLight.setXPos(posLight.x);
            itemLight.setYPos(posLight.y);
        }
    }

    /**
     * Returns the platform where the next item should spawn based of distances and current dificulty.
     * @return
     */
    private Platform getPlatformForItem(){
        Platform minPlatform = null;
        double minDistance = calculateMinDistance();
        double minDistancePlat = 0;
        for(Map.Entry<Double,TreeMap<Double,Platform>> platformsY: allPlatforms.entrySet()){
            for(Map.Entry<Double,Platform> platformsX: platformsY.getValue().entrySet()){
                if((minPlatform == null || dist(platformsX.getValue()) < minDistancePlat) && (dist(platformsX.getValue())>minDistance)){
                    minPlatform = platformsX.getValue();
                    minDistancePlat = dist(minPlatform);
                }
            }
        }
        return minPlatform;
    }


    protected double dist(Platform platform){
        return Math.sqrt((heroInfo.getXPos()-platform.getXPos())*(heroInfo.getXPos()-platform.getXPos()) +
                (heroInfo.getYPos()-platform.getYPos())*(heroInfo.getYPos()-platform.getYPos()));
    }


    /**
     * Function that calculates the distance to the item based on the time passed playing the game.
     * Linear.
     * @return Minimum distance to item.
     */
    protected double calculateMinDistance(){
        return (heroInfo.getYDim()*distanteLevel)+heroInfo.getYDim()*3;
    }

}
