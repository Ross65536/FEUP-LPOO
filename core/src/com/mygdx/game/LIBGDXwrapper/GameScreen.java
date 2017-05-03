package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Input.KeyboardInput;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelI currentLevel;
    private GameSettings gameSettings;

    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;


        registerInputHandler();
    }

    public void LoadLevel(com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelI currentLevel)
    {
        this.currentLevel = currentLevel;
        currentLevel.setupCamera(gameCamera);
    }


    public void sendHeroXMovement (double d) {
        if (currentLevel != null)
            currentLevel.setHeroXMovement(d); }

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

    public void sendHeroJump(double d) {
        if (currentLevel != null)
            currentLevel.setHeroJump(d);
    }
}