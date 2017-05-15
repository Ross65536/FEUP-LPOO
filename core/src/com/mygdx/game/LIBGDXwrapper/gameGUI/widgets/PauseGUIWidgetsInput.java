package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameGUI.GameMenus;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

public class PauseGUIWidgetsInput extends WidgetsInput{

    public void loadInputResumeButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.getGame().SwicthToGameScreen(MyGame.GameInstr.RESUME);
            }
        });
    }

    public void loadInputRestartButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.getGame().SwicthToGameScreen(MyGame.GameInstr.RESTART);
            }
        });
    }

    public void loadInputSettingsButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(GameMenus.SettingsGUI);
            }
        });
    }

    public void loadInputExitButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.getGame().SwicthToMenuScreen(MyGame.MenuInstr.EXIT);
            }
        });
    }


}
