package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

public abstract class Enemy extends Character implements EnemyInfo {
    final boolean isBossType;
    public Enemy(Vector2D position, Vector2D dimensions, Vector2D speed, boolean bossEn) {
        super(position, dimensions, speed);
        this.isBossType = bossEn;
    }

    public void setMovementDirection(final boolean bRight) {
        final double xSpeed = Math.abs(this.characterSpeed.x);

        if (bRight)
            this.characterSpeed.x = xSpeed;
        else
            this.characterSpeed.x = - xSpeed;
    }

    @Override
    public boolean isBossType() {
        return isBossType;
    }

    abstract public boolean isFlyingType();
}
