package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainMenuWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainMenuWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

public class MainMenu extends AbstractGameMenu {

    Table table;

    Skin skin;

    public MainMenu(MenuManager menuManager){
        super(menuManager);

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

    protected void loadWidgets(){

        skin.add("playButton", MainMenuWidgetsProperties.loadPropertiesPlayButton(table));
        table.row();
        skin.add("hightScoreButton", MainMenuWidgetsProperties.loadPropertiesHighScoreButton(table));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){
        MainMenuWidgetsInput.loadInputPlayButton(
                skin.get("playButton", Button.class),
                menuManager
        );

        MainMenuWidgetsInput.loadInputHighScoreButton(
                skin.get("hightScoreButton", Button.class),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }
}
