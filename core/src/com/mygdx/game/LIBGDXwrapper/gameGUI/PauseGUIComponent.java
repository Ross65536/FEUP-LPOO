package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

    private String pauseMessage;
    private String pauseScoreMessage;

    IGameWorldAdapter gameScreen;

    public PauseGUIComponent(IGameWorldAdapter gameScreen , MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput, PauseGUI.pauseType pauseType){
        super(menuManager, widgetsProperties, widgetsInput);
        this.gameScreen = gameScreen;
        table = new Table(skin);

        elements = new HashMap<String, Object>();

        this.addActor(table);

        Drawable background = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("pauseBackgroundImg",Drawable.class);

        table.setBackground(background);
        table.setPosition((float)xPos,(float)yPos);
        table.setSize((float)width,(float)height);

        this.setViewport(
                new StretchViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)viewportWidth, (int)viewportHeight);

        preLoadPauseTypeSpecificProperties(pauseType);
        loadWidgets();
        postLoadPauseTypeSpecificProperties(pauseType);
    }

    private void loadLabels( PauseGUIWidgetsProperties pauseGUIWidgetsProperties){
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        table.row().padTop(screenHeight/25);

        pauseGUIWidgetsProperties.loadPMLabel(table, skin,pauseMessage);

        table.row().padTop(screenHeight/25).padBottom(screenHeight/25);

        elements.put("scorelabel",pauseGUIWidgetsProperties.loadPMLabel(table, skin, pauseScoreMessage + "\n" +  ((gameScreen!=null)?this.gameScreen.getScore():0)));
        ((Label)elements.get("scorelabel")).setAlignment(Align.center);

    }

    public void update(IGameWorldAdapter gameScreen){
        this.gameScreen = gameScreen;
        ((Label)elements.get("scorelabel")).setText(pauseScoreMessage + "\n" +  this.gameScreen.getScore());
    }

    public void remakeLabels(PauseGUI.pauseType pauseType){
        preLoadPauseTypeSpecificProperties(pauseType);
        ((Label)elements.get("scorelabel")).setText(pauseScoreMessage + "\n" +  this.gameScreen.getScore());
        postLoadPauseTypeSpecificProperties(pauseType);
    }

    private void loadButtons( PauseGUIWidgetsProperties pauseGUIWidgetsProperties){
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        table.row().padBottom(screenHeight/20);

        //resumePauseMenu
        elements.put("resume",pauseGUIWidgetsProperties.loadPMButton( table, skin, "resumeButton"));

        //restartPauseMenu
        elements.put("restart",pauseGUIWidgetsProperties.loadPMButton( table, skin, "restartButton"));

        //settingsPauseMenu
        elements.put("settings",pauseGUIWidgetsProperties.loadPMButton( table, skin, "settingsPMButton"));

        //exitPauseMenu
        elements.put("exit",pauseGUIWidgetsProperties.loadPMButton( table, skin, "exitPMButton"));

    }



    protected void loadWidgets(){

        PauseGUIWidgetsProperties pauseGUIWidgetsProperties = ((PauseGUIWidgetsProperties)widgetsProperties);

        loadLabels(pauseGUIWidgetsProperties);

        loadButtons(pauseGUIWidgetsProperties);

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

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);
        return true;
    }


    private void preLoadPauseTypeSpecificProperties(PauseGUI.pauseType pauseType){
        switch (pauseType){
            case ENDGAME:
                pauseMessage = "You lost!";
                pauseScoreMessage = "Score:";
                break;
            case PAUSE:
                pauseMessage = "Paused";
                pauseScoreMessage = "Current Score:";
                break;
        }

    }

    private void postLoadPauseTypeSpecificProperties(PauseGUI.pauseType pauseType){
        switch (pauseType){
            case ENDGAME:
                ((Button)elements.get("resume")).setDisabled(true);
                //TODO: GREY BUTTON
                break;
            case PAUSE:

                break;
        }

    }

}
