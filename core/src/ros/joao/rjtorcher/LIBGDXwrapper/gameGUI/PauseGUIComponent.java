package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUIComponent.ComponentsNames.*;

public class PauseGUIComponent extends AbstractSingleStageGUI {
    HashMap<Integer, Object> elements;

    Table table;

    private double viewportWidth = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT);
    private double viewportHeight = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT* ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO);

    private double xPos = viewportWidth/10f;
    private double yPos = viewportHeight/10f;
    private double width = viewportWidth*8f/10f;
    private double height = viewportHeight*8f/10f;

    private String pauseMessage;
    private String pauseScoreMessage;

    IGameWorldAdapter gameScreen;

    protected enum ComponentsNames {HEADERLABEL, SCORELABEL, RESUME, RESTART, SETTINGS, EXIT};

    public PauseGUIComponent(IGameWorldAdapter gameScreen , ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType pauseType){
        super(menuManager, widgetsProperties, widgetsInput);
        this.gameScreen = gameScreen;
        table = new Table(skin);

        elements = new HashMap<Integer, Object>();

        this.addActor(table);

        Drawable background = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("pauseBackgroundImg",Drawable.class);

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

    private void loadLabels( ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties pauseGUIWidgetsProperties){
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        table.row().padTop(screenHeight/25);

        elements.put(HEADERLABEL.ordinal(),pauseGUIWidgetsProperties.loadPMLabel(table,pauseMessage));

        table.row().padTop(screenHeight/25).padBottom(screenHeight/25);

        elements.put(SCORELABEL.ordinal(),pauseGUIWidgetsProperties.loadPMLabel(table, pauseScoreMessage + "\n" +  ((gameScreen!=null)?this.gameScreen.getScore():0)));
        ((Label)elements.get(SCORELABEL.ordinal())).setAlignment(Align.center);

    }

    public void update(IGameWorldAdapter gameScreen){
        this.gameScreen = gameScreen;
        ((Label)elements.get(SCORELABEL.ordinal())).setText(pauseScoreMessage + "\n" +  this.gameScreen.getScore());
    }

    public void remakeLabels(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType pauseType, IGameWorldAdapter gameScreen){
        this.gameScreen = gameScreen;
        preLoadPauseTypeSpecificProperties(pauseType);
        ((Label)elements.get(SCORELABEL.ordinal())).setText(pauseScoreMessage + "\n" + this.gameScreen.getScore());
        ((Label)elements.get(HEADERLABEL.ordinal())).setText(pauseMessage);
        postLoadPauseTypeSpecificProperties(pauseType);
    }

    private void loadButtons( ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties pauseGUIWidgetsProperties){
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        table.row().padBottom(screenHeight/20);

        //resumePauseMenu
        elements.put(RESUME.ordinal(),pauseGUIWidgetsProperties.loadPMButton( table, skin, "resumeButton"));

        //restartPauseMenu
        elements.put(RESTART.ordinal(),pauseGUIWidgetsProperties.loadPMButton( table, skin, "restartButton"));

        //settingsPauseMenu
        elements.put(SETTINGS.ordinal(),pauseGUIWidgetsProperties.loadPMButton( table, skin, "settingsPMButton"));

        //exitPauseMenu
        elements.put(EXIT.ordinal(),pauseGUIWidgetsProperties.loadPMButton( table, skin, "exitPMButton"));

    }



    protected void loadWidgets(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties pauseGUIWidgetsProperties = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties)widgetsProperties);

        loadLabels(pauseGUIWidgetsProperties);

        loadButtons(pauseGUIWidgetsProperties);

        loadInputlisteners();
    }


    protected void loadInputlisteners(){

        PauseGUIWidgetsInput pauseGUIWidgetsInput = ((PauseGUIWidgetsInput)widgetsInput);

        pauseGUIWidgetsInput.loadInputResumeButton(
                (Button)elements.get(RESUME.ordinal()),
                menuManager
        );

        pauseGUIWidgetsInput.loadInputRestartButton(
                (Button)elements.get(RESTART.ordinal()),
                menuManager
        );


        pauseGUIWidgetsInput.loadInputSettingsButton(
                (Button)elements.get(SETTINGS.ordinal()),
                menuManager
        );


        pauseGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get(EXIT.ordinal()),
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


    private void preLoadPauseTypeSpecificProperties(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType pauseType){
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

    private void postLoadPauseTypeSpecificProperties(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PauseGUI.pauseType pauseType){
        switch (pauseType){
            case ENDGAME:
                ((Button)elements.get(RESUME.ordinal())).setDisabled(true);
                //TODO: GREY BUTTON
                break;
            case PAUSE:
                ((Button)elements.get(RESUME.ordinal())).setDisabled(false);
                break;
        }

    }

}
