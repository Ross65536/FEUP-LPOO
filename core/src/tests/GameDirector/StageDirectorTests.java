package tests.GameDirector;

import ros.joao.rjtorcher.gameLogic.Characters.Enemy;
import ros.joao.rjtorcher.gameLogic.Characters.EnemyFlying;
import ros.joao.rjtorcher.gameLogic.Characters.EnemyGround;
import ros.joao.rjtorcher.gameLogic.GameDirector.DifficultyCurves.Curve;
import ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes.EnemyTypes;
import ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes.EnemyTypesLinear;
import ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector;
import ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.StatisticsInfo;

import org.junit.Test;
import static org.junit.Assert.*;


public class StageDirectorTests {
    class MockEnemyTypes extends EnemyTypes{
        Enemy enemy;

        public MockEnemyTypes() {
            super(0);
        }

        @Override
        public Enemy chooseEnemyType(double difficulty) {
            return enemy;
        }
        public void setEnemy(Enemy enemy) { this.enemy=enemy;}
    }
    class MockCurve extends Curve {
        double difficulty;
        @Override
        public double generateDifficulty(StatisticsInfo statistics) {
            return difficulty;
        }
        public void setDifficulty (double difficulty) { this.difficulty=difficulty;}
    }
    class MockEnemy extends Enemy
    {
        boolean flyingType = false;
        int arrayIndex=0;
        public MockEnemy() {
            super(null,null,null, false);
        }

        @Override
        public int getArrayIndex() {
            return arrayIndex;
        }

        @Override
        public boolean isFlyingType() {
            return flyingType;
        }

        public void setArrayIndex(int index) {
            arrayIndex=index;
        }

        public void setisFlyingType(boolean bool) {
            flyingType = bool;
        }
    }

    private static final double Y_SCALER = 1.0;
    @Test(expected = IndexOutOfBoundsException.class)
    public void baseStageDirectorTests()
    {
        MockCurve curve = new MockCurve();
        MockEnemyTypes mockTypes = new MockEnemyTypes();
        MockStatistics statistics = new MockStatistics();
        MockEnemy enemy = new MockEnemy();
        mockTypes.setEnemy(enemy);
        StageDirector stageDirector = new StageDirector(curve, statistics, mockTypes);

        //shoud return null
        curve.setDifficulty(Curve.CURVES_NO_ENEMY_CREATED);
        assertTrue(stageDirector.tryGenerateEnemy() == null);

        //testing different enemies
        curve.setDifficulty(Curve.CURVES_MAX_DIFFICULTY / 2);
        enemy.setisFlyingType(true);
        assertTrue(stageDirector.tryGenerateEnemy()== enemy);
        assertEquals(statistics.getFlyingUpdatedEnemies(), 1);

        statistics.resetUpdatesEnemies();
        curve.setDifficulty(Curve.CURVES_MAX_DIFFICULTY / 2);
        enemy.setisFlyingType(false);
        assertTrue(stageDirector.tryGenerateEnemy()== enemy);
        assertEquals(statistics.getGroundUpdatedEnemies(), 1);

        //should throw exception
        mockTypes.setEnemy(null);
        stageDirector.tryGenerateEnemy();
    }

    @Test
    public void linearTypesTest()
    {
        EnemyTypesLinear linearTypes = new EnemyTypesLinear(Y_SCALER, 0.25,0.5,0.75,1.0);
        Enemy enemy;

        //should return null
        enemy =linearTypes.chooseEnemyType(-1.0);
        assertTrue(enemy == null);

        enemy =linearTypes.chooseEnemyType(Curve.CURVES_MAX_DIFFICULTY*1.1);
        assertTrue(enemy == null);

        enemy =linearTypes.chooseEnemyType(Curve.CURVES_MAX_DIFFICULTY*0.1);
        assertTrue(enemy instanceof EnemyGround);
        assertFalse(enemy.isBossType());

        enemy =linearTypes.chooseEnemyType(Curve.CURVES_MAX_DIFFICULTY*0.3);
        assertTrue(enemy instanceof EnemyFlying);
        assertFalse(enemy.isBossType());

        enemy =linearTypes.chooseEnemyType(Curve.CURVES_MAX_DIFFICULTY*0.6);
        assertTrue(enemy instanceof EnemyGround);
        assertTrue(enemy.isBossType());

        enemy =linearTypes.chooseEnemyType(Curve.CURVES_MAX_DIFFICULTY*0.9);
        assertTrue(enemy instanceof EnemyFlying);
        assertTrue(enemy.isBossType());


    }

}
