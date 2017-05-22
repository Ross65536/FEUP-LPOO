package com.mygdx.game.gameLogic.Characters;


public interface EnemyInfo extends CharacterInfo
{
    /**
     *
     * @return an int which corresponds to the index in some arrays to the appropriate data for each class type
     */
    int getArrayIndex();
    boolean isBossType();
}
