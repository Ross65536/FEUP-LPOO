package com.mygdx.game.gameLogic.LogicWorlds;


import com.badlogic.gdx.math.Matrix4;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;

import java.util.List;

public interface IGameWorld {
    HeroInfo getHeroInfo();
    void update (float deltaT);
    IGameWorldHeroInputs getWorldInputs();
}
