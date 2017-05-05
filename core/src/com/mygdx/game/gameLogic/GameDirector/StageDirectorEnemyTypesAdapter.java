package com.mygdx.game.gameLogic.GameDirector;

import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;

public class StageDirectorEnemyTypesAdapter {
    //// tier lists ----------
    public interface IEnemyTypes
    {
        Enemy op(StageDirector itself, final double difficulty);
    }

    public static final double ENEMY_GROUND_CUTTOFF = Curves.CURVES_MAX_DIFFICULTY;
    static public final Enemy testEnemyTypes(StageDirector itself, final double difficulty)
    {
        if (difficulty < ENEMY_GROUND_CUTTOFF)
            return itself.makeEnemy(EnemyGround.class, difficulty / ENEMY_GROUND_CUTTOFF);

        return null;
    }
}
