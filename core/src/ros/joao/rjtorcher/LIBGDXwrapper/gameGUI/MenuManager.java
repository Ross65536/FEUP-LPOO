package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;

import java.util.EnumMap;

/**
 * Manages all menus. Opens any menu.
 */
public class MenuManager {

    private ros.joao.rjtorcher.LIBGDXwrapper.MyGame game;

    public AbstractGUI currentMenu = null;

    EnumMap<GameMenus, Thread> threads;

    /**
     * Sets the menu to the one specified in the argument.
     * @param menuTypeEnum the menu type.
     */
    public void setMenu(GameMenus menuTypeEnum){
        waitForMenu(menuTypeEnum);
        switch(menuTypeEnum){
            case AboutGUI:
                currentMenu = menuTypeEnum.openAbout(this, currentMenu);
                break;
            case SettingsGUI:
                currentMenu = menuTypeEnum.openSettings(this, currentMenu);
                break;
            default:
                currentMenu = menuTypeEnum.createInstance(this);
                break;
        }
        this.setInputProcessor();
        resize(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
    }

    /**
     * Waits for the menu to be made.
     * @param menuTypeEnum Memu type.
     */
    private void waitForMenu(GameMenus menuTypeEnum){
        if(!threads.containsKey(menuTypeEnum))
            return;
        if(!threads.get(menuTypeEnum).isAlive())
            return;
        try{
            threads.get(menuTypeEnum).join();
        }catch (InterruptedException ex){
            System.out.println(ex.getMessage());
            return;
        }
    }


    /**
     * Pauses the game.
     * @param gameScreen The gameScreenAdapter
     * @param pauseType The pause type (Pause, or ending of game)
     */
    public void pauseGame(IGameWorldAdapter gameScreen, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType pauseType){
        currentMenu = GameMenus.PauseGUI.openPauseMenu(this, gameScreen, pauseType);
        this.setInputProcessor();
        resize(Gdx.graphics.getWidth() ,Gdx.graphics.getHeight());
    }

    /**
     * Return to the main menu.
     */
    public void backToMenu(){
        this.setMenu(GameMenus.PlayGUI);
    }


    /**
     * Constructor.
     * @param game The game.
     */
    public MenuManager(ros.joao.rjtorcher.LIBGDXwrapper.MyGame game){
        threads = new EnumMap<GameMenus, Thread>(GameMenus.class);
        this.game = game;
        this.setMenu(GameMenus.MainMenu);
    }

    /**
     * Update mehtod.
     * @param delta Delta time.
     */
    public void update(float delta){
        currentMenu.act(delta);
        currentMenu.draw();
    }

    /**
     * Sets the current menu the input processor.
     */
    public void setInputProcessor(){
        Gdx.input.setInputProcessor(currentMenu);
    }

    /**
     * Returns the game.
     * @return
     */
    public final ros.joao.rjtorcher.LIBGDXwrapper.MyGame getGame(){
        return game;
    }

    /**
     * Resizes the current menu.
     * @param width Screen Width
     * @param height Screen Height.
     */
    public void resize(int width, int height){
        if(currentMenu instanceof ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PlayGUI)
            currentMenu.updateViewPorts(width, height,false);
        else
            currentMenu.updateViewPorts(width, height,true);
    }

    /**
     * Pre-loads all menus.
     */
    public void loadAllMenus(){
        for (GameMenus menu : GameMenus.values()) {
            if(menu != GameMenus.MainMenu) {
                Thread menuCThread = new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuCreaterThread(this, menu);
                menuCThread.setPriority(Thread.MIN_PRIORITY);
                menuCThread.start();

                threads.put(menu, menuCThread);
            }
        }
    }
}
