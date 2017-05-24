package com.mygdx.game.LIBGDXwrapper.gameAdapter;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.LIBGDXwrapper.gameGUI.HUD;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.Vector2D;

/**
 * Interface that represents different logic world wrappers
 */
public interface IGameWorldAdapter {
    /**
     * Updates the camera, which should display the action correctly
     * @param hero hero informaiton
     * @param gameCamera camera
     */
    void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera);
    /**
     *
     * @return dimensiosn of the camera, which enable to setup the camera correctly
     */
    Vector2D getCameraSetup ();
    /**
     * Updates the world state
     * @param deltaT
     */
    void updateWorld(float delaT);
    /**
     * updates the graphics
     * @param deltaT
     */
    void updateGraphics(float deltaT);
    /**
     * Obtains the camera
     * @param camera
     */
    void setCamera(OrthographicCamera camera);
    /**
     *
     * @return objects that can send input data to logic world.
     */
    IGameWorldHeroInputs getLogicWorldInputs();
    /**
     * Resizes the game graphics
     * @param width
     * @param height
     */
    void resize(int width, int height);
    /**
     * Copies the hud
     * @param hud
     */
    void setHUD(HUD hud);
    /**
     * Obtains the score
     * @return
     */
    String getScore();
    /**
     * Dispose the graphics in memory
     */
    void dispose();
}
