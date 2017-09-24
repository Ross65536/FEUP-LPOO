package ros.joao.rjtorcher.gameLogic.Characters;


import ros.joao.rjtorcher.CommonConsts;

/**
 * class that implements ground based enemies
 */
public class EnemyFlying extends Enemy {


    public EnemyFlying(ros.joao.rjtorcher.Vector2D position, ros.joao.rjtorcher.Vector2D dimensions, ros.joao.rjtorcher.Vector2D speed, boolean bossEn) {
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
