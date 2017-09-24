package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;

/**
 * Enum with the listed menus, has methods for theirs creation and other methods.
 */
public enum GameMenus {

    MainMenu(MainGUI.class),

    PlayGUI(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PlayGUI.class),

    SettingsGUI(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI.class),

    PauseGUI(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.class)/*TODO*/,

    AboutGUI(AboutGUI.class),

    LASTVALUEMARKER(null);

    final private Class<? extends AbstractGUI> menuType;
    GameMenus(Class<? extends AbstractGUI> menuType){
        this.menuType = menuType;
    }

    AbstractGUI menu = null;

    public AbstractGUI createInstance(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        if(menu!=null) {
            return menu;
        }
        if(menuType ==  ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI.class ) {
            this.openSettings(menuManager, null);
            return null;
        }
        else if(menuType ==  AboutGUI.class)
        {
            this.openAbout(menuManager, null);
            return null;
        }

        else
            if(menuType ==  ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.class){
                this.openPauseMenu(menuManager,null, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType.PAUSE);
                return null;
            }else
                if(menuType == null){
                    return null;
                }
        try {
            menu = menuType.getDeclaredConstructor(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager.class).newInstance(menuManager);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested. ");
            return null;
        }
        return menu;
    }

    public AbstractGUI openPauseMenu(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager, IGameWorldAdapter gameScreen, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType pauseType){
        if(!menuType.equals(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.class))
            return null;
        if(menu!=null){
            if((((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI)menu).getGameScreen() == gameScreen) && (((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI)menu).getPauseType() == pauseType)){
                ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI)menu).updateScore();
                return menu;
            }else{
                ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI)menu).reciclePauseGUI(gameScreen,pauseType);
                return menu;
            }
        }
        try{
            menu = ((Class<ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI>) menuType).getDeclaredConstructor(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager.class,IGameWorldAdapter.class, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType.class).newInstance(menuManager,gameScreen, pauseType);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested.");
            return null;
        }
        return menu;
    }

    public AbstractGUI openSettings(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager, AbstractGUI currentMenu){
        if(! menuType.equals(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI.class))
            return null;
        else if (currentMenu == null)
            return null;
        if(menu!=null){
            if(((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI)menu).getBackgroundGUI() == currentMenu || ((AboutGUI)menu).getBackgroundGUI() == currentMenu){
                return menu;
            }
            else
            {
                ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI)menu).setBackgroundGUI(currentMenu);
                return menu;
            }
        }
        try {
            menu = ((Class<ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI>) menuType).getDeclaredConstructor(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager.class,AbstractGUI.class).newInstance(menuManager,currentMenu);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested.");
            return null;
        }
        return menu;
    }


    public AbstractGUI openAbout(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager, AbstractGUI currentMenu){
        if(! menuType.equals(AboutGUI.class))
            return null;
        else if (currentMenu == null)
            return null;
        if(menu!=null){
            if(((AboutGUI)menu).getBackgroundGUI() == currentMenu || ((AboutGUI)menu).getBackgroundGUI() == currentMenu){
                return menu;
            }
            else
            {
                ((AboutGUI)menu).setBackgroundGUI(currentMenu);
                return menu;
            }
        }
        try {
            menu = ((Class<AboutGUI>) menuType).getDeclaredConstructor(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager.class,AbstractGUI.class).newInstance(menuManager,currentMenu);
           // menu = new AboutGUI(menuManager, currentMenu);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested.");
            return null;
        }
        return menu;
    }
};