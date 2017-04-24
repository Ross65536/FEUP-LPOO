package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

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

    static float xPos = -1;
    static float yPos = -1;

    static public void loadSlideFunction(Table table, final MenuManager menuManager, final OrthographicCamera camera){
        table.addListener(
            new ClickListener(){
                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer){
                    if(xPos == -1 || yPos == -1){
                        xPos = x;
                        yPos = y;
                        return;
                    }
                    camera.translate(x-xPos,0);
                    xPos = x;
                    yPos = y;
                }
            }
        );
    }

}

