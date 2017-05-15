package com.mygdx.game.gameLogic.Characters;

public interface CharacterInfo {
    double getXPos();
    double getYPos();
    double getXDim();
    double getYDim();
    boolean isFalling();
    boolean isMovingY();
    boolean isMovingX();
    double getAnimationTime();
    double getMovementDirectionX();
    boolean isMovingRight();
    double getYCenter();
    double getXCenter();
}
