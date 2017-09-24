package ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes;

import ros.joao.rjtorcher.gameLogic.GameDirector.DifficultyCurves.Curve;

/**
 * turns difficulty into specific enemies
 */
public class EnemyTypesLinear extends EnemyTypes {
    private final double groundCuttoffDiff;
    private final double flyingCuttoffDiff;
    private final double groundBossCuttoffDiff;
    private final double flyingBossCuttoffDiff;

    public EnemyTypesLinear(final double dimYScaler, final double groundCuttoff, final double flyingCuttoff, final double groundBossCuttoff, final double flyingBossCuttoff)
    {
        super(dimYScaler);
        this.groundCuttoffDiff = Curve.CURVES_MAX_DIFFICULTY * groundCuttoff;
        this.flyingCuttoffDiff = Curve.CURVES_MAX_DIFFICULTY * flyingCuttoff;
        this.groundBossCuttoffDiff = Curve.CURVES_MAX_DIFFICULTY * groundBossCuttoff;
        this.flyingBossCuttoffDiff = Curve.CURVES_MAX_DIFFICULTY * flyingBossCuttoff;

    }


    /**
     * creates ground and flying enemies, either normal, or boss type
     * @param difficulty
     * @return
     */
    public final ros.joao.rjtorcher.gameLogic.Characters.Enemy chooseEnemyType(final double difficulty)
    {
        final int bossArrayPush = ros.joao.rjtorcher.CommonConsts.ENEMY_ARRAY_SIZE;

        if (difficulty < 0.0 || difficulty > flyingBossCuttoffDiff)
            return null;
        else if (difficulty <= groundCuttoffDiff)
            return makeEnemy(ros.joao.rjtorcher.gameLogic.Characters.EnemyGround.class, difficulty / groundCuttoffDiff, ros.joao.rjtorcher.CommonConsts.ENEMY_GROUND_ARRAY_INDEX);
        else if (difficulty <= flyingCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, groundCuttoffDiff, flyingCuttoffDiff);
            return makeEnemy(ros.joao.rjtorcher.gameLogic.Characters.EnemyFlying.class, distortion, ros.joao.rjtorcher.CommonConsts.ENEMY_FLYING_ARRAY_INDEX);
        }
        else if (difficulty <= groundBossCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, flyingCuttoffDiff, groundBossCuttoffDiff);
            return makeEnemy(ros.joao.rjtorcher.gameLogic.Characters.EnemyGround.class, distortion, ros.joao.rjtorcher.CommonConsts.ENEMY_GROUND_ARRAY_INDEX + bossArrayPush );
        }
        else if (difficulty <= flyingBossCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, groundBossCuttoffDiff, flyingBossCuttoffDiff);
            return makeEnemy(ros.joao.rjtorcher.gameLogic.Characters.EnemyFlying.class, distortion, ros.joao.rjtorcher.CommonConsts.ENEMY_FLYING_ARRAY_INDEX + bossArrayPush);
        }

        return null;
    }

    private static double getDistortion(final double difficulty, final double baseCuttoff, final double topCuttoff) {
        return (difficulty - baseCuttoff) / (topCuttoff - baseCuttoff);
    }
}
