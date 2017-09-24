package ros.joao.rjtorcher.gameLogic.Characters;


/**
 * Class that represents world characters such as the player controlled hero and the enemies.
 */
public abstract class Character extends Entity implements ros.joao.rjtorcher.gameLogic.Characters.CharacterInfo {
    protected ros.joao.rjtorcher.Vector2D characterSpeed;
    protected ros.joao.rjtorcher.Vector2D prevPosition;
    protected double animationTime; //goes from 0.0 to 1.0

    /**
     * Constructor. All parameters are object references which are CLONED. If object are null references
     * they are cloned as if they contained (0.0, 0.0)
     * @param position Vector2D object that contains the position of the Character
     * @param dimensions Vector2D object that contains the dimensions of the Character
     * @param speed Vector2D object that contains the position of the Character
     */
    public Character (final ros.joao.rjtorcher.Vector2D position, final ros.joao.rjtorcher.Vector2D dimensions, final ros.joao.rjtorcher.Vector2D speed)
    {
        super(position, dimensions);

        if (speed == null)
            characterSpeed = new ros.joao.rjtorcher.Vector2D(0,0);
        else
            characterSpeed = new ros.joao.rjtorcher.Vector2D(speed);

        animationTime= 0.0;
        prevPosition = null;
    }

    /**
     *
     * @return true if the character is moving downwards
     */
    @Override
    public boolean isFalling() {
        return characterSpeed.y < 0.0;
    }

    /**
     * Updates the state of the character, letting deltaT seconds pass, updating position and etc
     * @param deltaT tiem in seconds
     */
    public void update(float deltaT)
    {
        animationTime += deltaT;
        if (animationTime >= 1.0) //loop animation time
            animationTime -= 1.0;

        //added
        this.prevPosition = new ros.joao.rjtorcher.Vector2D(characterPosition);

        final double newX = this.characterPosition.x + this.characterSpeed.x * deltaT;
        final double newY = this.characterPosition.y + this.characterSpeed.y * deltaT;
        this.characterPosition.setXY(newX, newY);

    }

    /**
     *
     * @return Animation time, which goes from 0.0 to 1.0
     */
    public double getAnimationTime()
    {
        return animationTime;
    }

    /**
     *
     * @return Vector2D containted the position of the character before the last pollOrientation method call
     */
    public ros.joao.rjtorcher.Vector2D getPrevPosition(){
        return this.prevPosition;
    }

    /**
     *
     * @return true if character is moving along the y axis
     */
    public boolean isMovingY()
    {
        return characterSpeed.y != 0.0;
    }

    /**
     *
     * @return true if character is moving along the x axis
     */
    public boolean isMovingX()
    {
        return characterSpeed.x != 0.0;
    }

    /**
     *
     * @return double indicating which direction the character is moving. 0.0 for not moving, < 0.0 for moving left,  > 0.0 for moving right
     *
     */
    public double getMovementDirectionX()
    {
        return characterSpeed.x;
    }

    /**
     *
     * @return true if character is moving to the right or standing still (on X axis).
     */
    public boolean isMovingRight() {
        return getMovementDirectionX() >= 0.0;
    }

}
