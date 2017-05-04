package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Input.KeyboardInput;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameWorldAdapter;
import com.mygdx.game.gameLogic.Vector2D;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private GameWorldAdapter currentLevel;
    private GameSettings gameSettings;

    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;

        registerInputHandler();
    }

    public void LoadLevel(GameWorldAdapter currentLevel)
    {
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
    }

    public void sendHeroJump(double d) {
        if (currentLevel != null)
            currentLevel.sendHeroJump(d);
    }

    public void sendHeroXMovement (double d) {
        if (currentLevel != null)
            currentLevel.sendHeroXMovement(d); }

    public void registerInputHandler() {
        if (gameSettings.isKeyboardControlled()) //use keyboard if on desktop
        {
            KeyboardInput keyboardInput = new KeyboardInput(this);
            Gdx.input.setInputProcessor(keyboardInput);
        }
        else //gyroscope input
        {

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