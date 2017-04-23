package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MainMenu;

import java.util.HashMap;

public class GameScreen extends ScreenAdapter {
    public final static float MENU_VIEWPORT = 1250;

    private OrthographicCamera gameCamera;
    private MyGame game;
    private LevelI currentLevel;
    private GameSettings gameSettings;
    private AssetManager gameAssetManager;
    public final static double SCREEN_RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());



    public GameScreen(MyGame myGame, GameSettings gameSettings) {
        this.game = myGame;
        gameAssetManager = new AssetManager(new InternalFileHandleResolver());
        gameCamera = new OrthographicCamera();
        this.gameSettings = gameSettings;
        this.currentLevel = null;

        inputHandler();
    }

    public void LoadLevel(LevelI currentLevel)
    {
        if(this.currentLevel != null) //destroys textures od previous level
            this.currentLevel.unloadLevelAssets(gameAssetManager);

        currentLevel.loadLevelAssets(gameAssetManager);
        this.currentLevel = currentLevel;
        currentLevel.setupCamera(gameCamera);
        currentLevel.finishLoadingAssets(gameAssetManager);
    }





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

    private void inputHandler() {

    }


    public void nullifyLevel(){
        this.currentLevel = null;
    }


    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }
}
