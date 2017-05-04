package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Constants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

public class PlayGUIComponent2 extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;


    public PlayGUIComponent2(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);

        elements = new  HashMap<String, Object>();
        table = new Table();
        //table.setFillParent(true);

        this.addActor(table);
        table.setTouchable(Touchable.enabled);
        this.setViewport(
                new FitViewport(
                        (int)(DeviceConstants.MENU_VIEWPORT)
                        ,(int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO)
                )
        );
        table.setWidth((int)(DeviceConstants.MENU_VIEWPORT) * Constants.NUMBER_OF_GAMEMODES);
        table.setHeight((int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO));
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(DeviceConstants.MENU_VIEWPORT), (int)(DeviceConstants.MENU_VIEWPORT *  DeviceConstants.INVERTED_SCREEN_RATIO));

        loadWidgets();
        loadInputlisteners();
        table.setDebug(true);
    }

    protected void loadWidgets(){

        PlayGUIWidgetsProperties playGUIWidgetsProperties = ((PlayGUIWidgetsProperties)widgetsProperties);

        table.center().top();
        elements.put("topLabelS",  playGUIWidgetsProperties.loadTopLabel(table, "Survival"));
        elements.put("topLabelW",  playGUIWidgetsProperties.loadTopLabel(table, "Weapons"));
        elements.put("topLabelE",  playGUIWidgetsProperties.loadTopLabel(table, "Exemplo"));

        table.row();
        elements.put("playButtonS",  playGUIWidgetsProperties.loadPlayButton(table));
        elements.put("playButtonW",  playGUIWidgetsProperties.loadPlayButton(table));
        elements.put("playButtonE",  playGUIWidgetsProperties.loadPlayButton(table));


        table.row();
        String tutorialS = Gdx.files.internal("tutorialS.txt").readString();
        elements.put("textAreaS", playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialS,
                "blueBackgroundTextArea.png"));

        String tutorialW = new String(Gdx.files.internal("tutorialW.txt").readString());
        elements.put("textAreaW", playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialW,
                "greenBackgroundTextArea.png"));

        String tutorialE = new String(Gdx.files.internal("tutorialE.txt").readString());
        elements.put("textAreaE", playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialE,
                "redBackgroundTextArea.png"));
    }

    protected void loadInputlisteners(){
        PlayGUIWidgetsInput playGUIWidgetsInput = ((PlayGUIWidgetsInput)widgetsInput);

        playGUIWidgetsInput.loadSlideFunction(
                table,
                menuManager,
                (OrthographicCamera)this.getCamera()
        );

    }

    @Override
    public String toString(){
        return "PlayMenu";
    }
}
