package com.mygdx.game.gameLogic;


import com.mygdx.game.gameLogic.Vector2D;

public class Character {
    protected Vector2D characterPosition;
    protected Vector2D characterDimensions;
    protected Vector2D charactedSpeed;


    public Character (double charXPos, double charYPos, double charXDim, double charYDim, double speedX, double speedY)
    {
        characterDimensions = new Vector2D(charXDim, charYDim);
        characterPosition = new Vector2D(charXPos, charYPos);
        charactedSpeed = new Vector2D(speedX, speedY);
    }
    public Character (double charXPos, double charYPos, double charXDim, double charYDim)
    {
        this(charXPos, charYPos, charXDim, charYDim, 0, 0);
    }

    public Character(Vector2D characterPosition, Vector2D characterDimensions, Vector2D characterSpeed) {
        this.characterPosition= new Vector2D(characterPosition);
        this.characterDimensions= new Vector2D( characterDimensions);
        this.charactedSpeed = new Vector2D(characterSpeed);
    }
    public double getXPos() {return characterPosition.getX(); }

    public double getYPos() {
        return characterPosition.getY();
    }

    public double getXDim() {return characterDimensions.getX(); }
    public double getYDim() {return characterDimensions.getY(); }
}
