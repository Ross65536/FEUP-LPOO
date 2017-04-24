package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;

import java.util.HashMap;

public class MainGUIComponet extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;

    public MainGUIComponet(MenuManager menuManager){
        super(menuManager);

        elements = new HashMap<String, Object>();
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

        elements.put("playButton", MainGUIWidgetsProperties.loadPropertiesPlayButton(table));
        table.row();
        elements.put("hightScoreButton", MainGUIWidgetsProperties.loadPropertiesHighScoreButton(table));
        table.row();
        elements.put("settingsButton", MainGUIWidgetsProperties.loadPropertiesSettingsButton(table));
        table.row();
        elements.put("exitButton", MainGUIWidgetsProperties.loadPropertiesExitButton(table));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){
        MainGUIWidgetsInput.loadInputPlayButton(
                (Button)elements.get("playButton"),
                menuManager
        );

        MainGUIWidgetsInput.loadInputHighScoreButton(
                (Button)elements.get("hightScoreButton"),
                menuManager
        );

        MainGUIWidgetsInput.loadInputSettingsButton(
                (Button)elements.get("settingsButton"),
                menuManager
        );

        MainGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get("exitButton"),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }
}
