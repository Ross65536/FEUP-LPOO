package ros.joao.rjtorcher.gameLogic.GameDirector.Statistic;

/**
 * Provide the interface to input and pollOrientation statistics
 */
public interface StatisticsInput {
    /**
     * Updates nuber of ground enemies if level, should be called by the level manager itself whene enemies are creted or destroyed
     * @param deltaEnemies positive for created enemies, negative for destroyed enemeis
     */
    void updateNumberOfGroundEnemies (final int deltaEnemies);
    /**
     * Updates number of flying enemies of level, should be called by the level manager itself when enemies are created or destroyed
     * @param deltaEnemies positive for created enemies, negative for destroyed enemeis
     */
    void updateNumberOfFlyingEnemies (final int deltaEnemies);
    /**
     * specific pollOrientation method for statistics. updates internal time and deletes movement and jumps that are too old
     * @param deltaT tiem in seconds
     */
    void update(final float deltaT);
    /**
     * Ticks a movement, more ticks mean more sress for the movement part
     */
    void registerMovement();
    /**
     * Ticks a jump, more ticks mean more sress for the part part
     */
    void registerJump();
    /**
     * Sets light çeve part, that used for stress calculation
     * @param radiousPercentage should go form 0.0 (no light) to 1.0 (max light)
     */
    void setLightLevel(double radiousPercentage);
}
