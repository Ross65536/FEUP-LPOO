package ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes;

import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.gameLogic.Characters.EnemyFlying;

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

    protected void makeParameters(ros.joao.rjtorcher.Vector2D dimensions, ros.joao.rjtorcher.Vector2D speed, double distortFraction, int arrayIndex)
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

    protected ros.joao.rjtorcher.gameLogic.Characters.Enemy makeEnemy (final Class<?> enType, final double distortFraction, final int arrayIndex)
    {
        boolean bossEn = false;
        if (arrayIndex >= CommonConsts.ENEMY_ARRAY_SIZE)
            bossEn = true;

        ros.joao.rjtorcher.Vector2D dimensions = new ros.joao.rjtorcher.Vector2D();
        ros.joao.rjtorcher.Vector2D speed = new ros.joao.rjtorcher.Vector2D();
        makeParameters(dimensions, speed, distortFraction, arrayIndex);

        if (enType == ros.joao.rjtorcher.gameLogic.Characters.EnemyGround.class)
            return new ros.joao.rjtorcher.gameLogic.Characters.EnemyGround(null, dimensions, speed, bossEn);
        else if (enType == EnemyFlying.class)
            return new EnemyFlying(null, dimensions, speed, bossEn);

        throw new IndexOutOfBoundsException("tryGenerateEnemy missing a generation class");
    }

    /**
     * Creates the enemy form difficulty
     * @param difficulty
     * @return
     */
    public abstract ros.joao.rjtorcher.gameLogic.Characters.Enemy chooseEnemyType(final double difficulty);
}
