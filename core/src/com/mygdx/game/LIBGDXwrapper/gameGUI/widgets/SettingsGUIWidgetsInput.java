package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.LIBGDXwrapper.gameGUI.AbstractGUI;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MainGUI;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;
import com.mygdx.game.LIBGDXwrapper.gameGUI.PlayGUI;

public class SettingsGUIWidgetsInput extends WidgetsInput{

    public void loadInputExitButton(Button button, final MenuManager menuManager, final Class<? extends AbstractGUI> backGUI){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(backGUI.equals(MainGUI.class))
                    menuManager.setMenu(MenuManager.GameMenus.MainMenu);
                if(backGUI.equals(PlayGUI.class))
                    menuManager.setMenu(MenuManager.GameMenus.PlayGUI);
            }
        });
    }

}