package com.mygdx.game.gameLogic;

public class Vector2D
{
    public double x;
    public double y;
    public Vector2D (double x, double y)  { this.x =x; this.y= y; }
    public Vector2D (Vector2D vector) { this.x = vector.x; this.y = vector.y; }

    public Vector2D getVector2D() { return new Vector2D(x,y); }

    public void setXY(double newX, double newY) {
        this.x = newX; this.y = newY;
    }
}
