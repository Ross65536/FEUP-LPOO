package ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures;

import java.util.List;

/**
 * Interface for the dummy enemy world feature.
 */
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
    List<ros.joao.rjtorcher.gameLogic.Characters.EnemyInfo> getEnemiesInfo();
}
