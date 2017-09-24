package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter;


import com.badlogic.gdx.graphics.OrthographicCamera;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.IGameWorldHeroInputs;

/**
 * Interface that represents different logic world wrappers
 */
public interface IGameWorldAdapter {
    /**
     * Updates the camera, which should display the action correctly
     * @param hero hero informaiton
     * @param gameCamera camera
     */
    void updateCameraPos(ros.joao.rjtorcher.gameLogic.Characters.CharacterInfo hero, OrthographicCamera gameCamera);
    /**
     *
     * @return dimensiosn of the camera, which enable to setup the camera correctly
     */
    ros.joao.rjtorcher.Vector2D getCameraSetup ();
    /**
     * Updates the world state
     * @param deltaT
     */
    void updateWorld(float deltaT);
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
    void setHUD(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.HUD hud);
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
