package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

public interface HeroLifesFeature {
    /**
     * Returns the number of lifes left.
     * @return the number of lifes left.
     */
    int getNumberOfLifes();

    /**
     * Returns whether or not the hero is immune to damage.
     * @return whether or not the hero is immune to damage.
     */
    boolean isImmune();
    int takeLife();
}
