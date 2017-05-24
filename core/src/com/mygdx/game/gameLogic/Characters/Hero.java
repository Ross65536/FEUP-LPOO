package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

/**
 * Class that represents the player controlled character.
 */
public class Hero extends Character implements HeroInfo {
    private static final double JUMP_SPEED_MULTIPLIER = 5.0;
    private static final double JUMP_ACCELERATION_MULTIPLIER = 10.0;
    private final double maxSpeedXMult;
    private boolean bMovingRight;

    private boolean jumping;
    private double riseGravityStrength; //gravity

    public Hero (final Vector2D position, final Vector2D dimensions, double heroMaxSpeedXMult)
    {
        super( position, dimensions, null);

        jumping = false;
        riseGravityStrength = 0.0;
        this.maxSpeedXMult = heroMaxSpeedXMult;
        bMovingRight = true;

    }

    /**
     * Method that makes the hero move on the X axis.
     * d is multiplied by the max speed of the hero to obtain the speed of the hero on the X axis.
     * @param d goes form -1.0 to 1.0, with d < 0.0 menaing moving the hero left, d > 0.0 moving the hero right and d ==0.0 hero not moving
     */
    public void setXMovement (double d) {
        if (d > 0.0)
            bMovingRight = true;
        else if (d < 0.0)
            bMovingRight = false;

        final double heroMaxXSpeed = this.getYDim() * maxSpeedXMult;
        characterSpeed.x = heroMaxXSpeed * d;
    }

    /**
     *
     * @return true if hero is moving on X axis or jumping (moving on Y axis)
     */
    public boolean isMoving(){
        if((characterSpeed.x!=0) || (this.isJumping()))
            return true;
        return false;
    }

    /**
     * makes the hero character jump into the Y axis. Te movement along the Y axis follows a parabollic path (jump with gravity).
     * The hero is shot into the Y axis with a speed and is then that speed is then subtracted on each pollOrientation.
     * @param gravityStrength strength of the gravity that determines how much the hero is slowed down. should go from aorund 0.5 to 1.0
     */
    public void jump(final double gravityStrength)
    {
        if (! isJumping()) //first time called sets the jump motion
        {
            jumping = true;
            final double newYSpeed= this.getYDim() * JUMP_SPEED_MULTIPLIER;
            characterSpeed.y = newYSpeed; //initial jump
        }

        this.riseGravityStrength = gravityStrength;
    }

    /**
     * Allows the hero to fall from platform. Same as jump method only without an initial thrust on the Y axis.
     * @param gravityStrength determines the strenght of the gravity, same as in the jump method.
     */
    public void fall(final double gravityStrength)
    {
        if (! isJumping()) //first time called sets the jump motion
        {
            jumping = true;
            final double newYSpeed= 0;
            characterSpeed.y = newYSpeed; //initial jump
        }
        this.riseGravityStrength = gravityStrength;
    }

    private double getYAcceleration(double gravityStrength) {
        return - gravityStrength * this.getYDim() * JUMP_ACCELERATION_MULTIPLIER;
    }

    /**
     * Stops the hero's movement on the Y axis (juming and falling).
     */
    public void stopJump()
    {
        jumping = false;
        riseGravityStrength = 0.0;
        characterSpeed.y = 0.0;
    }

    /**
     * Specific pollOrientation method for Hero class. updates speed on Y axis when jumping or falling is in progress. and has specific animation time logic.
     * @param deltaT tiem in seconds
     */
    @Override
    public void update(float deltaT)
    {
        super.update(deltaT);

        if (!isMovingX() || isMovingY()) //resets animation
            animationTime = 0.0;

        double yAcceleration;
        if (isFalling())
            yAcceleration = getYAcceleration(1.0);
        else
            yAcceleration = getYAcceleration(this.riseGravityStrength);

        final double newYSpeed= this.characterSpeed.y + yAcceleration * deltaT;
        characterSpeed.y = newYSpeed;
    }

    /**
     *
     * @return true if hero is jumping (either the upwards or downwards part)
     */
    public boolean isJumping() {
        return jumping;
    }

    /**
     *
     * @return true if hero if facing the right direction
     */
    @Override
    public boolean isMovingRight() {
        return bMovingRight;
    }

    private static final double HERO_X_LEEWAY = 0.4;
    private static final double HERO_Y_LEEWAY = 0.2;

    /**
     * Check if the hero collides with entity en. The collisions arent checked at hero borders but a bit towards the center.
     * @param en the object to test against for collisions.
     * @return true if there is a collision, false otherwise
     */
    @Override
    public boolean checkCollision(final Entity en) {
        final boolean heroXLeft = characterPosition.x + (1.0 - HERO_X_LEEWAY) * characterDimensions.x < en.characterPosition.x;
        final boolean heroXRight = characterPosition.x + HERO_X_LEEWAY * characterDimensions.x  > en.characterPosition.x + en.characterDimensions.x;
        final boolean heroYDown = characterPosition.y + (1.0 - HERO_Y_LEEWAY) * characterDimensions.y < en.characterPosition.y;
        final boolean heroYUp = characterPosition.y + HERO_Y_LEEWAY * characterDimensions.y > en.characterPosition.y + en.characterDimensions.y;

        return !(heroXLeft || heroXRight || heroYUp || heroYDown);
    }
}
