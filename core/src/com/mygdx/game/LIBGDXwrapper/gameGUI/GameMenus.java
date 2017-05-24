package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;

public enum GameMenus {

    MainMenu(MainGUI.class),

    PlayGUI(PlayGUI.class),

    SettingsGUI(SettingsGUI.class),

    PauseGUI(PauseGUI.class)/*TODO*/,

    LASTVALUEMARKER(null);

    private Class<? extends AbstractGUI> menuType;
    GameMenus(Class<? extends AbstractGUI> menuType){
        this.menuType = menuType;
    }

    AbstractGUI menu = null;

    public AbstractGUI createInstance(MenuManager menuManager){
        if(menu!=null) {
            return menu;
        }
        if(menuType == null || (menuType ==  SettingsGUI.class)||(menuType ==  PauseGUI.class)){
            return null;
        }
        try {
            menu = menuType.getDeclaredConstructor(MenuManager.class).newInstance(menuManager);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor with MenuManager as only argument in class.");
            return null;
        }
        return menu;
    }

    public AbstractGUI openPauseMenu(MenuManager menuManager, IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        if(!menuType.equals(PauseGUI.class))
            return null;
        if(menu!=null){
            if((((PauseGUI)menu).getGameScreen() == gameScreen) && (((PauseGUI)menu).getPauseType() == pauseType)){
                ((PauseGUI)menu).update();
                return menu;
            }
        }
        try{
            menu = ((Class<PauseGUI>) menuType).getDeclaredConstructor(MenuManager.class,IGameWorldAdapter.class, PauseGUI.pauseType.class).newInstance(menuManager,gameScreen, pauseType);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor with MenuManager as only argument in class.");
            return null;
        }
        return menu;
    }

    public AbstractGUI openSettings(MenuManager menuManager, AbstractGUI currentMenu){
        if(!menuType.equals(SettingsGUI.class) || currentMenu == null)
            return null;
        if(menu!=null){
            if(((SettingsGUI)menu).getBackgroundGUI() == currentMenu){
                return menu;
            }
        }
        try {
            menu = ((Class<SettingsGUI>) menuType).getDeclaredConstructor(MenuManager.class,AbstractGUI.class).newInstance(menuManager,currentMenu);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor with MenuManager as only argument in class.");
            return null;
        }
        return menu;
    }
};