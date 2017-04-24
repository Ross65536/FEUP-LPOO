package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.Settings;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

public class MainGUIWidgetsInput {

    static public void loadInputPlayButton(Button button,final MenuManager menuManager){
        button.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 menuManager.getGame().SwicthToGameScreen(MyGame.GameInstr.START);
             }
         });
    }

    static public void loadInputHighScoreButton(Button button,final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(MenuManager.GameMenus.PlayGUI);
            }
        });
    }

    static public void loadInputSettingsButton(Button button,final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    static public void loadInputExitButton(Button button,final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

}
