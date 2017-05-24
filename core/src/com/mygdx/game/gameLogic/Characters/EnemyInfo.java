package com.mygdx.game.gameLogic.Characters;

/**
 * Provides the interface to provide information aobut the enemy
 */
public interface EnemyInfo extends CharacterInfo
{
    /**
     *
     * @return an int which corresponds to the index in some arrays to the appropriate data for each class type
     */
    int getArrayIndex();

    /**
     * Boss enemies are special enemies of their class that are faster/bigger
     * @return true if enemy if of a boss type
     */
    boolean isBossType();
}
