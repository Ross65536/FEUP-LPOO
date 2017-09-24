package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;

/**
 * Enum with the listed menus, has methods for theirs creation and other methods.
 */
public enum GameMenus {

    MainMenu(MainGUI.class),

    PlayGUI(PlayGUI.class),

    SettingsGUI(SettingsGUI.class),

    PauseGUI(PauseGUI.class)/*TODO*/,

    AboutGUI(AboutGUI.class),

    LASTVALUEMARKER(null);

    final private Class<? extends AbstractGUI> menuType;
    GameMenus(Class<? extends AbstractGUI> menuType){
        this.menuType = menuType;
    }

    AbstractGUI menu = null;

    public AbstractGUI createInstance(MenuManager menuManager){
        if(menu!=null) {
            return menu;
        }
        if(menuType ==  SettingsGUI.class ) {
            this.openSettings(menuManager, null);
            return null;
        }
        else if(menuType ==  AboutGUI.class)
        {
            this.openAbout(menuManager, null);
            return null;
        }

        else
            if(menuType ==  PauseGUI.class){
                this.openPauseMenu(menuManager,null, com.mygdx.game.LIBGDXwrapper.gameGUI.PauseGUI.pauseType.PAUSE);
                return null;
            }else
                if(menuType == null){
                    return null;
                }
        try {
            menu = menuType.getDeclaredConstructor(MenuManager.class).newInstance(menuManager);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested. ");
            return null;
        }
        return menu;
    }

    public AbstractGUI openPauseMenu(MenuManager menuManager, IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        if(!menuType.equals(PauseGUI.class))
            return null;
        if(menu!=null){
            if((((PauseGUI)menu).getGameScreen() == gameScreen) && (((PauseGUI)menu).getPauseType() == pauseType)){
                ((PauseGUI)menu).updateScore();
                return menu;
            }else{
                ((PauseGUI)menu).reciclePauseGUI(gameScreen,pauseType);
                return menu;
            }
        }
        try{
            menu = ((Class<PauseGUI>) menuType).getDeclaredConstructor(MenuManager.class,IGameWorldAdapter.class, PauseGUI.pauseType.class).newInstance(menuManager,gameScreen, pauseType);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested.");
            return null;
        }
        return menu;
    }

    public AbstractGUI openSettings(MenuManager menuManager, AbstractGUI currentMenu){
        if(! menuType.equals(SettingsGUI.class))
            return null;
        else if (currentMenu == null)
            return null;
        if(menu!=null){
            if(((SettingsGUI)menu).getBackgroundGUI() == currentMenu || ((AboutGUI)menu).getBackgroundGUI() == currentMenu){
                return menu;
            }
            else
            {
                ((SettingsGUI)menu).setBackgroundGUI(currentMenu);
                return menu;
            }
        }
        try {
            menu = ((Class<SettingsGUI>) menuType).getDeclaredConstructor(MenuManager.class,AbstractGUI.class).newInstance(menuManager,currentMenu);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested.");
            return null;
        }
        return menu;
    }


    public AbstractGUI openAbout(MenuManager menuManager, AbstractGUI currentMenu){
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
            menu = ((Class<AboutGUI>) menuType).getDeclaredConstructor(MenuManager.class,AbstractGUI.class).newInstance(menuManager,currentMenu);
           // menu = new AboutGUI(menuManager, currentMenu);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor like the requested.");
            return null;
        }
        return menu;
    }
};