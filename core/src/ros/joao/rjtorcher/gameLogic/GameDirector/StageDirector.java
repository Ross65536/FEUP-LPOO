package ros.joao.rjtorcher.gameLogic.GameDirector;

import ros.joao.rjtorcher.gameLogic.GameDirector.DifficultyCurves.Curve;

/**
 * A "bridge" class, that takes a difficulty form a generator and generates an enemy corresponding to that difficulty
 */
public class StageDirector {

    private final Curve difficultyGenerator; //generatos a difficulty based on various statistics
    private ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.Statistics gameStatistics;
    private ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes.EnemyTypes enemyTypes;

    public StageDirector (Curve difficultyGenerator, ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.Statistics gameStatistics, ros.joao.rjtorcher.gameLogic.GameDirector.EnemyTypes.EnemyTypes enemyTypes)
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
    public ros.joao.rjtorcher.gameLogic.Characters.Enemy tryGenerateEnemy ()
    {
        double difficulty = difficultyGenerator.generateDifficulty(gameStatistics);

        if (difficulty == Curve.CURVES_NO_ENEMY_CREATED)
            return null;
        else {
            ros.joao.rjtorcher.gameLogic.Characters.Enemy enemy = enemyTypes.chooseEnemyType(difficulty);
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

    public ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.StatisticsInput getStatsticsInput() {
        return gameStatistics;
    }

}
