package com.mygdx.game.gameLogic.GameDirector.StageDirectors;

import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;
import com.mygdx.game.gameLogic.GameDirector.Statistics;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInput;

/**
 * A "bridge" class, that takes a difficulty form a generator and generates an enemy corresponding to that difficulty
 */
public class StageDirector {

    private final Curves difficultyGenerator; //generatos a difficulty based on various statistics
    private Statistics gameStatistics;
    private EnemyTypes enemyTypes;

    public StageDirector (Curves difficultyGenerator, Statistics gameStatistics, EnemyTypes enemyTypes)
    {

        this.difficultyGenerator = difficultyGenerator;
        this.gameStatistics = gameStatistics;
        this.enemyTypes = enemyTypes;
    }

    //// core -------

    /**
     * Tries to create an enemy based on the enemy types passed during construction
     * @return the enemu, null, if no enemy is to be created
     */
    public Enemy tryGenerateEnemy ()
    {
        double difficulty = difficultyGenerator.generateDifficulty(gameStatistics);

        if (difficulty == Curves.CURVES_NO_ENEMY_CREATED)
            return null;
        else {
            Enemy enemy = enemyTypes.get(difficulty);
            if (enemy == null)
                throw new IndexOutOfBoundsException("tryGenerateEnemy missing a generation class");
            else
            {
                if (enemy.isFlyingType())
                    gameStatistics.updateNumberOfFlyingEnemies(1);
                else
                    gameStatistics.updateNumberOfGroundEnemies(1); //enemy ground created

                return enemy;
            }
        }
    }

    public StatisticsInput getStatsticsInput() {
        return gameStatistics;
    }

}
