package tests.GameDirector;


import com.mygdx.game.gameLogic.GameDirector.Statistic.Statistics;

import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsTests {

    static final float DELTA_T = 0.5f;
    static final double JUMP_FREQUENCY_SCALER = 2.0;
    static final double MOVEMENT_FREQUENCY_SCALER = 2.0;
    static final double DELTA_DOUBLE = 0.00001;
    private static final double TIME_MEMORY = 2.0;


    @Test
    public void updateTest()
    {
        Statistics statistics = new Statistics(JUMP_FREQUENCY_SCALER, MOVEMENT_FREQUENCY_SCALER, TIME_MEMORY);
        assertEquals(statistics.getCurrentPlayTime(), 0.0 , DELTA_DOUBLE);
        statistics.update(DELTA_T);
        assertEquals(statistics.getCurrentPlayTime(), DELTA_T, DELTA_DOUBLE);
        statistics.update(DELTA_T);
        assertEquals(statistics.getCurrentPlayTime(), DELTA_T*2 , DELTA_DOUBLE);


    }

    private void passMemoryTime(Statistics statistics)
    {
        double currentTime = statistics.getCurrentPlayTime();

        while (currentTime + TIME_MEMORY > statistics.getCurrentPlayTime())
            statistics.update(DELTA_T);

        statistics.update(DELTA_T);
    }

    @Test
    public void enemiesData()
    {
        Statistics statistics = new Statistics(JUMP_FREQUENCY_SCALER, MOVEMENT_FREQUENCY_SCALER, TIME_MEMORY);
        assertEquals(statistics.getNumberOfEnemies(), 0);
        assertEquals(statistics.getNumGroundEnemies(), 0);
        assertEquals(statistics.getNumFlyingEnemies(), 0);
        assertEquals(statistics.getLastCreatedEnemyDeltaT(), 0.0, DELTA_DOUBLE);

        statistics.updateNumberOfFlyingEnemies(2);

        assertEquals(statistics.getNumberOfEnemies(), 2);
        assertEquals(statistics.getNumGroundEnemies(), 0);
        assertEquals(statistics.getNumFlyingEnemies(), 2);

        statistics.update(DELTA_T);
        assertEquals(statistics.getLastCreatedEnemyDeltaT(), DELTA_T, DELTA_DOUBLE);

        statistics.updateNumberOfFlyingEnemies(-1);

        assertEquals(statistics.getNumberOfEnemies(), 1);
        assertEquals(statistics.getNumGroundEnemies(), 0);
        assertEquals(statistics.getNumFlyingEnemies(), 1);

        statistics.update(DELTA_T);
        assertEquals(statistics.getLastCreatedEnemyDeltaT(), 2* DELTA_T, DELTA_DOUBLE);

        statistics.updateNumberOfGroundEnemies(5);

        assertEquals(statistics.getLastCreatedEnemyDeltaT(), 0.0, DELTA_DOUBLE);
        statistics.update(DELTA_T);
        assertEquals(statistics.getLastCreatedEnemyDeltaT(), DELTA_T, DELTA_DOUBLE);

        assertEquals(statistics.getNumberOfEnemies(), 6);
        assertEquals(statistics.getNumGroundEnemies(), 5);
        assertEquals(statistics.getNumFlyingEnemies(), 1);

        statistics.updateNumberOfGroundEnemies(-10);

        assertEquals(statistics.getNumberOfEnemies(), 1);
        assertEquals(statistics.getNumGroundEnemies(), 0);
        assertEquals(statistics.getNumFlyingEnemies(), 1);

        statistics.updateNumberOfFlyingEnemies(-2);

        assertEquals(statistics.getNumberOfEnemies(), 0);
        assertEquals(statistics.getNumGroundEnemies(), 0);
        assertEquals(statistics.getNumFlyingEnemies(), 0);

    }

    @Test
    public void inputAndStress()
    {
        Statistics statistics = new Statistics(JUMP_FREQUENCY_SCALER, MOVEMENT_FREQUENCY_SCALER, TIME_MEMORY);

        //light test
        statistics.setLightLevel(1.0);
        assertEquals(statistics.getStressLevel(), 0.0, DELTA_DOUBLE);

        statistics.setLightLevel(10.0);
        assertEquals(statistics.getStressLevel(), 0.0, DELTA_DOUBLE);

        statistics.setLightLevel(0.0);
        double stress = statistics.getStressLevel();
        assertTrue(stress> 0.0 && stress < 0.5); //light should be less than 50% of stress

        statistics.setLightLevel(-10.0);
        double stress2 = statistics.getStressLevel();
        assertTrue(stress == stress2);

        statistics.setLightLevel(0.5);
        double stress3 = statistics.getStressLevel();
        assertTrue( stress3 < stress && stress3 > 0.0);

        //get max stress
        statistics.setLightLevel(0.0);
        for (int i = 0; i < 1000 ; i++) // should be a high stress after this, close to 1.0
        {
            statistics.registerJump();
            statistics.registerMovement();
        }
        assertEquals(statistics.getStressLevel(), 1.0, 0.05);

        //stress leveled out after time
        this.passMemoryTime(statistics);
        statistics.setLightLevel(1.0);
        assertEquals(0.0, statistics.getStressLevel(),  DELTA_DOUBLE);

        // should give a stress close to 0.5
        statistics.setLightLevel(0.5);
        double baseLightStress = statistics.getStressLevel();
        for (int i = 0; i < JUMP_FREQUENCY_SCALER; i++)
            statistics.registerJump();
        for (int i = 0; i < MOVEMENT_FREQUENCY_SCALER; i++)
            statistics.registerMovement();

        assertEquals(statistics.getStressLevel(), 0.5, 0.05);

        this.passMemoryTime(statistics);
        assertEquals(statistics.getStressLevel(), baseLightStress, DELTA_DOUBLE);

    }

    @Test (timeout= 100)
    public void stressFalloutTest()
    {
        //stress should fall off for movement and jumps after some inactivity

        Statistics statistics = new Statistics(JUMP_FREQUENCY_SCALER, MOVEMENT_FREQUENCY_SCALER, TIME_MEMORY);

        for(int i=0; i < 5; i++)
        {
            statistics.registerMovement();
            statistics.update(DELTA_T);
        }

        double prevStress = statistics.getStressLevel();
        for(int i=0; i < 5; i++)
        {
            statistics.update(DELTA_T);
            double newStress = statistics.getStressLevel();
            if (newStress < prevStress)
                break;

        }

        statistics = new Statistics(JUMP_FREQUENCY_SCALER, MOVEMENT_FREQUENCY_SCALER, TIME_MEMORY);

        for(int i=0; i < 5; i++)
        {
            statistics.registerJump();
            statistics.update(DELTA_T);
        }

        prevStress = statistics.getStressLevel();
        for(int i=0; i < 5; i++)
        {
            statistics.update(DELTA_T);
            double newStress = statistics.getStressLevel();
            if (newStress < prevStress)
                break;

        }



    }

}
