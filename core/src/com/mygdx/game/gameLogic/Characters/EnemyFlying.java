package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.CommonConsts;
import com.mygdx.game.Vector2D;

/**
 * class that implements ground based enemies
 */
public class EnemyFlying extends Enemy {


    public EnemyFlying(Vector2D position, Vector2D dimensions, Vector2D speed, boolean bossEn) {
        super(position, dimensions, speed, bossEn);
    }


    @Override
    public boolean isFlyingType() {
        return true;
    }

    @Override
    public int getArrayIndex() {
        return CommonConsts.ENEMY_FLYING_ARRAY_INDEX;
    }



}
