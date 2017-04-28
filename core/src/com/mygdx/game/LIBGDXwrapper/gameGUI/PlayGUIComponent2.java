package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.CommonConstants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;

import java.nio.charset.Charset;
import java.util.HashMap;

public class PlayGUIComponent2 extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;



    public PlayGUIComponent2(MenuManager menuManager){
        super(menuManager);

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
        table.setWidth((int)(DeviceConstants.MENU_VIEWPORT) * CommonConstants.NUMBER_OF_GAMEMODES);
        table.setHeight((int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO));
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(DeviceConstants.MENU_VIEWPORT), (int)(DeviceConstants.MENU_VIEWPORT *  DeviceConstants.INVERTED_SCREEN_RATIO));

        loadWidgets();
        loadInputlisteners();
        table.setDebug(true);
    }

    protected void loadWidgets(){

        table.center().top();
        elements.put("topLabelS", PlayGUIWidgetsProperties.loadTopLabel(table, "Survival"));
        elements.put("topLabelW", PlayGUIWidgetsProperties.loadTopLabel(table, "Weapons"));
        elements.put("topLabelE", PlayGUIWidgetsProperties.loadTopLabel(table, "Exemplo"));

        table.row();
        elements.put("playButtonS", PlayGUIWidgetsProperties.loadPlayButton(table));
        elements.put("playButtonW", PlayGUIWidgetsProperties.loadPlayButton(table));
        elements.put("playButtonE", PlayGUIWidgetsProperties.loadPlayButton(table));


        table.row();
        String tutorialS = Gdx.files.internal("tutorialS.txt").readString();
        elements.put("textAreaS", PlayGUIWidgetsProperties.loadTextAreaTutorial(table,
                "", //não funciona
                "blueBackgroundTextArea.png"));

        String tutorialW = new String(Gdx.files.internal("tutorialW.txt").readString());
        elements.put("textAreaW", PlayGUIWidgetsProperties.loadTextAreaTutorial(table,
                "", //não funciona
                "greenBackgroundTextArea.png"));

        String tutorialE = new String(Gdx.files.internal("tutorialE.txt").readString());
        elements.put("textAreaE", PlayGUIWidgetsProperties.loadTextAreaTutorial(table,
                "", //não funciona
                "redBackgroundTextArea.png"));
    }

    protected void loadInputlisteners(){
        PlayGUIWidgetsInput.loadSlideFunction(
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
