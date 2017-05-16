package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.Vector2D;

public class EnemyGround extends Enemy {

    public EnemyGround(final Vector2D position, final Vector2D dimensions, final Vector2D speed, boolean bossEn)
    {
        super(position, dimensions, speed, bossEn);
    }

    @Override
    public boolean isFlyingType() {
        return false;
    }

    @Override
    public int getArrayIndex() {
        return CommonConsts.ENEMY_GROUND_ARRAY_INDEX;
    }


}
