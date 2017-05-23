package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
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
        table = new Table(skin);

        elements = new HashMap<String, Object>();
        table.setFillParent(true);
        this.addActor(table);


        double viewportWidth = (DeviceConstants.MENU_VIEWPORT);
        double viewportHeight = (DeviceConstants.MENU_VIEWPORT*DeviceConstants.INVERTED_SCREEN_RATIO);

        this.setViewport(
                new StretchViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)viewportWidth, (int)viewportHeight);

        loadWidgets();

        table.setDebug(true);
    }

    protected void loadWidgets(){

        MainGUIWidgetsProperties mainGUIWidgetsProperties = ((MainGUIWidgetsProperties)widgetsProperties);

        elements.put("playButton", mainGUIWidgetsProperties.loadPropertiesPlayButton(table,skin));
        table.row();
        elements.put("hightScoreButton", mainGUIWidgetsProperties.loadPropertiesHighScoreButton(table,skin));
        table.row();
        elements.put("settingsButton", mainGUIWidgetsProperties.loadPropertiesSettingsButton(table,skin));
        table.row();
        elements.put("aboutButton", mainGUIWidgetsProperties.loadPropertiesAboutButton(table,skin));

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

        mainGUIWidgetsInput.loadInputAboutButton(
                (Button)elements.get("aboutButton"),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }


}
