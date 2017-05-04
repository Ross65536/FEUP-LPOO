package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

public class MainGUIComponet extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;

    public MainGUIComponet(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);

        elements = new HashMap<String, Object>();
        table = new Table();
        table.setFillParent(true);
        this.addActor(table);
        this.setViewport(
                new CustomViewport(
                        (int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO)
                        ,(int)(DeviceConstants.MENU_VIEWPORT)
                )
        );
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(DeviceConstants.MENU_VIEWPORT *  DeviceConstants.INVERTED_SCREEN_RATIO), (int)(DeviceConstants.MENU_VIEWPORT));

        ((OrthographicCamera) this.getCamera()).rotate(90);
        ((OrthographicCamera) this.getCamera()).update();

        loadWidgets();

        table.setDebug(true);
    }

    protected void loadWidgets(){

        MainGUIWidgetsProperties mainGUIWidgetsProperties = ((MainGUIWidgetsProperties)widgetsProperties);

        elements.put("playButton", mainGUIWidgetsProperties.loadPropertiesPlayButton(table));
        table.row();
        elements.put("hightScoreButton", mainGUIWidgetsProperties.loadPropertiesHighScoreButton(table));
        table.row();
        elements.put("settingsButton", mainGUIWidgetsProperties.loadPropertiesSettingsButton(table));
        table.row();
        elements.put("exitButton", mainGUIWidgetsProperties.loadPropertiesExitButton(table));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        MainGUIWidgetsInput mainGUIWidgetsInput = ((MainGUIWidgetsInput)widgetsInput);

        mainGUIWidgetsInput.loadInputPlayButton(
                (Button)elements.get("playButton"),
                menuManager
        );

        mainGUIWidgetsInput.loadInputHighScoreButton(
                (Button)elements.get("hightScoreButton"),
                menuManager
        );

        mainGUIWidgetsInput.loadInputSettingsButton(
                (Button)elements.get("settingsButton"),
                menuManager
        );

        mainGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get("exitButton"),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }


}
