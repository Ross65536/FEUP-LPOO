package ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures;

/**
 * Interface for the hero lifes world feature.
 */
public interface HeroLifesFeature {
    /**
     * Returns the number of lifes left.
     * @return the number of lifes left.
     */
    int getNumberOfLifes();

    /**
     * Returns whether or not the hero is immune to damage.
     * @return whether or not the hero is immune to damage.
     */
    boolean isImmune();

    /**
     * Decreases the lifes of the hero by one.
     * Once the hero's life reaches zero the life's wont go lower.
     * @return Number of lifes left.
     */
    int takeLife();
}
