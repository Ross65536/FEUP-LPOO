package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PlayGUI;

public class SettingsGUIWidgetsInput extends WidgetsInput {

    public void loadInputExitButton(Button button, final MenuManager menuManager, final Class<? extends ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AbstractGUI> backGUI){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backGUI.equals(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MainGUI.class))
                    menuManager.setMenu(GameMenus.MainMenu);
                if(backGUI.equals(PlayGUI.class))
                    menuManager.setMenu(GameMenus.PlayGUI);
                if(backGUI.equals(PauseGUI.class)){
                    menuManager.setMenu(GameMenus.PauseGUI);
                }
            }
        });
    }

}