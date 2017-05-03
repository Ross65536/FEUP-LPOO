package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.gameLogic.Vector2D;

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

    public Character (final Vector2D position, final Vector2D dimensions, final Vector2D speed)
    {
        characterDimensions = new Vector2D(position);
        characterPosition = new Vector2D(dimensions);
        characterSpeed = new Vector2D(speed);
    }

    public Character (double charXPos, double charYPos, double charXDim, double charYDim)
    {
        this(charXPos, charYPos, charXDim, charYDim, 0, 0);
    }

    protected Character()
    {
        this.characterSpeed = null;
        this.characterPosition = null;
        this.characterDimensions = null;
    }

    public Character(Vector2D characterPosition, Vector2D characterDimensions) {
        this(characterPosition, characterDimensions, new Vector2D(0,0));
    }

    public double getXPos() {return characterPosition.x; }
    public double getYPos() {
        return characterPosition.y;
    }
    public double getXDim() {return characterDimensions.x; }
    public double getYDim() {return characterDimensions.y; }

    public void setXSpeed (double vx) {characterSpeed.x =vx; }

    public void updatePos (float deltaT)
    {
        final double newX = this.characterPosition.x + this.characterSpeed.x * deltaT;
        final double newY = this.characterPosition.y + this.characterSpeed.y * deltaT;
        this.characterPosition.setXY(newX, newY);
    }
    protected void setXYDims (double xDim, double yDim)
    {
        this.characterDimensions.setXY(xDim, yDim);
    }


    public boolean checkCollision(final Character en) {
        return ! (
                (characterPosition.x + characterDimensions.x < en.characterPosition.x) ||
                (characterPosition.x > en.characterPosition.x + en.characterDimensions.x) ||
                (characterPosition.y + characterDimensions.y < en.characterPosition.y) ||
                (characterPosition.y > en.characterPosition.y + en.characterDimensions.y)
        );
    }
}
