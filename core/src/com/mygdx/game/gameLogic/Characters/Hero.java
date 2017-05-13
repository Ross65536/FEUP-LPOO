package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.gameLogic.Vector2D;

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

    public void setXMovement (double d) {
        if (d > 0.0)
            bMovingRight = true;
        else if (d < 0.0)
            bMovingRight = false;

        final double heroMaxXSpeed = this.getYDim() * maxSpeedXMult;
        characterSpeed.x = heroMaxXSpeed * d;
    }

    public boolean isMoving(){
        if((characterSpeed.x!=0) || (this.isJumping()))
            return true;
        return false;
    }

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

    public void stopJump()
    {
        jumping = false;
        riseGravityStrength = 0.0;
        characterSpeed.y = 0.0;
    }

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

    public boolean isJumping() {
        return jumping;
    }

    @Override
    public boolean isMovingRight() {
        return bMovingRight;
    }

    private static final double HERO_X_LEEWAY = 0.2;
    private static final double HERO_Y_LEEWAY = 0.2;



    @Override
    public boolean checkCollision(final Character en) {
        final boolean heroXLeft = characterPosition.x + (1.0 - HERO_X_LEEWAY) * characterDimensions.x < en.characterPosition.x;
        final boolean heroXRight = characterPosition.x + HERO_X_LEEWAY * characterDimensions.x  > en.characterPosition.x + en.characterDimensions.x;
        final boolean heroYDown = characterPosition.y + (1.0 - HERO_Y_LEEWAY) * characterDimensions.y < en.characterPosition.y;
        final boolean heroYUp = characterPosition.y + HERO_Y_LEEWAY * characterDimensions.y > en.characterPosition.y + en.characterDimensions.y;

        return !(heroXLeft || heroXRight || heroYUp || heroYDown);
    }
}
