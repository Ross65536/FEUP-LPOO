package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Input.GyroscopeInput;
import com.mygdx.game.Input.KeyboardInput;
import com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelI;
import com.mygdx.game.LIBGDXwrapper.gameGUI.CustomViewport;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private Viewport viewport;
    private LevelI currentLevel;
    private GameSettings gameSettings;
    private GyroscopeInput gyroscopeInput = null;
    public GameScreen(MyGame myGame, GameSettings gameSettings) {


        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;

        viewport =  new FillViewport(
                (int)(DeviceConstants.MENU_VIEWPORT)
                ,(int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO)

        );
        viewport.setCamera(gameCamera);

        registerInputHandler();
    }

    public void LoadLevel(LevelI currentLevel)
    {
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        this.currentLevel = currentLevel;
        currentLevel.setupCamera(gameCamera);
    }


    public void sendHeroXMovement (double d) {
        if (currentLevel != null)
            currentLevel.setHeroXMovement(d); }

    /**
     * Start of Periodic function.
     *
     * @param deltaT
     */
    @Override
    public void render(float deltaT) {
        super.render(deltaT);

        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        currentLevel.update(deltaT, gameCamera);

        if(!gameSettings.isKeyboardControlled() && gyroscopeInput!=null){
            gyroscopeInput.update(deltaT);
        }

    }

    public void registerInputHandler() {
        if (gameSettings.isKeyboardControlled()) //use keyboard if on desktop
        {
            KeyboardInput keyboardInput = new KeyboardInput(this);
            Gdx.input.setInputProcessor(keyboardInput);
        }
        else //gyroscope input
        if(Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass)){
            gyroscopeInput = new GyroscopeInput(this);
        }

    }

    @Override
    public void resize(int width, int height){

        super.resize(width, height);

    }


    public void nullifyLevel(){
        currentLevel = null;
        System.gc();
    }
}