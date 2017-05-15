package com.mygdx.game.gameLogic.GameDirector.StageDirectors;

import com.mygdx.game.CommonConsts;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;

public class EnemyTypesLinear implements IEnemyTypes {
    private final double groundCuttoffDiff;
    private final double flyingCuttoffDiff;
    private final double groundBossCuttoffDiff;
    private final double flyingBossCuttoffDiff;

    public EnemyTypesLinear(final double groundCuttoff, final double flyingCuttoff, final double groundBossCuttoff, final double flyingBossCuttoff)
    {
        this.groundCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * groundCuttoff;
        this.flyingCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * flyingCuttoff;
        this.groundBossCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * groundBossCuttoff;
        this.flyingBossCuttoffDiff = Curves.CURVES_MAX_DIFFICULTY * flyingBossCuttoff;

    }



    public final Enemy get(StageDirector itself, final double difficulty)
    {
        final int bossArrayPush = CommonConsts.ENEMY_ARRAY_SIZE;

        if (difficulty <= groundCuttoffDiff)
            return itself.makeEnemy(EnemyGround.class, difficulty / groundCuttoffDiff, CommonConsts.ENEMY_GROUND_ARRAY_INDEX);
        else if (difficulty <= flyingCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, groundCuttoffDiff, flyingCuttoffDiff);
            return itself.makeEnemy(EnemyFlying.class, distortion, CommonConsts.ENEMY_FLYING_ARRAY_INDEX);
        }
        else if (difficulty <= groundBossCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, flyingCuttoffDiff, groundBossCuttoffDiff);
            return itself.makeEnemy(EnemyGround.class, distortion, CommonConsts.ENEMY_GROUND_ARRAY_INDEX + bossArrayPush );
        }
        else if (difficulty <= flyingBossCuttoffDiff)
        {
            final double distortion = getDistortion(difficulty, groundBossCuttoffDiff, flyingBossCuttoffDiff);
            return itself.makeEnemy(EnemyFlying.class, distortion, CommonConsts.ENEMY_FLYING_ARRAY_INDEX + bossArrayPush);
        }
        else
            return null;
    }

    private static double getDistortion(final double difficulty, final double baseCuttoff, final double topCuttoff) {
        return (difficulty - baseCuttoff) / (topCuttoff - baseCuttoff);
    }
}
