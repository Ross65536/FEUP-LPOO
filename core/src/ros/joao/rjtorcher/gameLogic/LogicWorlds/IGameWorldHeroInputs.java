package ros.joao.rjtorcher.gameLogic.LogicWorlds;

/**
 * Provides an interface to controll the hero
 */
public interface IGameWorldHeroInputs {
    /**
     * Allows the hero to be moved on the X axis
     * @param heroXMovement should be from -1.0 to 1.0, being moving to the left or right respectively, and 0.0 stopping the hero.
     */
    void moveHeroHorizontal(final double heroXMovement);

    /**
     * Makes the hero jump.
     * Can be called with a weak gravity to make the hero jump higher.
     * @param gravityStrength strenght of the gravity, should go from 0.5 to 1.0
     */
    void heroJump(final double gravityStrength);

}
