package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Input.KeyboardInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MainMenu;

import java.util.HashMap;

public class GameScreen extends ScreenAdapter {
    private MyGame game;
    private OrthographicCamera gameCamera;
    private com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelI currentLevel;
    private GameSettings gameSettings;

    private static enum GameHUDID {MENU("Menu"), INGAME("InGame"), PAUSE("Pause"), SETTINGS("Settings"), HIGH_SCORES("HighScore"), PLAY_MENU("PlayMenu");
        private String string;

        GameHUDID(String name){string = name;}
        @Override public String toString(){
            return string;
        }
    };

    private GameHUDID gameHUDID;
    HashMap<String,Stage> menuDisplays;

    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;

        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;


        gameHUDID = GameHUDID.MENU;
        menuDisplays = new HashMap<String, Stage>();
        loadGUIs();
        registerInputHandler();
    }

    public void LoadLevel(com.mygdx.game.LIBGDXwrapper.LevelAdapters.LevelI currentLevel)
    {
        this.currentLevel = currentLevel;
        currentLevel.setupCamera(gameCamera);
    }

    private void loadGUIs() {
        Stage mainMenu = new MainMenu();
        Gdx.input.setInputProcessor(mainMenu);
        menuDisplays.put("Menu", mainMenu);
        //TODO
    }

    Stage getMenuStage(){
        if(gameHUDID != GameHUDID.INGAME)
            return menuDisplays.get(gameHUDID.toString());
        return null;
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
    }

    private void registerInputHandler() {
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
        Stage stage;
        if((stage=getMenuStage())!= null)
            stage.getViewport().update(width, height, true);
    }


}
