package com.mygdx.game.gameLogic.GameDirector.DifficultyCurves;


import com.mygdx.game.gameLogic.GameDirector.Statistic.StatisticsInfo;

/**
 * Generates difficulty randomly
 */
public class RandomCurve extends Curve
{
    static private final int CREATE_ENEMY_ODDS_RANDOM = 120;

    /**
     * Random difficulty generator. Average should be half difficulty
     * @param statistics not used
     * @return difficulty generated or a special value indicated no enemy is to be generated
     */
    @Override
    public double generateDifficulty(StatisticsInfo statistics) {
        {
            if (random.nextInt(CREATE_ENEMY_ODDS_RANDOM) == 0)
                return random.nextDouble() * CURVES_MAX_DIFFICULTY;
            else
                return CURVES_NO_ENEMY_CREATED;
        }
    }
}
