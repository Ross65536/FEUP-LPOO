package com.mygdx.game.gameLogic.GameDirector.DifficultyCurve;

import com.mygdx.game.gameLogic.GameDirector.StatisticsInfo;

public class BalancedCurve extends Curves {
    private double enemyGenerationPeriod;
    private int maxNumEnemies;

    public BalancedCurve(double enemyCreateDeltaTCutoff, int maxNumEnemies)
    {
        this.enemyGenerationPeriod = enemyCreateDeltaTCutoff;
        this.maxNumEnemies = maxNumEnemies;
    }

    @Override
    public double generateDifficulty(StatisticsInfo statistics) {

        final boolean bEnemeyNotTooSoon = statistics.getLastCreatedEnemyDeltaT() > enemyGenerationPeriod;
        final boolean bNotTooManyEnemies = statistics.getNumberOfEnemies() < maxNumEnemies;

        if (bEnemeyNotTooSoon && bNotTooManyEnemies) // create enemy
        {
            return random.nextDouble() * CURVES_MAX_DIFFICULTY;
        }
        else
            return CURVES_NO_ENEMY_CREATED;
    }
}
