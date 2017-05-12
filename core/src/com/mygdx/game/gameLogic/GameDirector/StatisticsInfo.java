package com.mygdx.game.gameLogic.GameDirector;

public interface StatisticsInfo
{
    int getNumGroundEnemies();
    int getNumberOfEnemies();
    double getCurrentPlayTime();
    double getLastCreatedEnemyDeltaT();

    /**
     * 0.0 represents no stress, 1.0 is max stress, 0.5 is middle stress (also determined by polling frequency)
     * @return from 0.0 to 1.0
     */
    double getJumpStress();
    double getMovStress();
    double getLightLevel();
}
