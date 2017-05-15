package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

public abstract class Enemy extends Character implements EnemyInfo {
    public Enemy(Vector2D position, Vector2D dimensions, Vector2D speed) {
        super(position, dimensions, speed);
    }

    public void setMovementDirection(final boolean bRight) {
        final double xSpeed = Math.abs(this.characterSpeed.x);

        if (bRight)
            this.characterSpeed.x = xSpeed;
        else
            this.characterSpeed.x = - xSpeed;
    }


    abstract public boolean isFlyingType();
}
