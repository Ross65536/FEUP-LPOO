package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.gameLogic.Vector2D;

public abstract class Character implements CharacterInfo {
    protected Vector2D characterPosition;
    protected Vector2D characterDimensions;
    protected Vector2D characterSpeed;
    protected Vector2D prevPosition;

    public Character (double charXPos, double charYPos, double charXDim, double charYDim, double speedX, double speedY)
    {
        characterDimensions = new Vector2D(charXDim, charYDim);
        characterPosition = new Vector2D(charXPos, charYPos);
        characterSpeed = new Vector2D(speedX, speedY);
    }

    public Character (final Vector2D position, final Vector2D dimensions, final Vector2D speed)
    {
        if (position == null)
            characterPosition = new Vector2D(0,0);
        else
            characterPosition = new Vector2D(position);

        if (dimensions == null)
            characterDimensions= new Vector2D(0,0);
        else
            characterDimensions = new Vector2D(dimensions);

        if (speed == null)
            characterSpeed = new Vector2D(0,0);
        else
            characterSpeed = new Vector2D(speed);
    }



    public double getXPos() {return characterPosition.x; }
    public double getYPos() {
        return characterPosition.y;
    }
    public double getXDim() {return characterDimensions.x; }
    public double getYDim() {return characterDimensions.y; }

    @Override
    public boolean isFalling() {
        return characterSpeed.y < 0.0;
    }

    public void update(float deltaT)
    {
        //added
        this.prevPosition =characterPosition;

        final double newX = this.characterPosition.x + this.characterSpeed.x * deltaT;
        final double newY = this.characterPosition.y + this.characterSpeed.y * deltaT;
        this.characterPosition.setXY(newX, newY);

    }

    public Vector2D getPrevPosition(){
        return this.prevPosition;
    }

    public boolean isFlying(){
        return characterSpeed.y != 0.0;
    }



    public boolean checkCollision(final Character en) {
        final boolean heroXLeft = characterPosition.x + characterDimensions.x < en.characterPosition.x;
        final boolean heroXRight = characterPosition.x > en.characterPosition.x + en.characterDimensions.x;
        final boolean heroYDown = characterPosition.y + characterDimensions.y < en.characterPosition.y;
        final boolean heroYUp = characterPosition.y > en.characterPosition.y + en.characterDimensions.y;

        return !(heroXLeft || heroXRight || heroYUp || heroYDown);
    }

    public void setYPos(double YPos) {
        this.characterPosition.y = YPos;
    }
    public void setXPos(final double XPos) {
        this.characterPosition.x = XPos;
    }
}
