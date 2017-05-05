package com.mygdx.game.gameLogic.LogicWorlds;


import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;

import java.util.List;

public interface IGameWorld {
    HeroInfo getHeroInfo();
    List<EnemyInfo> getEnemiesInfo();
    void update (float deltaT);
    IGameWorldHeroInputs getWorldInputs();
}
