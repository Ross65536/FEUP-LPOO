package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

import static com.mygdx.game.LIBGDXwrapper.gameGUI.MainGUIComponet.ComponentsNames.*;

public class MainGUIComponet extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;

    protected enum ComponentsNames {PLAYBUTTON,HIGHSCOREBUTTON,SETTINGSBUTTON,ABOUTBUTTON};

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

    }

    protected void loadWidgets(){

        MainGUIWidgetsProperties mainGUIWidgetsProperties = ((MainGUIWidgetsProperties)widgetsProperties);

        elements.put(PLAYBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesPlayButton(table,skin));
        table.row();
        elements.put(HIGHSCOREBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesHighScoreButton(table,skin));
        table.row();
        elements.put(SETTINGSBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesSettingsButton(table,skin));
        table.row();
        elements.put(ABOUTBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesAboutButton(table,skin));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        MainGUIWidgetsInput mainGUIWidgetsInput = ((MainGUIWidgetsInput)widgetsInput);

        mainGUIWidgetsInput.loadInputPlayButton(
                (Button)elements.get(PLAYBUTTON.toString()),
                menuManager
        );

        mainGUIWidgetsInput.loadInputHighScoreButton(
                (Button)elements.get(HIGHSCOREBUTTON.toString()),
                menuManager
        );

        mainGUIWidgetsInput.loadInputSettingsButton(
                (Button)elements.get(SETTINGSBUTTON.toString()),
                menuManager
        );

        mainGUIWidgetsInput.loadInputAboutButton(
                (Button)elements.get(ABOUTBUTTON.toString()),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }


}
