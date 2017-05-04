package com.mygdx.game.LIBGDXwrapper.gameGUI;

public enum GameMenus {MainMenu(MainGUI.class),PlayGUI(PlayGUI.class),SettingsGUI(SettingsGUI.class)/*TODO*/,LASTVALUEMARKER(null);
    private Class<? extends AbstractGUI> menuType;
    GameMenus(Class<? extends AbstractGUI> menuType){
        this.menuType = menuType;
    }

    AbstractGUI menu = null;

    private int usage = 0;

    public AbstractGUI createInstance(MenuManager menuManager){

        if(menu!=null)
            return menu;
        try {
            menu = menuType.getDeclaredConstructor(MenuManager.class).newInstance(menuManager);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor with MenuManager as only argument in class.");
            return null;
        }
        updateUsages();
        return menu;
    }

    public AbstractGUI openSettings(MenuManager menuManager, AbstractGUI currentMenu){
        if(!menuType.equals(SettingsGUI.class))
            return null;
        try {
            menu = ((Class<SettingsGUI>) menuType).getDeclaredConstructor(MenuManager.class,AbstractGUI.class).newInstance(menuManager,currentMenu);
        }catch (Exception e){
            System.out.println(e.toString());
            System.out.println("No constructor with MenuManager as only argument in class.");
            return null;
        }
        return menu;
    }

    private void updateUsages(){
        increaseUsage();
        for(GameMenus ms: GameMenus.values()){
            ms.decreasedUsage();
        }
    }

    private void decreasedUsage() {
        if (menu != null) {
            if (usage <= 0) {
                menu = null;
                System.gc();
                usage = 0;
            }
            usage -= 1 / (GameMenus.LASTVALUEMARKER.ordinal() * 2);
        }
    }

    private void increaseUsage(){
        usage= 1 + 1/(GameMenus.LASTVALUEMARKER.ordinal()*2);
    }
};