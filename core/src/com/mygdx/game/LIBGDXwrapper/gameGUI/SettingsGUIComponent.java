package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
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

    SettingsGUI settingsGUI;

    public SettingsGUIComponent(MenuManager menuManager, SettingsGUI settingsGUI){
        super(menuManager, settingsGUI.widgetsProperties, settingsGUI.widgetsInput);
        this.settingsGUI = settingsGUI;
        elements = new HashMap<String, Object>();
        table = new Table();
        this.addActor(table);
        table.setFillParent(true);

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
        SettingsGUIWidgetsProperties settingsGUIWidgetsProperties = ((SettingsGUIWidgetsProperties)widgetsProperties);

        elements.put("exitButton", settingsGUIWidgetsProperties.loadPropertiesExitButton(table));
        //table.row();
        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        SettingsGUIWidgetsInput settingsGUIWidgetsInput = ((SettingsGUIWidgetsInput)widgetsInput);

        settingsGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get("exitButton"),
                menuManager,
                settingsGUI.getBackgroundGUI().getClass()
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }


    @Override
    public void draw(){
        super.draw();

    }
}
