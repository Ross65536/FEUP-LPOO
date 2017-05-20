package com.mygdx.game.gameLogic.GameDirector.DifficultyCurve;


import com.mygdx.game.gameLogic.GameDirector.StatisticsInfo;

public class IncreasingDifficulty extends Curves
{
    private double difficultyGenerationRange;
    private double timeHalftime;
    private double enemyGenerationPeriod;
    private int maxNumEnemies;
    public IncreasingDifficulty (double difficultyGenerationRange, double timeRange, double enemyCreateDeltaTCutoff, int maxNumEnemies)
    {
        this.difficultyGenerationRange = difficultyGenerationRange;
        this.timeHalftime = timeRange;
        this.enemyGenerationPeriod = enemyCreateDeltaTCutoff;
        this.maxNumEnemies = maxNumEnemies;
    }

    /**
     * Turns time into a difficulty. At each time point a diffculty can have range difficultyGenerationRange and
     * timeRange indicates at what second half of the max difficulty is generated
     * @param statistics queried object
     * @return
     */
    @Override
    public double generateDifficulty(StatisticsInfo statistics)
    {
        final boolean bEnemyNotTooSoon = statistics.getLastCreatedEnemyDeltaT() > enemyGenerationPeriod;
        final boolean bNotTooManyEnemies = statistics.getNumberOfEnemies() < maxNumEnemies;

        if (bEnemyNotTooSoon && bNotTooManyEnemies) // create enemy
        {
            final double currTime = statistics.getCurrentPlayTime();
            final double basePortion = Math.atan(currTime / timeHalftime) * 2 / Math.PI * (1.0 - difficultyGenerationRange);
            final double generatedPortion = random.nextDouble() * difficultyGenerationRange;
            final double difficulty = (generatedPortion + basePortion) * Curves.CURVES_MAX_DIFFICULTY;
            return difficulty;
        }
        else
            return CURVES_NO_ENEMY_CREATED;
    }
}
