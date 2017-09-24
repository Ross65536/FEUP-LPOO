package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;

/**
 * Thread to create a menu instance.
 */
public class MenuCreaterThread extends Thread{

    ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager;
    ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus menu;

    public MenuCreaterThread(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus menu){
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
