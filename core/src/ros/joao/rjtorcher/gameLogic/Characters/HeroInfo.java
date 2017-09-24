package ros.joao.rjtorcher.gameLogic.Characters;

/**
 * Interface to query hero specific information
 */
public interface HeroInfo extends CharacterInfo {
    /**
     *
     * @return true if hero is jumping, false otherwise
     */
    boolean isJumping();


}
