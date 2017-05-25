package com.mygdx.game.gameLogic.GameDirector.Statistic;


import java.util.ArrayDeque;
import java.util.Queue;

/**
 * class that register statistics about imte passed, the stress level in the current time, as a fucntion of number of hero movements on X and Y axis
 * and registers current number of enemies in monitored level
 */
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

    private final double inputTimeMemory; //in seconds

    /**
     * constructor
     * @param jumpFrequencyScaler if number is high, more calls to method registerJump produce less stress
     * @param movementFrequencyScaler if number is high, more calls to method registerMovement produce less stress
     * @param timeMemory number of seconds before jumps and movement are forgotten (all jumps and movements are timestamped)
     */
    public Statistics(final double jumpFrequencyScaler, final double movementFrequencyScaler, final double timeMemory )
    {
        this.jumpFreqScaler = jumpFrequencyScaler;
        this.movFreqScaler = movementFrequencyScaler;
        this.inputTimeMemory = timeMemory;

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

    /**
     * Updates nuber of ground enemies if level, should be called by the level manager itself whene enemies are creted or destroyed
     * @param deltaEnemies positive for created enemies, negative for destroyed enemeis
     */
    @Override
    public void updateNumberOfGroundEnemies(int deltaEnemies) {
        numGroundEnemies += deltaEnemies;
        if (numGroundEnemies < 0)
            numGroundEnemies = 0;

        checkCreatedEnemy(deltaEnemies);
    }

    /**
     * specific pollOrientation method for statistics. updates internal time and deletes movement and jumps that are too old
     * @param deltaT tiem in seconds
     */
    @Override
    public void update(float deltaT) {

        currentPlayTime += deltaT;
        updateQueue(jumpTimes);
        updateQueue(movementTimes);
    }

    private void updateQueue(final Queue<Double> queue)
    {
        //delta time since jump or movement is bigger than memory?
        while((! queue.isEmpty()) && currentPlayTime - queue.element() > inputTimeMemory)
            queue.remove();
    }

    /**
     * Ticks a movement, more ticks mean more sress for the movement part
     */
    @Override
    public void registerMovement() {

        movementTimes.add(currentPlayTime);
    }

    /**
     * Ticks a jump, more ticks mean more sress for the part part
     */
    @Override
    public void registerJump() {

        jumpTimes.add(currentPlayTime);
    }

    /**
     * Sets light çeve part, that used for stress calculation
     * @param radiusPart should go form 0.0 (no light) to 1.0 (max light)
     */
    @Override
    public void setLightLevel(double radiusPart) {

        if (radiusPart < 0.0)
            currLightRadiusPart = 0.0;
        else if (radiusPart > 1.0)
            currLightRadiusPart = 1.0;
        else
            currLightRadiusPart = radiusPart;

    }

    //// getters -------

    /**
     *
     * @return number of ground enemis
     */
    @Override
    public int getNumGroundEnemies() {
        return numGroundEnemies;
    }

    /**
     *
     * @return numebr of flying enemies
     */
    public int getNumFlyingEnemies() {
        return numFlyingEnemies;
    }

    /**
     *
     * @return number of ground + flying enemies
     */
    @Override
    public int getNumberOfEnemies() {
        return getNumGroundEnemies() + getNumFlyingEnemies();
    }

    /**
     *
     * @return play time as stored in this object
     */
    public double getCurrentPlayTime() {
        return currentPlayTime;
    }

    /**
     *
     * @return time since last enemy was created (pollOrientation enemy bigger than 0)
     */
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

    /**
     * Returns the stress level as determined by this statistics object, as a function of input.
     * @return from 0.0 (no stress) to 1.0 (highest stress)
     */
    @Override
    public double getStressLevel() {
        final double jumpStressRatio = getJumpStress();
        final double movStressRatio = getMovStress();
        final double lightRatio = 1.0 - getLightLevel();

        final double compositeStressRatio =
                (JUMP_WEIGHT * jumpStressRatio + MOV_WEIGHT * movStressRatio + LIGHT_WEIGHT * lightRatio)
                / (JUMP_WEIGHT + MOV_WEIGHT + LIGHT_WEIGHT);

//        System.out.println("input portion " + compositeStressRatio +" light portion " + lightRatio);

        return compositeStressRatio;
    }

    /**
     * Updates number of flying enemies of level, should be called by the level manager itself when enemies are created or destroyed
     * @param i positive for created enemies, negative for destroyed enemeis
     */
    @Override
    public void updateNumberOfFlyingEnemies(int i) {
        numFlyingEnemies += i;
        if (numFlyingEnemies < 0)
            numFlyingEnemies = 0;

        checkCreatedEnemy(i);
    }
}
