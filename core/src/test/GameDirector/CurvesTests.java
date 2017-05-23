package test.GameDirector;

import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.BalancedCurve;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.IncreasingDifficulty;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.RandomCurve;

import org.junit.Test;
import static org.junit.Assert.*;

public class CurvesTests {
    static final double DELTA_DOUBLE = 0.00001;


    @Test
    public void randomCurvesTests()
    {
        MockStatistics statistics = new MockStatistics();
        RandomCurve randomCurve = new RandomCurve();

        double difficulty = randomCurve.generateDifficulty(statistics);
        assertTrue(difficulty >=0.0 && difficulty <= Curves.CURVES_MAX_DIFFICULTY || difficulty == Curves.CURVES_NO_ENEMY_CREATED);

        //random curve should give half difficulty on average
        double diff = diminishRandomness(statistics, randomCurve);

        assertEquals(0.5 * Curves.CURVES_MAX_DIFFICULTY, diff, 0.05 * Curves.CURVES_MAX_DIFFICULTY);
    }

    private final double diminishRandomness(MockStatistics statistics, Curves curve)
    {
        final int iterations = 100000;
        double sum=0;
        int n=0;
        for (int i=0; i < iterations; i++)
        {
            double difficulty = curve.generateDifficulty(statistics);
            if (difficulty != Curves.CURVES_NO_ENEMY_CREATED)
            {
                sum += difficulty;
                n++;
            }
        }

        return sum/n;
    }

    private static final double ENEMY_DELTA_T = 2.0;
    private static final int MAX_ENEMIES = 3;
    private static final double BALANCED_RANDOMNESS_PORTION = 0.5;
    @Test
    public void balancedCurveTests()
    {
        MockStatistics statistics = new MockStatistics();
        BalancedCurve balancedCurve = new BalancedCurve(ENEMY_DELTA_T, MAX_ENEMIES);

        //should'nt make enemy, too soon
        statistics.setBalancedValues(0.0,0.0, 0);
        double diff = balancedCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );
        statistics.setLastCreatedEnemyDeltaT(ENEMY_DELTA_T-0.1);
        diff = balancedCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );

        //too many enemies
        statistics.setBalancedValues(ENEMY_DELTA_T * 3, 0.0, MAX_ENEMIES + 1 );
        diff = balancedCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );
        statistics.setNumberOfEnemies(MAX_ENEMIES);
        diff = balancedCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );

        //shoudl generate a high difficulty half or more
        statistics.setBalancedValues(ENEMY_DELTA_T * 3, 0.0, 0);
        diff = balancedCurve.generateDifficulty(statistics);
        assertTrue(diff != Curves.CURVES_NO_ENEMY_CREATED && diff > (BALANCED_RANDOMNESS_PORTION - 0.05) * Curves.CURVES_MAX_DIFFICULTY);
        //should be a low difficulty, less than half max difficulty
        statistics.setStressLevel(1.0);
        diff = balancedCurve.generateDifficulty(statistics);
        assertTrue(diff != Curves.CURVES_NO_ENEMY_CREATED && diff < (BALANCED_RANDOMNESS_PORTION + 0.05) * Curves.CURVES_MAX_DIFFICULTY);

        //values should be incremental
        statistics.setBalancedValues(ENEMY_DELTA_T*2, 0.0, 0);
        double lowStressDiff = diminishRandomness(statistics, balancedCurve);

        statistics.setStressLevel(0.5);
        double midStressDiff = diminishRandomness(statistics, balancedCurve);

        statistics.setStressLevel(1.0);
        double highStressDiff = diminishRandomness(statistics, balancedCurve);

        assertTrue(highStressDiff < midStressDiff && midStressDiff < lowStressDiff);

    }

    private double NOT_USED_ARGUMENT = 0.0;
    @Test
    public void scalingCurveTests()
    {
        MockStatistics statistics = new MockStatistics();
        final double generationRange = 0.0;
        final double timeMidpoint = 10;
        IncreasingDifficulty increasingCurve = new IncreasingDifficulty(generationRange, timeMidpoint, ENEMY_DELTA_T, MAX_ENEMIES);

        //should'nt make enemy, too soon
        statistics.setIncreasingValues(0.0,0, 0);
        double diff = increasingCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );
        statistics.setLastCreatedEnemyDeltaT(ENEMY_DELTA_T-0.1);
        diff = increasingCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );

        //too many enemies
        statistics.setIncreasingValues(ENEMY_DELTA_T * 3, MAX_ENEMIES + 1, 0 );
        diff = increasingCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );
        statistics.setNumberOfEnemies(MAX_ENEMIES);
        diff = increasingCurve.generateDifficulty(statistics);
        assertTrue(diff == Curves.CURVES_NO_ENEMY_CREATED );

        //testing that difficulties go up
        statistics.setIncreasingValues(ENEMY_DELTA_T * 3, 0, 0 );
        double lesserDiff = increasingCurve.generateDifficulty(statistics);
        statistics.setCurrentPlayTime(timeMidpoint / 2);
        double higherDiff = increasingCurve.generateDifficulty(statistics);
        assertTrue(higherDiff > lesserDiff);

        lesserDiff = higherDiff;
        statistics.setCurrentPlayTime(timeMidpoint);
        higherDiff = increasingCurve.generateDifficulty(statistics);
        assertTrue(higherDiff > lesserDiff);
        assertEquals(higherDiff, 0.5 * Curves.CURVES_MAX_DIFFICULTY, 0.05);

        lesserDiff = higherDiff;
        statistics.setCurrentPlayTime(2 * timeMidpoint);
        higherDiff = increasingCurve.generateDifficulty(statistics);
        assertTrue(higherDiff > lesserDiff);

        //REALLY high values should be similar
        statistics.setCurrentPlayTime(1e6 * timeMidpoint);
        lesserDiff = increasingCurve.generateDifficulty(statistics);
        statistics.setCurrentPlayTime(1e9 * timeMidpoint);
        higherDiff = increasingCurve.generateDifficulty(statistics);
        assertEquals(higherDiff, lesserDiff, 0.05);
    }
}
