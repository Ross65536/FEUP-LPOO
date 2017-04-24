package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

public class PlayGUIWidgetsInput{

    static public void loadSettingsButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    static public void loadBackToMenuButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(MenuManager.GameMenus.MainMenu);
            }
        });
    }

}
