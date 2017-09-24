package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class PauseGUIWidgetsInput extends WidgetsInput {

    public void loadInputResumeButton(Button button, final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.getGame().SwicthToGameScreen(ros.joao.rjtorcher.LIBGDXwrapper.MyGame.GameInstr.RESUME);
            }
        });
    }

    public void loadInputRestartButton(Button button, final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.getGame().SwicthToGameScreen(ros.joao.rjtorcher.LIBGDXwrapper.MyGame.GameInstr.RESTART);
            }
        });
    }

    public void loadInputSettingsButton(Button button, final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus.SettingsGUI);
            }
        });
    }

    public void loadInputExitButton(Button button, final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.getGame().SwicthToMenuScreen(ros.joao.rjtorcher.LIBGDXwrapper.MyGame.MenuInstr.EXIT);
            }
        });
    }


}
