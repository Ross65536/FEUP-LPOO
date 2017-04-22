package com.mygdx.game.gameLogic;


public class Hero extends Character {
    public Hero (double charXPos, double charYPos, double charXDim, double charYDim)
    {
        super( charXPos,  charYPos,  charXDim,  charYDim);
    }
    public Hero(Hero hero)
    {
        super(hero.characterPosition, hero.characterDimensions, hero.charactedSpeed);
    }
    protected void hi() {}
}
