package com.mygdx.game.gameLogic.Characters;

public class Hero extends Character implements HeroInputs, HeroInfo {
    private static final double JUMP_SPEED_MULTIPLIER = 5.0;
    private static final double JUMP_ACCELERATION_MULTIPLIER = 10.0;
    private static final double HERO_MAX_SPEED_MULT = 3.0;

    private boolean jumping;
    private double Yacceleration; //gravity

    public Hero (double charXPos, double charYPos, double charXDim, double charYDim)
    {
        super( charXPos,  charYPos,  charXDim,  charYDim);

        jumping = false;
        Yacceleration = 0.0;

    }

    public Hero(Hero hero)
    {
        super(hero.characterPosition, hero.characterDimensions, hero.characterSpeed);
    }

    public void setXMovement (double d) {
        final double heroMaxXSpeed = this.getYDim() * HERO_MAX_SPEED_MULT;
        characterSpeed.x = heroMaxXSpeed * d;
    }

    @Override
    public void jump(double gravityStrength)
    {
        if (! isJumping()) //first time called sets the jump motion
        {
            jumping = true;
            final double newYSpeed= this.getYDim() * JUMP_SPEED_MULTIPLIER;
            characterSpeed.y = newYSpeed; //initial jump
        }
        if (isFalling())
            gravityStrength = 1.0;

        Yacceleration = - gravityStrength * this.getYDim() * JUMP_ACCELERATION_MULTIPLIER;
    }

    public void stopJump(final double newYPos)
    {
        jumping = false;
        Yacceleration = 0.0;
        characterSpeed.y = 0.0;
        characterPosition.y= 0.0;
    }

    @Override
    public void updatePos (float deltaT)
    {
        super.updatePos(deltaT);
        final double newYSpeed= this.characterSpeed.y + Yacceleration * deltaT;
        characterSpeed.y = newYSpeed;

    }

    public boolean isJumping() {
        return jumping;
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
