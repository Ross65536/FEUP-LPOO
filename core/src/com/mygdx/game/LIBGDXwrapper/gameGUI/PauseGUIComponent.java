package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

public class PauseGUIComponent extends AbstractSingleStageGUI{
    HashMap<String, Object> elements;

    Table table;

    private double viewportWidth = (DeviceConstants.MENU_VIEWPORT);
    private double viewportHeight = (DeviceConstants.MENU_VIEWPORT*DeviceConstants.INVERTED_SCREEN_RATIO);

    private double xPos = viewportWidth/10f;
    private double yPos = viewportHeight/10f;
    private double width = viewportWidth*8f/10f;
    private double height = viewportHeight*8f/10f;


    public PauseGUIComponent(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);

        table = new Table(skin);

        elements = new HashMap<String, Object>();

        this.addActor(table);

        skin.add("pauseBackgroundImage",new Texture(Gdx.files.internal("pauseBackgroundImage.png")));

        table.setBackground("pauseBackgroundImage");
        table.setPosition((float)xPos,(float)yPos);
        table.setSize((float)width,(float)height);

        this.setViewport(
                new FitViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)viewportWidth, (int)viewportHeight);

        loadWidgets();

        table.setDebug(true);
    }


    protected void loadWidgets(){
        PauseGUIWidgetsProperties pauseGUIWidgetsProperties = ((PauseGUIWidgetsProperties)widgetsProperties);

        pauseGUIWidgetsProperties.loadCurrentScoreLabel(table, skin,"Paused");

        table.row();

        pauseGUIWidgetsProperties.loadCurrentScoreLabel(table, skin,"Score: ");

        table.row();

        elements.put("resume",pauseGUIWidgetsProperties.loadPMButton( table, skin, "resumeButton"));

        elements.put("restart",pauseGUIWidgetsProperties.loadPMButton( table, skin, "restartButton"));

        elements.put("settings",pauseGUIWidgetsProperties.loadPMButton( table, skin, "settingsPMButton"));

        elements.put("exit",pauseGUIWidgetsProperties.loadPMButton( table, skin, "exitPMButton"));

        loadInputlisteners();
    }


    protected void loadInputlisteners(){

        PauseGUIWidgetsInput pauseGUIWidgetsInput = ((PauseGUIWidgetsInput)widgetsInput);

        pauseGUIWidgetsInput.loadInputResumeButton(
                (Button)elements.get("resume"),
                menuManager
        );

        pauseGUIWidgetsInput.loadInputRestartButton(
                (Button)elements.get("restart"),
                menuManager
        );


        pauseGUIWidgetsInput.loadInputSettingsButton(
                (Button)elements.get("settings"),
                menuManager
        );


        pauseGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get("exit"),
                menuManager
        );

    }


}
