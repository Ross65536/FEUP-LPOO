package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Platform;

import java.util.List;

public interface PlatformFeature {
    List<Platform> getPlatforms();
    void updatePlatformsInRange();
    void checkPlatformCollisions();
    double getLandingYValue();
}
