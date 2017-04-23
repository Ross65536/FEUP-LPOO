package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.gameLogic.Vector2D;
import com.sun.org.apache.xerces.internal.impl.dv.xs.YearDV;

public class Character implements CharacterInfo {
    protected Vector2D characterPosition;
    protected Vector2D characterDimensions;
    protected Vector2D characterSpeed;

    public Character (double charXPos, double charYPos, double charXDim, double charYDim, double speedX, double speedY)
    {
        characterDimensions = new Vector2D(charXDim, charYDim);
        characterPosition = new Vector2D(charXPos, charYPos);
        characterSpeed = new Vector2D(speedX, speedY);
    }
    public Character (double charXPos, double charYPos, double charXDim, double charYDim)
    {
        this(charXPos, charYPos, charXDim, charYDim, 0, 0);
    }

    public Character(Vector2D characterPosition, Vector2D characterDimensions, Vector2D characterSpeed) {
        this.characterPosition= new Vector2D(characterPosition);
        this.characterDimensions= new Vector2D( characterDimensions);
        this.characterSpeed = new Vector2D(characterSpeed);
    }
    public double getXPos() {return characterPosition.getX(); }
    public double getYPos() {
        return characterPosition.getY();
    }
    public double getXDim() {return characterDimensions.getX(); }
    public double getYDim() {return characterDimensions.getY(); }

    public void setXSpeed (double vx) {characterSpeed.setX(vx); }

    public void updatePos (float deltaT)
    {
        final double newX = this.characterPosition.getX() + this.characterSpeed.getX() * deltaT;
        final double newY = this.characterPosition.getY() + this.characterSpeed.getY() * deltaT;
        this.characterPosition.setXY(newX, newY);
    }
    protected void setXYDims (double xDim, double yDim)
    {
        this.characterDimensions.setXY(xDim, yDim);
    }


}
