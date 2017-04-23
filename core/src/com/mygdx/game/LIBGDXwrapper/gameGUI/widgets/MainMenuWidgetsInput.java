package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.Settings;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

public class MainMenuWidgetsInput {
    static public void loadInputPlayButton(Button playButton,final MenuManager menuManager){
         playButton.addListener(new ChangeListener() {
             @Override
             public void changed(ChangeEvent event, Actor actor) {
                 menuManager.getGame().SwicthToGameScreen(MyGame.GameInstr.START);
             }
         });
    }

    static public void loadInputHighScoreButton(Button highScoreButton,final MenuManager menuManager){
        highScoreButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }
}
