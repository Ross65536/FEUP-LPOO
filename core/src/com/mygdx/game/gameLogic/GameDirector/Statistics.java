package com.mygdx.game.gameLogic.GameDirector;


import java.util.ArrayDeque;
import java.util.Queue;

public class Statistics implements StatisticsInfo, StatisticsInput
{
    private double currentPlayTime; //playtime since level start
    private double lastEnemyCreationTime;
    private int numGroundEnemies;
    private int numFlyingEnemies;
    private double currLightRadiusPart; // 0.0 to 1.0
    private Queue<Double> jumpTimes;
    private Queue<Double> movementTimes;
    private final double jumpFreqScaler; //point at wich strees is 0.5
    private final double movFreqScaler; //point at wich strees is 0.5

    private static final double INPUTS_TIME_MEMORY = 2.0; //in seconds

    public Statistics(final double jumpFrequencyScaler, final double movementFrequencyScaler )
    {
        this.jumpFreqScaler = jumpFrequencyScaler;
        this.movFreqScaler = movementFrequencyScaler;

        currentPlayTime =0;
        numGroundEnemies=0;
        numFlyingEnemies = 0;
        lastEnemyCreationTime=0;
        currLightRadiusPart = 1.0;
        jumpTimes = new ArrayDeque<>();
        movementTimes = new ArrayDeque<>();
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
        updateQueue(jumpTimes);
        updateQueue(movementTimes);
    }

    private void updateQueue(final Queue<Double> queue)
    {
        //delta time since jump or movement is bigger than memory?
        while((! queue.isEmpty()) && currentPlayTime - queue.element() > INPUTS_TIME_MEMORY)
            queue.remove();
    }

    @Override
    public void registerMovement() {

        movementTimes.add(currentPlayTime);
    }

    @Override
    public void registerJump() {

        jumpTimes.add(currentPlayTime);
    }

    @Override
    public void setLightLevel(double radiusPart) {
        currLightRadiusPart = radiusPart;
    }

    //// getters -------
    @Override
    public int getNumGroundEnemies() {
        return numGroundEnemies;
    }

    public int getNumFlyingEnemies() {
        return numFlyingEnemies;
    }

    @Override
    public int getNumberOfEnemies() {
        return getNumGroundEnemies() + getNumFlyingEnemies();
    }

    public double getCurrentPlayTime() {
        return currentPlayTime;
    }

    @Override
    public double getLastCreatedEnemyDeltaT() {
        return currentPlayTime - lastEnemyCreationTime;
    }



    private static double stressNormalizer(final double x)
    {
        return Math.atan(x) * 2/ Math.PI;

    }

    private double getJumpStress() {
        return stressNormalizer(jumpTimes.size() / jumpFreqScaler);
    }

    private double getMovStress() {
        return stressNormalizer(movementTimes.size() / movFreqScaler);
    }

    private double getLightLevel() {
        return currLightRadiusPart;
    }

    private static final double JUMP_WEIGHT = 6.0;
    private static final double MOV_WEIGHT = 1.0;
    private static final double LIGHT_WEIGHT = 2.0;

    @Override
    public double getStressLevel() {
        final double jumpStressRatio = 1.0 - getJumpStress();
        final double movStressRatio = 1.0 - getMovStress();
        final double lightRatio = getLightLevel();

        final double compositeStressRatio =
                (JUMP_WEIGHT * jumpStressRatio + MOV_WEIGHT * movStressRatio + LIGHT_WEIGHT * lightRatio)
                / (JUMP_WEIGHT + MOV_WEIGHT + LIGHT_WEIGHT);

//        System.out.println("input portion " + compositeStressRatio +" light portion " + lightRatio);

        return compositeStressRatio;
    }

    @Override
    public void updateNumberOfFlyingEnemies(int i) {
        numFlyingEnemies += i;
        if (numFlyingEnemies < 0)
            numFlyingEnemies = 0;

        checkCreatedEnemy(i);
    }
}
