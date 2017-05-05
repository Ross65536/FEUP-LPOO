package com.mygdx.game.gameLogic.GameDirector;


public class Statistics implements StatisticsInfo, StatisticsInput
{
    private double currentPlayTime; //playtime since level start
    private double lastEnemyCreationTime;
    private int numGroundEnemies;

    public Statistics()
    {
        currentPlayTime =0;
        numGroundEnemies=0;
        lastEnemyCreationTime=0;
    }

    private void checkCreatedEnemy(int deltaEnemies)
    {
        if (deltaEnemies > 0)
            lastEnemyCreationTime = currentPlayTime;
    }

    //// setters ---------
    @Override
    public void updateNumberOfGroundEnemies(int deltaEnemies) {
        numGroundEnemies += deltaEnemies;
        if (numGroundEnemies < 0)
            numGroundEnemies = 0;

        checkCreatedEnemy(deltaEnemies);
    }

    @Override
    public void update(float deltaT) {
        currentPlayTime += deltaT;
    }

    //// getters -------
    @Override
    public int getNumGroundEnemies() {
        return numGroundEnemies;
    }

    @Override
    public int getNumberOfEnemies() {
        return getNumGroundEnemies();
    }

    public double getCurrentPlayTime() {
        return currentPlayTime;
    }

    @Override
    public double getLastCreatedEnemyDeltaT() {
        return currentPlayTime - lastEnemyCreationTime;
    }
}
