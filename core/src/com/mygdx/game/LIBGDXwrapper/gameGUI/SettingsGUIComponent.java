package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

public class SettingsGUIComponent extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;


    public SettingsGUIComponent(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);

        elements = new HashMap<String, Object>();
        table = new Table();
        table.setFillParent(true);
        table.pad(DeviceConstants.MENU_VIEWPORT/10
                ,DeviceConstants.MENU_VIEWPORT/10
                ,DeviceConstants.MENU_VIEWPORT/10
                ,DeviceConstants.MENU_VIEWPORT/10
        );
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
        SettingsGUIWidgetsProperties mainGUIWidgetsProperties = ((SettingsGUIWidgetsProperties)widgetsProperties);

        //elements.put("playButton", mainGUIWidgetsProperties.loadPropertiesPlayButton(table));
        //table.row();
        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        SettingsGUIWidgetsInput mainGUIWidgetsInput = ((SettingsGUIWidgetsInput)widgetsInput);

        /*mainGUIWidgetsInput.loadInputPlayButton(
                (Button)elements.get("playButton"),
                menuManager
        );*/
    }

    @Override
    public String toString(){
        return "Menu";
    }
}
