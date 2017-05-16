package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Light;

public interface HeroLightFeature {
    void updateLight(float deltaT);
    Light getLightInfo();
    float getRadiousPercentage();
}
