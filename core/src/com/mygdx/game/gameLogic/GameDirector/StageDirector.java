package com.mygdx.game.gameLogic.GameDirector;

import com.mygdx.game.Constants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.Random;


public class StageDirector {
    private final double measurementUnit; //should be hero.YDim
    private final DifficultyCurves.generator difficultyGenerator; //generatos a difficulty based on various statistics
    private Statistics gameStatistics;

    public StageDirector (DifficultyCurves.generator difficultyGenerator, Statistics gameStatistics, final double dimYScaler)
    {
        this.measurementUnit = dimYScaler;
        this.difficultyGenerator = difficultyGenerator;
        this.gameStatistics = gameStatistics;
    }

    //// utilities ------------
    private void makeParameters (final Class<?> enType, Vector2D dimensions, Vector2D speed)
    {
        final Constants.CharacterConstants enConsts = Constants.getEnemyConstants(enType);

        final double enYDim = enConsts.dimYMult * measurementUnit;
        final double enXDim = enYDim * enConsts.aspectRatio;
        final double enXSpeed = enConsts.speedMult * measurementUnit;

        dimensions.setXY(enXDim, enYDim);
        speed.setXY(enXSpeed, 0);
    }

    private Enemy makeEnemy (final Class<?> enType)
    {
        Vector2D dimensions = new Vector2D();
        Vector2D speed = new Vector2D();
        makeParameters(enType, dimensions, speed);

        if (enType == EnemyGround.class)
            return new EnemyGround(null, dimensions, speed);

        return null;
    }

    //// core -------
    public Enemy tryGenerateEnemy ()
    {
        final StatisticsInfo gameStatisticsInfo = gameStatistics;
        double difficulty = difficultyGenerator.op(gameStatisticsInfo);

        if (difficulty == DifficultyCurves.NO_ENEMY_CREATED)
            return null;
        else if (difficulty <= 100.0)
            return makeEnemy(EnemyGround.class);

        throw new IndexOutOfBoundsException("tryGenerateEnemy missing a generation class");
    }

    public void update(float deltaT) {
    }
}
