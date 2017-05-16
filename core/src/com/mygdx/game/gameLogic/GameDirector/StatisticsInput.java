package com.mygdx.game.gameLogic.GameDirector;

public interface StatisticsInput {
    /**
     * numberOfGroundEnemies += deltaEnemies
     * @param deltaEnemies
     */
    void updateNumberOfGroundEnemies (final int deltaEnemies);
    void updateNumberOfFlyingEnemies (final int deltaEnemies);
    void update(final float deltaT);
    void registerMovement();
    void registerJump();

    void setLightLevel(double radiousPercentage);
}
