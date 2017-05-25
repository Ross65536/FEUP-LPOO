package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Light;

public interface HeroLightFeature {
    /**
     * Updates the hero's light with delta time.
     * @param deltaT delta time
     */
    void updateLight(float deltaT);

    /**
     * Returns the ligth's information.
     * @return
     */
    Light getLightInfo();

    /**
     * Returns the percentage of the current radios form the initial radious of the light.
     * @return percentage
     */
    float getRadiousPercentage();
}
