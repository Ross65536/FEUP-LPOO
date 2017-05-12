package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Vector2D;

public class Lights implements LightsFeature{

    private Light light;
    private Hero hero;

    public Lights(Hero hero){
        this.hero = hero;
        this.light = new Light(new Vector2D(hero.getXPos(),hero.getYPos()),hero.getYDim()*8,true,0.05);;
    }

    public void updateLight(float deltaT){
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

    public float getRadiousPercentage(){
        return light.getRadiousPercentage();
    }
}
