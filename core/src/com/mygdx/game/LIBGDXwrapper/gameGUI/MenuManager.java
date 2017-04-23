package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.MyGame;

public class MenuManager {

    private MyGame game;

    private static enum GameMenus {MainMenu(MainMenu.class),/*TODO*/;
        private Class<? extends AbstractGameMenu> menuType;
        GameMenus(Class<? extends AbstractGameMenu> menuType){
            this.menuType = menuType;
        }
        public Class<? extends AbstractGameMenu> getType(){
            return menuType;
        }

        public AbstractGameMenu createInstance(MenuManager menuManager){
            menuType = MainMenu.class;
            AbstractGameMenu newgui;
            try {
                newgui = menuType.getDeclaredConstructor(MenuManager.class).newInstance(menuManager);
            }catch (Exception e){
                System.out.println("No constructor with settings as only argument in class.");
                return null;
            }
            return newgui;
        }
    };

    private AbstractGameMenu currentMenu;

    private void setMenu(GameMenus menuTypeEnum){
        currentMenu = null;
        System.gc();
        currentMenu = menuTypeEnum.createInstance(this);
        this.setInputProcessor();
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
        currentMenu.getViewport().update(width, height,true);
    }
}
