package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.gameGUI.GameMenus;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

/**
 * Thread to create a menu instance.
 */
public class MenuCreaterThread extends Thread{

    MenuManager menuManager;
    GameMenus menu;

    public MenuCreaterThread(MenuManager menuManager, GameMenus menu){
        this.menuManager = menuManager;
        this.menu = menu;
    }

    @Override
    public void run(){
        Gdx.app.postRunnable(
             new Runnable() {
                 @Override
                public void run() {
                   menu.createInstance(menuManager);
                }
           }
        );
    }
}
