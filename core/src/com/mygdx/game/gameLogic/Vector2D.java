package com.mygdx.game.gameLogic;

public class Vector2D
{
    private double x;
    private double y;
    public Vector2D (double x, double y)  { this.x =x; this.y= y; }
    public Vector2D (Vector2D vector) { this.x = vector.x; this.y = vector.y; }
    public double getX() { return x;}
    public double getY() { return y;}
    public Vector2D getVector2D() { return new Vector2D(x,y); }

    public void setX(double x) {
        this.x = x;
    }
    public void setXY (double x, double y)
    {
        this.x=x;
        this.y=y;
    }
}
