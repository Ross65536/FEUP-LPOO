package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.LIBGDXwrapper.Input.GyroscopeInput;
import com.mygdx.game.LIBGDXwrapper.Input.KeyboardInput;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.gameLogic.Vector2D;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private IGameWorldAdapter currentLevel;
    private GameSettings gameSettings;
    private Viewport viewport;
    private GyroscopeInput gyroscopeInput = null;

    final static double MIN_JUMP_GRAVITY_STRENGTH = 0.5;

    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;

        viewport =  new FitViewport(
                (int)(DeviceConstants.MENU_VIEWPORT)
                ,(int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO)

        );
        viewport.setCamera(gameCamera);

        registerInputHandler();
    }

    public void LoadLevel(IGameWorldAdapter currentLevel)
    {
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        this.currentLevel = currentLevel;
        final Vector2D camDims = currentLevel.getCameraSetup();
        gameCamera.setToOrtho(false, (float) camDims.x, (float) camDims.y); //camera has maximum world height

    }

    /**
     * Start of Periodic function.
     *
     * @param deltaT in seconds
     */
    @Override
    public void render(float deltaT) {
        super.render(deltaT);
        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);


        currentLevel.update(deltaT, gameCamera);
        if(!gameSettings.noMotionSensors() && gyroscopeInput!=null){
            gyroscopeInput.update(deltaT);
        }
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

    public void registerInputHandler() {
        if (gameSettings.noMotionSensors()) //use keyboard if on desktop
        {
            KeyboardInput keyboardInput = new KeyboardInput(this);
            Gdx.input.setInputProcessor(keyboardInput);
        }
        else //gyroscope input
            if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass)){
                gyroscopeInput = new GyroscopeInput(this);

                KeyboardInput keyboardInput = new KeyboardInput(this); //for touch
                Gdx.input.setInputProcessor(keyboardInput);
            }

    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        if(currentLevel!= null)
            currentLevel.resize(width, height);
    }


    public void nullifyLevel(){
        if(currentLevel!=null){
            currentLevel.dispose();
            currentLevel = null;
        }
        System.gc();
    }
}