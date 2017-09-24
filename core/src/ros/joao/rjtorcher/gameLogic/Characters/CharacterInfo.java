package ros.joao.rjtorcher.gameLogic.Characters;

/**
 * Provides an interface to query information about the character
 */
public interface CharacterInfo {
    double getXPos();
    double getYPos();
    double getXDim();
    double getYDim();
    /**
     *
     * @return true if the character is moving downwards
     */
    boolean isFalling();
    /**
     *
     * @return true if character is moving along the y axis
     */
    boolean isMovingY();
    /**
     *
     * @return true if character is moving along the x axis
     */
    boolean isMovingX();

    /**
     *
     * @return a value from 0.0 to 1.0
     */
    double getAnimationTime();
    /**
     *
     * @return double indicating which direction the character is moving. 0.0 for not moving, < 0.0 for moving left,  > 0.0 for moving right
     *
     */
    double getMovementDirectionX();
    /**
     *
     * @return true if character is moving to the right or standing still (on X axis).
     */
    boolean isMovingRight();

    /**
     *
     * @return coordiante which corresponds to the Y of the center of the character
     */
    double getYCenter();
    /**
     *
     * @return coordiante which corresponds to the X of the center of the character
     */
    double getXCenter();
}
