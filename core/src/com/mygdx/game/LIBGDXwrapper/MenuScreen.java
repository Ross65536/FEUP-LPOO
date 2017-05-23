package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;
import com.mygdx.game.LIBGDXwrapper.gameGUI.PauseGUI;

import static com.mygdx.game.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO;
import static com.mygdx.game.LIBGDXwrapper.DeviceConstants.SCREEN_RATIO;

public class MenuScreen extends ScreenAdapter {

    private MyGame game;

    private MenuManager menuManager;

    public MenuScreen(MyGame game){
        this.game = game;
        this.menuManager = new MenuManager(game);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(202 / 255f, 149 / 255f, 96 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        menuManager.update(delta);
    }

    public void backToMenu(){
        menuManager.backToMenu();
    }

    public void pauseGame(IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        menuManager.pauseGame(gameScreen, pauseType);
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        menuManager.resize(width, height);

    }
}
