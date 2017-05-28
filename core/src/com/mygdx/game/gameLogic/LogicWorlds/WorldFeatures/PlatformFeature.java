package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Platform;

import java.util.List;

/**
 * Interface for the world platforms feature.
 */
public interface PlatformFeature {
    /**
     * Returns all the platforms seen of screen.
     * @return
     */
    List<Platform> getPlatforms();
    /**
     * Determins the platforms seen on screen.
     */
    void updatePlatformsInRange();

    /**
     * Checks the colisions with the platforms.
     */
    void checkPlatformCollisions();

    /**
     * Returns the minimum Y position of the hero, until the hero falls of the platform or jumps.
     * @return
     */
    double getLandingYValue();
}
