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

        Yacceleration = - gravityStrength * this.getYDim() * JUMP_ACCELERATION_MULTIPLIER;
    }

    public void stopJump()
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
}
