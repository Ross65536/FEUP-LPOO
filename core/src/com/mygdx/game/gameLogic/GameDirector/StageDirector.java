package com.mygdx.game.gameLogic.GameDirector;

import com.mygdx.game.Constants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.GameDirector.DifficultyCurve.Curves;
import com.mygdx.game.gameLogic.Vector2D;


public class StageDirector {
    private final double measurementUnit; //should be hero.YDim
    private final Curves difficultyGenerator; //generatos a difficulty based on various statistics
    private Statistics gameStatistics;
    private StageDirectorEnemyTypesAdapter.IEnemyTypes enemyTypes;

    public StageDirector (Curves difficultyGenerator, Statistics gameStatistics, final double dimYScaler, StageDirectorEnemyTypesAdapter.IEnemyTypes enemyTypes)
    {
        this.measurementUnit = dimYScaler;
        this.difficultyGenerator = difficultyGenerator;
        this.gameStatistics = gameStatistics;
        this.enemyTypes = enemyTypes;
    }

    //// utilities ------------
    private void makeParameters(final Class<?> enType, Vector2D dimensions, Vector2D speed, double distortFraction)
    {
        final Constants.CharacterConstants enConsts = Constants.getEnemyConstants(enType);
        final double dimDistort = enConsts.dimYPadding;
        final double enYDim = (enConsts.dimYMult + dimDistort * distortFraction) * measurementUnit;
        final double enXDim = enYDim * enConsts.aspectRatio;
        final double speedDistort = enConsts.speedXPadding;
        final double enXSpeed = (enConsts.speedMult + speedDistort * distortFraction) * measurementUnit ;

        dimensions.setXY(enXDim, enYDim);
        speed.setXY(enXSpeed, 0);
    }

    /**
     *
     * @param enType
     * @param distortion should be from 0.0 to 1.0, multiplied with speed and dimensions distrortion
     * @return
     */
    protected Enemy makeEnemy (final Class<?> enType, final double distortFraction)
    {


        Vector2D dimensions = new Vector2D();
        Vector2D speed = new Vector2D();
        makeParameters(enType, dimensions, speed, distortFraction);

        if (enType == EnemyGround.class)
            return new EnemyGround(null, dimensions, speed);
        else if (enType == EnemyFlying.class)
            return new EnemyFlying(null, dimensions, speed);

        throw new IndexOutOfBoundsException("tryGenerateEnemy missing a generation class");
    }

    //// core -------
    public Enemy tryGenerateEnemy ()
    {
        final StatisticsInfo gameStatisticsInfo = gameStatistics;
        double difficulty = difficultyGenerator.generateDifficulty(gameStatisticsInfo);

        if (difficulty == Curves.CURVES_NO_ENEMY_CREATED)
            return null;
        else {
            final Enemy enemy = enemyTypes.op(this, difficulty);
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
