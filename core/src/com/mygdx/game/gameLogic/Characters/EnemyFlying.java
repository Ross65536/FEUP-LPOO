package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.PathConstants;
import com.mygdx.game.Vector2D;

public class EnemyFlying extends Enemy {


    public EnemyFlying(Vector2D position, Vector2D dimensions, Vector2D speed) {
        super(position, dimensions, speed);
    }


    @Override
    public boolean isFlyingType() {
        return true;
    }

    @Override
    public int getArrayIndex() {
        return PathConstants.ENEMY_FLYING_ARRAY_INDEX;
    }

    @Override
    public boolean isBossType() {
        return true;
    }
}
