package tests.GameDirector;

import ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.Statistics;


public class MockStatistics extends Statistics
{
    double lastCreatedEnemyDeltaT = 0;
    int numberOfEnemies = 0;
    double stress = 0;
    double playTime =0;
    int updatedGroundEnemies = -1;
    int updatedFlyingEnemies = -1;

    public MockStatistics() {
        super(0, 0, 0);
    }
    public void setLastCreatedEnemyDeltaT(double deltaT)  { lastCreatedEnemyDeltaT = deltaT;   }
    public void setNumberOfEnemies(int num) {numberOfEnemies=num;}
    public void setStressLevel(double stress) {this.stress =stress;}
    public void setCurrentPlayTime(double time) { playTime=time;}
    @Override
    public double getLastCreatedEnemyDeltaT() {return lastCreatedEnemyDeltaT;}

    @Override
    public int getNumberOfEnemies() {return numberOfEnemies;}

    @Override
    public double getStressLevel() {return stress;}

    @Override
    public double getCurrentPlayTime() { return playTime;}

    public void setBalancedValues(double enemyDeltaT, double stress, int numEnemies)
    {
        setLastCreatedEnemyDeltaT(enemyDeltaT);
        setStressLevel(stress);
        setNumberOfEnemies(numEnemies);
    }

    public void setIncreasingValues(double enemyDeltaT, int numEnemies, double playTime)
    {
        setLastCreatedEnemyDeltaT(enemyDeltaT);
        setNumberOfEnemies(numEnemies);
        setCurrentPlayTime(playTime);
    }

    @Override
    public void updateNumberOfFlyingEnemies(int num) { updatedFlyingEnemies = num;}
    @Override
    public void updateNumberOfGroundEnemies(int num) { updatedGroundEnemies = num;}

    public int getGroundUpdatedEnemies() { return updatedGroundEnemies;}
    public int getFlyingUpdatedEnemies() { return updatedFlyingEnemies;}
    public void resetUpdatesEnemies() {
        updatedGroundEnemies =-1;
        updatedFlyingEnemies = -1;
    }
}
