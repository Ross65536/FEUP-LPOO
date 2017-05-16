package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.Vector2D;

public abstract class Character extends Entity implements CharacterInfo {
    protected Vector2D characterSpeed;
    protected Vector2D prevPosition;
    protected double animationTime; //goes from 0.0 to 1.0

    public Character (final Vector2D position, final Vector2D dimensions, final Vector2D speed)
    {
        super(position, dimensions);

        if (speed == null)
            characterSpeed = new Vector2D(0,0);
        else
            characterSpeed = new Vector2D(speed);

        animationTime= 0.0;

    }


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

}
