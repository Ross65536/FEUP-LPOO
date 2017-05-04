package com.mygdx.game.gameLogic.GameDirector;

import java.util.Random;

public class DifficultyCurves {
    public static final double NO_ENEMY_CREATED = -1.0;
    /**
     * Function object wrapper
     */
    public interface generator {
        /**
         * Receives a Statistics object from which it queries the "stress" level of the game.
         * @param statistics queried object
         * @return NO_ENEMY_CREATED if no enemy is to be created or a
         * number form 0.0 to 100.0 to indicate the difficulty of the enemy to be created
         */
        double op(final StatisticsInfo statistics);
    }

    static private Random random = new Random();
    static private final int CREATE_ENEMY_ODDS = 120;
    /**
     * Random difficulty generator.
     * @param statistics not used
     * @return
     */
    final public static double randomGenerator(final StatisticsInfo statistics)
    {
        if (random.nextInt(CREATE_ENEMY_ODDS) == 0)
            return random.nextDouble() * 100;
        else
            return NO_ENEMY_CREATED;
    }
}
