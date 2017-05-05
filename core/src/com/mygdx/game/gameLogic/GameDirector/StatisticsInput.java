package com.mygdx.game.gameLogic.GameDirector;

public interface StatisticsInput {
    /**
     * numberOfGroundEnemies += deltaEnemies
     * @param deltaEnemies
     */
    void updateNumberOfGroundEnemies (final int deltaEnemies);
    void update(final float deltaT);

}
