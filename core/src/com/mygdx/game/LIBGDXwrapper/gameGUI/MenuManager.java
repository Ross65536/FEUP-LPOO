package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.GameScreen;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;

public class MenuManager {


    private MyGame game;

    public AbstractGUI currentMenu;

    public void setMenu(GameMenus menuTypeEnum){
        switch(menuTypeEnum){
            case SettingsGUI:
                currentMenu = menuTypeEnum.openSettings(this, currentMenu);
                break;
            default:
                currentMenu = menuTypeEnum.createInstance(this);
                break;
        }
        this.setInputProcessor();
        resize(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
    }

    public void pauseGame(IGameWorldAdapter gameScreen){
        currentMenu = GameMenus.PauseGUI.openPauseMenu(this, gameScreen);
        this.setInputProcessor();
        resize(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
    }

    public void backToMenu(){
        this.setMenu(GameMenus.PlayGUI);
    }


    public MenuManager(MyGame game){
        this.game = game;
        this.setMenu(GameMenus.MainMenu);
    }

    public void update(float delta){
        currentMenu.act(delta);
        currentMenu.draw();
    }

    public void setInputProcessor(){
        Gdx.input.setInputProcessor(currentMenu);
    }

    public final MyGame getGame(){
        return game;
    }

    public void resize(int width, int height){

        currentMenu.updateViewPorts(width, height,false);
    }
}
