package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.PathConstants;
import com.mygdx.game.Vector2D;

public class EnemyGround extends Enemy {

    public EnemyGround (final Vector2D position, final Vector2D dimensions, final Vector2D speed)
    {
        super(position, dimensions, speed);
    }

    @Override
    public boolean isFlyingType() {
        return false;
    }

    @Override
    public int getArrayIndex() {
        return PathConstants.ENEMY_GROUND_ARRAY_INDEX;
    }

    @Override
    public boolean isBossType() {
        return false;
    }
}
