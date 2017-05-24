package com.mygdx.game.gameLogic.GameDirector.DifficultyCurves;

import com.mygdx.game.gameLogic.GameDirector.Statistic.StatisticsInfo;

import java.util.Random;


public abstract class Curve {
    public static final double CURVES_NO_ENEMY_CREATED = -1.0;
    public static final double CURVES_MAX_DIFFICULTY = 100;

    protected static final Random random = new Random(); //used by children

    /**
     * Receives a Statistics object from which it queries the "stress" level of the game.
     * @param statistics queried object
     * @return CURVES_NO_ENEMY_CREATED if no enemy is to be created or a
     * number form 0.0 to 100.0 to indicate the difficulty of the enemy to be created
     */
    abstract public double generateDifficulty(final StatisticsInfo statistics);
}
