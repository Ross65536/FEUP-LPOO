package ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures;


/**
 * Interface for the light recharger item world feature.
 */
public interface LightRechargerFeature {
    /**
     * Returns the recharger item's info.
     * @return
     */
    ros.joao.rjtorcher.gameLogic.Characters.Recharger getItemInfo();

    /**
     * Updates the recharers properties with the delta time given.
     * @param deltaT
     */
    void updateRechargerItem(float deltaT);

    /**
     * Returns the Light around the recharger item.
     * @return
     */
    ros.joao.rjtorcher.gameLogic.Characters.Light getItemLight();
}
