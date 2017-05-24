package com.mygdx.game.gameLogic.LogicWorlds;


import com.mygdx.game.gameLogic.Characters.HeroInfo;

/**
 * Interface that provides tha basic methods to control a logic world
 */
public interface IGameWorld {
    /**
     *
     * @return an object which contains information about the hero in the logic world.
     */
    HeroInfo getHeroInfo();

    /**
     * Updates the internal state of the object
     * @param deltaT time in seconds
     */
    void update (float deltaT);
    /**
     *
     * @return an object which allows the hero to be controlled by the user
     */
    IGameWorldHeroInputs getWorldInputs();
}
