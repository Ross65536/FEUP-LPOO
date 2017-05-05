package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

public class PlayGUIComponent1 extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;

    public PlayGUIComponent1(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);

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

        PlayGUIWidgetsProperties playGUIWidgetsProperties = ((PlayGUIWidgetsProperties)widgetsProperties);

        elements.put("backToMenu", playGUIWidgetsProperties.loadBackToMenuButton(table));
        elements.put("settings", playGUIWidgetsProperties.loadSettingsButton(table));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        PlayGUIWidgetsInput playGUIWidgetsInput = ((PlayGUIWidgetsInput)widgetsInput);

        playGUIWidgetsInput.loadSettingsButton(
            (Button)elements.get("settings"),
            menuManager
        );

        playGUIWidgetsInput.loadBackToMenuButton(
            (Button)elements.get("backToMenu"),
            menuManager
        );



    }

    @Override
    public String toString(){
        return "Menu";
    }
}
