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

public class LightRecharger implements LightRechargerFeature{

    private Recharger item = null;

    private HeroInfo heroInfo;

    private Light heroLight;

    private Light itemLight = null;

    private TreeMap<Double, TreeMap<Double,Platform>> allPlatforms;

    private double timePast = 0;

    private double distanceVelInc;

    private double cameraHeight;

    private double lightRadious;
    //private double minDiameter;

    public LightRecharger(HeroInfo hero, TreeMap<Double, TreeMap<Double,Platform>> allPlatforms, Light light, Vector2D cameraDim){
        this.cameraHeight = cameraDim.y;
        this.heroInfo = hero;
        this.heroLight = light;
        this.allPlatforms = allPlatforms;
        //this.minDiameter = Math.sqrt(cameraDim.x*cameraDim.x + cameraDim.y*cameraDim.y)/2f;
        this.distanceVelInc = hero.getYDim()/20f;//per s

        lightRadious = heroInfo.getYDim()*5;
        generateItem();
    }

    private boolean checkFound(){
        return item.checkCollision((Entity) heroInfo);
    }

    public void updateRechargerItem(float deltaT){
        timePast+=deltaT;
        if(checkFound()){
            heroLight.resetRadious();
            generateItem();
        }
        updateLight();
        itemLight.updateOscilation(deltaT);
    }

    public Recharger getItemInfo(){
        return item;
    }

    public Light getItemLight(){
        return itemLight;
    }

    private void generateItem(){
        Random randomPercentage = new Random();
        Platform plat = getPlatformForItem();

        Vector2D posItem = new Vector2D();

        posItem.x = plat.getXPos() + randomPercentage.nextFloat()*plat.getXDim();
        posItem.y = plat.getYPos() + plat.getYDim();

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

    private Platform getPlatformForItem(){
        Platform minPlatform = null;
        for(Map.Entry<Double,TreeMap<Double,Platform>> platformsY: allPlatforms.entrySet()){
            for(Map.Entry<Double,Platform> platformsX: platformsY.getValue().entrySet()){
                if((minPlatform == null || dist(platformsX.getValue()) < dist(minPlatform)) && (dist(platformsX.getValue())>calculateMinDistance())){
                    minPlatform = platformsX.getValue();
                }
            }
        }
        return minPlatform;
    }


    /*
    private void generateItem(){

        final double diameterStep = heroInfo.getYDim();
        final double maxDiameterSearch = getMaxCircunference();

        for(double i = minDiameter; i < maxDiameterSearch; i+=diameterStep){

            double searchDiameter = i + diameterStep;
            final double maxY = heroInfo.getYPos() + searchDiameter;
            final double minY = heroInfo.getYPos() - searchDiameter;

            Double topYKey = allPlatforms.floorKey(maxY);
            Double bottomYKey = allPlatforms.ceilingKey(minY);

            if(topYKey == null || topYKey<minY )
                continue;

            Platform platformToContainItem = null;
            for(Map.Entry<Double,TreeMap<Double,Platform>> xTree: allPlatforms.subMap(bottomYKey,true,topYKey,true).entrySet()){

                double rightX = calculateXfromY(xTree.getKey(), searchDiameter);
                double leftX = calculateXfromY(-xTree.getKey(), searchDiameter);

                Double leftXKey = xTree.getValue().ceilingKey(leftX);
                Double rightXKey = xTree.getValue().floorKey(rightX );

                double centerRightX = 0;
                double centerLeftX;

                boolean skip = false;
                if(xTree.getKey()>(heroInfo.getYPos()+i) || xTree.getKey()<(heroInfo.getYPos()-i)){
                    centerLeftX = rightXKey;
                    skip = true;
                }else{
                    centerRightX = calculateXfromY(xTree.getKey(), searchDiameter - diameterStep);
                    centerLeftX = calculateXfromY(-xTree.getKey(), searchDiameter - diameterStep);
                }

                if(leftXKey<centerLeftX){
                    Double rightleftXKey = xTree.getValue().floorKey(centerLeftX );

                    for(Map.Entry<Double,Platform> platforms : xTree.getValue().subMap(leftXKey,true,rightleftXKey,true).entrySet()){
                        if(platformToContainItem==null || (dist(platformToContainItem)>dist(platforms.getValue()))){
                            platformToContainItem = platforms.getValue();
                        }
                    }
                }
                if(!skip && rightXKey>centerRightX){
                    Double leftrightXKey = xTree.getValue().ceilingKey(centerRightX );

                    for(Map.Entry<Double,Platform> platforms : xTree.getValue().subMap(leftrightXKey,true,rightXKey,true).entrySet()){
                        if(platformToContainItem==null || (dist(platformToContainItem)>dist(platforms.getValue()))){
                            platformToContainItem = platforms.getValue();
                        }
                    }
                }

                if(platformToContainItem!=null)
                    break;
            }
        }
    }


    private double getMaxCircunference(){

    }

    private double calculateXfromY(double y, double diam){
        y = y- heroInfo.getYPos();
        double x = Math.sqrt(diam*diam - y*y);
        if(y>0)
            return heroInfo.getXPos()+x;
        else
            return heroInfo.getXPos()-x;
    }
*/

    private double dist(Platform platform){
        return Math.sqrt((heroInfo.getXPos()-platform.getXPos())*(heroInfo.getXPos()-platform.getXPos()) +
                (heroInfo.getYPos()-platform.getYPos())*(heroInfo.getYPos()-platform.getYPos()));
    }


    /**
     * Function that calculates the distance to the item based on the time passed playing the game.
     * Linear.
     * @return Minimum distance to item.
     */
    private double calculateMinDistance(){
        return timePast*distanceVelInc + heroInfo.getYDim()*3;
    }

}
