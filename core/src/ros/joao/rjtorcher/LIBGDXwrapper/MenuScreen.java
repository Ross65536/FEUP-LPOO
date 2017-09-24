package ros.joao.rjtorcher.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI;

/**
 * Screen Adapter for the menu.
 */
public class MenuScreen extends ScreenAdapter {

    private MyGame game;

    private MenuManager menuManager;

    public MenuScreen(MyGame game){
        this.game = game;
        this.menuManager = new MenuManager(game);
        render(0.1f);
        this.menuManager.loadAllMenus();
    }

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(202 / 255f, 149 / 255f, 96 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        menuManager.update(delta);
    }

    public void backToMenu(){
        menuManager.backToMenu();
    }

    public void pauseGame(IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        menuManager.pauseGame(gameScreen, pauseType);
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        menuManager.resize(width, height);

    }
}
