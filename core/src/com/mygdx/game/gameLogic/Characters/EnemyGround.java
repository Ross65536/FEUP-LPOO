package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.PathConstants;
import com.mygdx.game.Vector2D;

public class EnemyGround extends Enemy {

    public EnemyGround (final Vector2D position, final Vector2D dimensions, final Vector2D speed)
    {
        super(position, dimensions, speed);
    }

    @Override
    public String getAssociatedImagePath() {
        return PathConstants.ENEMY_GROUND_IMAGE_PATH;
    }

    @Override
    public boolean isFlyingType() {
        return false;
    }
}
