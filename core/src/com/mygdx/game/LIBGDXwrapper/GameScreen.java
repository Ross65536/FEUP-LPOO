package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.Input.GyroscopeInput;
import com.mygdx.game.LIBGDXwrapper.Input.KeyboardInput;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.DiscGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.PlatGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.HUD;
import com.mygdx.game.gameLogic.Vector2D;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private IGameWorldAdapter currentLevel;
    private GameSettings gameSettings;
    private GyroscopeInput gyroscopeInput = null;
    private StretchViewport viewport;

    private InputMultiplexer input;

    public static enum GameMode{PLATAFORMS, DODGING};
    ///////HUD////////
    private HUD hud;

    final static double MIN_JUMP_GRAVITY_STRENGTH = 0.5;

    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;
        viewport = new StretchViewport(0,0,gameCamera);


        registerInputHandler();
    }

    public void LoadLevel(IGameWorldAdapter currentLevel)
    {
        this.currentLevel = currentLevel;
        currentLevel.setCamera(gameCamera);
        final Vector2D camDims = currentLevel.getCameraSetup();

        ////////HUD////////
        hud = new HUD(camDims,game);
        registerHUDInput(hud);

        viewport.setWorldSize((float)camDims.x,(float)camDims.y);
        viewport.update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        gameCamera.setToOrtho(false, (float) camDims.x, (float) camDims.y); //camera has maximum world height
    }

    public void setAsInput(){
        Gdx.input.setInputProcessor(input);
    }

    ///////HUD////////
    private void registerHUDInput(HUD hud){
        if(input.size()>1)
            input.removeProcessor(0);
        input.addProcessor(0,hud);
        this.setAsInput();
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

    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        if(currentLevel!= null)
            currentLevel.resize(width, height);

        this.viewport.update(width,height,false);
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
        if(currentLevel instanceof DiscGameWorldAdapter){
            return GameMode.DODGING;
        }else
            if(currentLevel instanceof PlatGameWorldAdapter){
                return GameMode.PLATAFORMS;
            }
        return null;
    }
}