package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.Vector2D;

/**
 * Implements the logic behind the heros light. Used has a feature or component on the gamesLogic worlds
 */
public class HeroLight implements HeroLightFeature {

    private Light light;
    private Hero hero;

    /**
     * Construcor
     * @param hero needed info of the hero
     */
    public HeroLight(Hero hero){
        this.hero = hero;
        double lightRadious = hero.getYDim()*8;
        this.light = new Light(new Vector2D(
                            hero.getXPos() + (hero.getXDim()/2.0)-(lightRadious/2.0),
                            hero.getYPos() - (lightRadious/2.0) + hero.getYDim()/2.0),
                            lightRadious,
                            true,
                            0.05
        );
    }

    /**
     * Updates the light with delta time.
     * @param deltaT delta time
     */
    public void updateLight(float deltaT){
        light.setXPos(hero.getXPos() + (hero.getXDim()/2.0)-(light.getRadious()/2.0));
        light.setYPos(hero.getYPos()-(light.getRadious()/2.0) + hero.getYDim()/2.0);

        light.updateRadious(deltaT);

        if(light.isOscilating()){
            light.updateOscilation(Math.abs(deltaT));
        }


    }

    /**
     * returns information regarding the light.
     * @return
     */
    public Light getLightInfo(){
        return light;
    }

    /**
     * Returns the percentage of light left.
     * @return the percentage of light left.
     */
    public float getRadiousPercentage(){
        return light.getRadiousPercentage();
    }
}
