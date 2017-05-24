package com.mygdx.game.gameLogic.GameDirector.Statistic;

/**
 * Provides an interface to acess various statistics
 */
public interface StatisticsInfo
{
    /**
     *
     * @return number of ground enemis
     */
    int getNumGroundEnemies();
    /**
     *
     * @return number of ground + flying enemies
     */
    int getNumberOfEnemies();
    /**
     *
     * @return numebr of flying enemies
     */
    int getNumFlyingEnemies();
    /**
     *
     * @return play time as stored in this object
     */
    double getCurrentPlayTime();
    /**
     *
     * @return time since last enemy was created (pollOrientation enemy bigger than 0)
     */
    double getLastCreatedEnemyDeltaT();

    /**
     * 0.0 represents no stress, 1.0 is max stress, 0.5 is middle stress (also determined by polling frequency)
     * @return from 0.0 to 1.0
     */
    double getStressLevel();

}
