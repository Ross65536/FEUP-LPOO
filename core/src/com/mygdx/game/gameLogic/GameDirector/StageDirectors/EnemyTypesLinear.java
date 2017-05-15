package com.mygdx.game.gameLogic.GameDirector.StageDirectors;

import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;

public class EnemyTypesLinear implements IEnemyTypes {
    public final double enemyGroundCuttoff;
    public final double enemyFlyingCuttoff;

    public EnemyTypesLinear(final double groundCuttoff, final double flyingCuttoff)
    {
        enemyGroundCuttoff = Curves.CURVES_MAX_DIFFICULTY * groundCuttoff;
        enemyFlyingCuttoff = Curves.CURVES_MAX_DIFFICULTY * flyingCuttoff;
    }

    public final Enemy get(StageDirector itself, final double difficulty)
    {
        if (difficulty <= enemyGroundCuttoff)
            return itself.makeEnemy(EnemyGround.class, difficulty / enemyGroundCuttoff, PathConstants.ENEMY_GROUND_ARRAY_INDEX);
        else if (difficulty <= enemyFlyingCuttoff)
        {
            final double distortion = (difficulty - enemyGroundCuttoff) / (enemyFlyingCuttoff - enemyGroundCuttoff);
            return itself.makeEnemy(EnemyFlying.class, distortion, PathConstants.ENEMY_FLYING_ARRAY_INDEX);
        }
        else
            return null;
    }
}
