package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MainGUIWidgetsInput extends WidgetsInput {

    public void loadInputPlayButton(Button button,final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 menuManager.setMenu(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus.PlayGUI);
             }
         });
    }

    public void loadInputHighScoreButton(Button button,final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    public void loadInputSettingsButton(Button button,final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus.SettingsGUI);
            }
        });
    }

    public void loadInputAboutButton(Button button,final ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.GameMenus.AboutGUI);
            }
        });
    }

}
