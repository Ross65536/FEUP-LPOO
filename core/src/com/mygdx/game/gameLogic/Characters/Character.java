package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.Vector2D;

public abstract class Character implements CharacterInfo {
    protected Vector2D characterPosition;
    protected Vector2D characterDimensions;
    protected Vector2D characterSpeed;
    protected Vector2D prevPosition;
    protected double animationTime; //goes from 0.0 to 1.0

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

        animationTime= 0.0;

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
        animationTime += deltaT;
        if (animationTime > 1.0) //loop animation time
            animationTime -= 1.0;

        //added
        this.prevPosition = new Vector2D(characterPosition);

        final double newX = this.characterPosition.x + this.characterSpeed.x * deltaT;
        final double newY = this.characterPosition.y + this.characterSpeed.y * deltaT;
        this.characterPosition.setXY(newX, newY);

    }

    public double getAnimationTime()
    {
        return animationTime;
    }

    public Vector2D getPrevPosition(){
        return this.prevPosition;
    }

    public boolean isMovingY()
    {
        return characterSpeed.y != 0.0;
    }

    public boolean isMovingX()
    {
        return characterSpeed.x != 0.0;
    }

    public double getMovementDirectionX()
    {
        return characterSpeed.x;
    }

    public boolean isMovingRight() {
        return getMovementDirectionX() >= 0.0;
    }

    public double getYCenter()
    {
        return characterPosition.y + characterDimensions.y/2.0;
    }

    public double getXCenter() { return characterPosition.x + characterDimensions.x/2.0;};


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
