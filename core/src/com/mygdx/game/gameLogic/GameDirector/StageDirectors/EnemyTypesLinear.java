package com.mygdx.game.gameLogic.GameDirector.StageDirectors;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;

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
        this.groundCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * groundCuttoff;
        this.flyingCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * flyingCuttoff;
        this.groundBossCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * groundBossCuttoff;
        this.flyingBossCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * flyingBossCuttoff;

    }


    /**
     * creates ground and flying enemies, either normal, or boss type
     * @param difficulty
     * @return
     */
    public final Enemy get(final double difficulty)
    {
        final int bossArrayPush = CommonConsts.ENEMY_ARRAY_SIZE;

        if (difficulty < 0.0 || difficulty > flyingBossCuttoffDiff)
            return null;
        else if (difficulty <= groundCuttoffDiff)
            return makeEnemy(EnemyGround.class, difficulty / groundCuttoffDiff, CommonConsts.ENEMY_GROUND_ARRAY_INDEX);
        else if (difficulty <= flyingCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, groundCuttoffDiff, flyingCuttoffDiff);
            return makeEnemy(EnemyFlying.class, distortion, CommonConsts.ENEMY_FLYING_ARRAY_INDEX);
        }
        else if (difficulty <= groundBossCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, flyingCuttoffDiff, groundBossCuttoffDiff);
            return makeEnemy(EnemyGround.class, distortion, CommonConsts.ENEMY_GROUND_ARRAY_INDEX + bossArrayPush );
        }
        else if (difficulty <= flyingBossCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, groundBossCuttoffDiff, flyingBossCuttoffDiff);
            return makeEnemy(EnemyFlying.class, distortion, CommonConsts.ENEMY_FLYING_ARRAY_INDEX + bossArrayPush);
        }

        return null;
    }

    private static double getDistortion(final double difficulty, final double baseCuttoff, final double topCuttoff) {
        return (difficulty - baseCuttoff) / (topCuttoff - baseCuttoff);
    }
}
