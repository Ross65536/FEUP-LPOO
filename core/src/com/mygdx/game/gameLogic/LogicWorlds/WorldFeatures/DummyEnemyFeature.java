package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.EnemyInfo;

import java.util.List;


public interface DummyEnemyFeature {
    /**
     * Creates the enemies
     */
    void createDummyEnemies ();

    /**
     * Updates the enemu statistics.
     * @param deltaT
     */
    void updateEnemieStatistics(float deltaT);

    /**
     * Checks for enemy colosions.
     * @return
     */
    long checkEnemyCollisions();

    /**
     * tries to generate an enemy.
     */
    void tryGenerateEnemy();

    /**
     * Returns information regarging all enemmies.
     * @return information regarging all enemmies.
     */
    List<EnemyInfo> getEnemiesInfo();
}
