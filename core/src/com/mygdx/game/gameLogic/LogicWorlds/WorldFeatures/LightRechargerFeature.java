package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Recharger;

public interface LightRechargerFeature {
    Recharger getItemInfo();
    void updateRechargerItem(float deltaT);
    Light getItemLight();
}
