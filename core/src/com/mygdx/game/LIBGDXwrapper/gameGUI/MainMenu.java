package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

public class MainMenu extends Stage{

    Table table;

    Skin skin;

    public MainMenu(){
        skin = new Skin();
        table = new Table();
        table.setFillParent(true);
        this.addActor(table);
        this.setViewport(
                new FitViewport(
                        (int)(DeviceConstants.MENU_VIEWPORT)
                        ,(int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO)
                )
        );
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(DeviceConstants.MENU_VIEWPORT), (int)(DeviceConstants.MENU_VIEWPORT *  DeviceConstants.INVERTED_SCREEN_RATIO));
        loadWidgets();


        table.setDebug(true);
    }

    private void loadWidgets(){

        skin.add("playButton", com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainMenuWidgets.loadPlayButton(table));
        table.row();
        skin.add("hightScoreButton", com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainMenuWidgets.loadHighScoreButton(table));
        //table.row();
    }
}
