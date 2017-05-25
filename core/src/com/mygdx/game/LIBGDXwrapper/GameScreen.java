package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.Input.GyroscopeInput;
import com.mygdx.game.LIBGDXwrapper.Input.KeyboardInput;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.SurvivalGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.PlatGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.HUD;
import com.mygdx.game.Vector2D;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private IGameWorldAdapter currentLevel;
    private GameSettings gameSettings;
    private GyroscopeInput gyroscopeInput = null;
    private StretchViewport viewport;
    private HUD hud;
    private InputMultiplexer input;

    public static enum GameMode{PLATAFORMS, DODGING};

    final static double MIN_JUMP_GRAVITY_STRENGTH = 0.5;

    /**
     * Constrcutor for the class.
     * creates a camera for the display.
     * Registers an input handler for the screen
     * @param myGame
     * @param gameSettings
     */
    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;
        viewport = new StretchViewport(0,0,gameCamera);
        viewport.setCamera(gameCamera);


        registerInputHandler();
    }

    /**
     * Loads a gameworld
     * @param currentLevel gameworld object
     */
    public void LoadLevel(IGameWorldAdapter currentLevel)
    {
        this.currentLevel = currentLevel;
        currentLevel.setCamera(gameCamera);
        final Vector2D camDims = currentLevel.getCameraSetup();

        createHUD(camDims);

        viewport.setWorldSize((float)camDims.x,(float)camDims.y);
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        gameCamera.setToOrtho(false, (float) camDims.x, (float) camDims.y); //camera has maximum world height
    }


    private void createHUD(Vector2D camDims){
        hud = new HUD(camDims,game);
        registerHUDInput(hud);
        currentLevel.setHUD(hud);
    }

    private void registerHUDInput(HUD hud){
        if(input.size()>1)
            input.removeProcessor(0);
        input.addProcessor(0,hud);
    }

    /**
     * Start of Periodic function.
     *
     * @param deltaT in seconds
     */
    @Override
    public void render(float deltaT) {

        currentLevel.updateWorld(deltaT);

        if(!gameSettings.noMotionSensors() && gyroscopeInput!=null){
            gyroscopeInput.update(deltaT);
        }

        super.render(deltaT);

        currentLevel.updateScreen(deltaT);

        ////////HUD////////
        hud.act(deltaT);
        hud.draw();
    }

    /**
     * makes the hero jump onscreen
     * @param strength indicates the "strength" of the jump, can go from 0.0 to around 0.5
     */
    public void sendHeroJump(double strength) {
        if (currentLevel != null)
        {
            double gravityStrength = 1.0 - strength;

            if (gravityStrength < MIN_JUMP_GRAVITY_STRENGTH)
                gravityStrength = MIN_JUMP_GRAVITY_STRENGTH;

            else if (gravityStrength > 1.0)
                gravityStrength = 1.0;

            currentLevel.getLogicWorldInputs().heroJump(gravityStrength);
        }
    }

    /**
     * Moves the hero on the horizontal axis.
     * mov is multiplied by the maximum poosible speed of the hero to get the horizontal speed of the hero
     *
     * @param xMov goes from -1.0 to 1.0, negative being movement to the left and postive to the right, 0.0 stops hero's horintal movement.
     */
    public void sendHeroXMovement (double xMov) {
        if (currentLevel != null)
        {
            if (xMov < -1.0)
                xMov = -1.0;
            else if (xMov > 1.0)
                xMov = 1.0;

            currentLevel.getLogicWorldInputs().moveHeroHorizontal(xMov);
        }
    }

    /**
     * Registers the input for the screen. For andorid should register sensor input, for desktop should only register keyboard and mouse
     */
    public void registerInputHandler() {
        input = new InputMultiplexer();
        if (gameSettings.noMotionSensors()) //use keyboard if on desktop
        {
            KeyboardInput keyboardInput = new KeyboardInput(this);
            input.addProcessor(keyboardInput);
        }
        else //gyroscope input
            if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass)){
                gyroscopeInput = new GyroscopeInput(this);

                KeyboardInput keyboardInput = new KeyboardInput(this); //for touch
                input.addProcessor(keyboardInput);
            }

        this.setAsInput();
    }

    public void setAsInput(){
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        if(currentLevel!= null)
            currentLevel.resize(width, height);

        this.viewport.update(width,height,false);

        this.hud.getCamera().update();
        this.hud.getViewport().update(width,height,true);
    }

    public IGameWorldAdapter getCurrentLevel(){
        return currentLevel;
    }

    public void nullifyLevel(){
        if(currentLevel!=null){
            currentLevel.dispose();
            currentLevel = null;
        }
        System.gc();
    }


    public GameMode whatGameMode(){
        if(currentLevel instanceof SurvivalGameWorldAdapter){
            return GameMode.DODGING;
        }else
            if(currentLevel instanceof PlatGameWorldAdapter){
                return GameMode.PLATAFORMS;
            }
        return null;
    }

    @Override
    public void dispose(){
        if(currentLevel!=null)
            currentLevel.dispose();
    }
}