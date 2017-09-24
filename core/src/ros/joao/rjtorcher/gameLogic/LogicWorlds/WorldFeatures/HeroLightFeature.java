package ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures;


/**
 * Interface for the hero's light world feature.
 */
public interface HeroLightFeature {
    /**
     * Updates the hero's light with delta time.
     * @param deltaT delta time
     */
    void updateLight(float deltaT);

    /**
     * Returns the ligth's information.
     * @return
     */
    ros.joao.rjtorcher.gameLogic.Characters.Light getLightInfo();

    /**
     * Returns the percentage of the current radios form the initial radious of the light.
     * @return percentage
     */
    float getRadiousPercentage();
}
