package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.CommonConstants;

public class Hero extends Character implements HeroInputs {
    private double heroMaxXSpeed;
    public Hero (double charXPos, double charYPos, double charXDim, double charYDim)
    {
        super( charXPos,  charYPos,  charXDim,  charYDim);
        heroMaxXSpeed = charXDim * CommonConstants.characterXDimToSpeedMult;
    }

    public Hero(Hero hero)
    {
        super(hero.characterPosition, hero.characterDimensions, hero.characterSpeed);
    }

    public void setXMovement (double d) {
        characterSpeed.setX(heroMaxXSpeed * d);
    }
}
