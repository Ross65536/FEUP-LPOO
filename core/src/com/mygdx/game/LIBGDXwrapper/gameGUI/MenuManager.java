package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.MyGame;

public class MenuManager {

    private MyGame game;

    public static enum GameMenus {MainMenu(MainGUI.class),PlayGUI(PlayGUI.class),/*TODO*/;
        private Class<? extends AbstractGUI> menuType;
        GameMenus(Class<? extends AbstractGUI> menuType){
            this.menuType = menuType;
        }
        public Class<? extends AbstractGUI> getType(){
            return menuType;
        }

        public AbstractGUI createInstance(MenuManager menuManager){
            AbstractGUI newgui;
            try {
                newgui = menuType.getDeclaredConstructor(MenuManager.class).newInstance(menuManager);
            }catch (Exception e){
                System.out.println(e.toString());
                System.out.println("No constructor with MenuManager as only argument in class.");
                return null;
            }
            return newgui;
        }
    };

    private AbstractGUI currentMenu;

    public void setMenu(GameMenus menuTypeEnum){
        currentMenu = null;
        System.gc();
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

    public void nullifyMenu(){
        currentMenu = null;
        System.gc();
    }

    public final MyGame getGame(){
        return game;
    }

    public void resize(int width, int height){
        currentMenu.updateViewPorts(width, height,true);
    }
}
