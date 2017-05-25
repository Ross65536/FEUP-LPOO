package com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures;

import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Recharger;

public interface LightRechargerFeature {
    /**
     * Returns the recharger item's info.
     * @return
     */
    Recharger getItemInfo();

    /**
     * Updates the recharers properties with the delta time given.
     * @param deltaT
     */
    void updateRechargerItem(float deltaT);

    /**
     * Returns the Light around the recharger item.
     * @return
     */
    Light getItemLight();
}
