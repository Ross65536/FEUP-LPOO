package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.EnemyInfo;

import java.util.List;

/**
 * Created by João on 11/05/2017.
 */

public interface DummyEnemyFeature {
    void createDummyEnemies ();
    void updateEnemieStatistics(float deltaT);
    long checkEnemyCollisions();
    void tryGenerateEnemy();
    List<EnemyInfo> getEnemiesInfo();
}
