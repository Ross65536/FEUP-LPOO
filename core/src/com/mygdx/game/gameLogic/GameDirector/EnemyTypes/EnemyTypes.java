package com.mygdx.game.gameLogic.GameDirector.EnemyTypes;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.Characters.EnemyGround;

/**
 * Base class for classes that convert difficulty into enemies
 */
public abstract class EnemyTypes {
    private final double measurementUnit; //should be hero.YDim

    /**
     * constructor
     * @param measurementUnit should be dimension Y oh hero character
     */
    public EnemyTypes(double measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    protected void makeParameters(Vector2D dimensions, Vector2D speed, double distortFraction, int arrayIndex)
    {
        final CommonConsts.CharacterConstants enConsts = CommonConsts.getEnemyConstants(arrayIndex);
        final double dimDistort = enConsts.dimYPadding;
        final double enYDim = (enConsts.dimYMult + dimDistort * distortFraction) * measurementUnit;
        final double enXDim = enYDim * enConsts.aspectRatio;
        final double speedDistort = enConsts.speedXPadding;
        final double enXSpeed = (enConsts.speedMult + speedDistort * distortFraction) * measurementUnit ;

        dimensions.setXY(enXDim, enYDim);
        speed.setXY(enXSpeed, 0);
    }

    protected Enemy makeEnemy (final Class<?> enType, final double distortFraction, final int arrayIndex)
    {
        boolean bossEn = false;
        if (arrayIndex >= CommonConsts.ENEMY_ARRAY_SIZE)
            bossEn = true;

        Vector2D dimensions = new Vector2D();
        Vector2D speed = new Vector2D();
        makeParameters(dimensions, speed, distortFraction, arrayIndex);

        if (enType == EnemyGround.class)
            return new EnemyGround(null, dimensions, speed, bossEn);
        else if (enType == EnemyFlying.class)
            return new EnemyFlying(null, dimensions, speed, bossEn);

        throw new IndexOutOfBoundsException("tryGenerateEnemy missing a generation class");
    }

    /**
     * Creates the enemy form difficulty
     * @param difficulty
     * @return
     */
    public abstract Enemy chooseEnemyType(final double difficulty);
}
