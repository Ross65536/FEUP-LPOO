package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.Vector2D;

public class HeroLight implements HeroLightFeature {

    private Light light;
    private Hero hero;

    public HeroLight(Hero hero){
        this.hero = hero;
        double lightRadious = hero.getYDim()*8;
        this.light = new Light(new Vector2D(hero.getXPos() + (hero.getXDim()/2.0)-(lightRadious/2.0),
                            hero.getYPos()-(lightRadious/2.0) + hero.getYDim()/2.0),
                            lightRadious,
                            true,
                            0.05
        );
    }

    public void updateLight(float deltaT){
        light.setXPos(hero.getXPos() + (hero.getXDim()/2.0)-(light.getRadious()/2.0));
        light.setYPos(hero.getYPos()-(light.getRadious()/2.0) + hero.getYDim()/2.0);
        if(light.isOscilating()){
            light.updateOscilation(Math.abs(deltaT));
        }

        light.updateRadious(deltaT);

    }

    public Light getLightInfo(){
        return light;
    }

    public float getRadiousPercentage(){
        return light.getRadiousPercentage();
    }
}
