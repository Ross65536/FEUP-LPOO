package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.MyGame;

public class MenuManager {

    private MyGame game;

    public static enum GameMenus {MainMenu(MainGUI.class),PlayGUI(PlayGUI.class)/*TODO*/,LASTVALUEMARKER(null);
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
