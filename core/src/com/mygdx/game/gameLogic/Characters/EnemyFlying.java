package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Vector2D;

public class EnemyFlying extends Enemy {


    public EnemyFlying(Vector2D position, Vector2D dimensions, Vector2D speed) {
        super(position, dimensions, speed);
    }

    @Override
    public String getAssociatedImagePath() {
        //TODO
        return PathConstants.ENEMY_GROUND_IMAGE_PATH;
    }

    @Override
    public boolean isFlyingType() {
        return true;
    }
}
