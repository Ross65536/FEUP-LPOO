package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.LIBGDXwrapper.GameSettings;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.Settings;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

public class MenuScreen extends ScreenAdapter {

    private MyGame game;

    private MenuManager menuManager;

    public MenuScreen(MyGame game){
        this.game = game;
        this.menuManager = new MenuManager(game);
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(15 / 255f, 45 / 255f, 113 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        menuManager.update(delta);
    }

    public void backToMenu(){
        menuManager.backToMenu();
    }

    public void nullifyMenu(){
        menuManager.backToMenu();
    }

    public void pauseGame(){
        menuManager.pauseGame();
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        menuManager.resize(width, height);
    }
}