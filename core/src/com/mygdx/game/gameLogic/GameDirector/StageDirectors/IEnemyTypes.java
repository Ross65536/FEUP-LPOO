package com.mygdx.game.gameLogic.GameDirector.StageDirectors;

import com.mygdx.game.gameLogic.Characters.Enemy;


public interface IEnemyTypes {
    Enemy get(StageDirector itself, final double difficulty);
}
