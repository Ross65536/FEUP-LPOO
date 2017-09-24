package ros.joao.rjtorcher.gameLogic.Characters;

import ros.joao.rjtorcher.CommonConsts;

/**
 * class that implements flying type enemies
 */
public class EnemyGround extends Enemy {

    public EnemyGround(final ros.joao.rjtorcher.Vector2D position, final ros.joao.rjtorcher.Vector2D dimensions, final ros.joao.rjtorcher.Vector2D speed, boolean bossEn)
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
