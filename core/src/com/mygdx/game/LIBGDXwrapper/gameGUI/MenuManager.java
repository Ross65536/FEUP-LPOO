package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.MyGame;

public class MenuManager {


    private MyGame game;

    public AbstractGUI currentMenu;

    public void setMenu(GameMenus menuTypeEnum){
        if(menuTypeEnum.equals(GameMenus.SettingsGUI)){
            currentMenu = menuTypeEnum.openSettings(this, currentMenu);
        }else
            currentMenu = menuTypeEnum.createInstance(this);

        this.setInputProcessor();
        resize(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
    }

    public void pauseGame(){
        //TODO
        //this.setMenu(GameMenus.PauseInGame);
    }

    public void backToMenu(){
        //TODO
        //this.setMenu(GameMenus.PlayMenu);
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
