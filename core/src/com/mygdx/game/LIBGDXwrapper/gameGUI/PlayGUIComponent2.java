package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

public class PlayGUIComponent2 extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;


    Table table;

    public PlayGUIComponent2(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);
        table = new Table(skin);

        elements = new  HashMap<String, Object>();

        skin.add("playMenuBackground",new Texture(Gdx.files.internal("playMenuBackground.png")));

        table.setBackground("playMenuBackground");

        this.addActor(table);


        table.setTouchable(Touchable.enabled);
        this.setViewport(
                new FitViewport(
                        (int)(DeviceConstants.MENU_VIEWPORT)
                        ,(int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO)
                )
        );
        table.setWidth((int)(DeviceConstants.MENU_VIEWPORT) * CommonConsts.NUMBER_OF_GAMEMODES);
        table.setHeight((int)(DeviceConstants.MENU_VIEWPORT*  DeviceConstants.INVERTED_SCREEN_RATIO));
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(DeviceConstants.MENU_VIEWPORT), (int)(DeviceConstants.MENU_VIEWPORT *  DeviceConstants.INVERTED_SCREEN_RATIO));

        loadWidgets();
        loadInputlisteners();
    }

    private void loadLabels(PlayGUIWidgetsProperties playGUIWidgetsProperties){

        elements.put("topLabelS",  playGUIWidgetsProperties.loadTopLabel(table, "Platforms",skin));
        elements.put("topLabelW",  playGUIWidgetsProperties.loadTopLabel(table, "Dodging",skin));

    }

    private void loadPlayButton(PlayGUIWidgetsProperties playGUIWidgetsProperties){

        elements.put("swipeLeft", playGUIWidgetsProperties.loadSwipeImageTable(table,
                "swipeImageLeft.png",
                skin));

        elements.put("playButtonS",  playGUIWidgetsProperties.loadPlayButton(table,skin));

        elements.put("swipeRight", playGUIWidgetsProperties.loadSwipeImageTable(table,
                "swipeImageRight.png",
                skin));

        elements.put("swipeLeft", playGUIWidgetsProperties.loadSwipeImageTable(table,
                "swipeImageLeft.png",
                skin));

        elements.put("playButtonW",  playGUIWidgetsProperties.loadPlayButton(table,skin));

        elements.put("swipeRight", playGUIWidgetsProperties.loadSwipeImageTable(table,
                "swipeImageRight.png",
                skin));


    }

    private void loadTutorials(PlayGUIWidgetsProperties playGUIWidgetsProperties){
        String tutorialS = Gdx.files.internal("tutorialS.txt").readString();
        elements.put("textAreaS", playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialS,
                "blueBackgroundTextArea.png",skin));

        String tutorialW = new String(Gdx.files.internal("tutorialW.txt").readString());
        elements.put("textAreaW", playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialW,
                "greenBackgroundTextArea.png",skin));

        table.row();
        elements.put("imageS", playGUIWidgetsProperties.loadTextbackImageTable(table,
                "backGroundButtonImage.png",
                skin));

        elements.put("imageW", playGUIWidgetsProperties.loadTextbackImageTable(table,
                "backGroundButtonImage.png",
                skin));
    }



    protected void loadWidgets(){

        PlayGUIWidgetsProperties playGUIWidgetsProperties = ((PlayGUIWidgetsProperties)widgetsProperties);

        table.center().top();

        loadLabels(playGUIWidgetsProperties);
        table.row();

        loadPlayButton(playGUIWidgetsProperties);
        table.row();

        loadTutorials(playGUIWidgetsProperties);

    }

    protected void loadInputlisteners(){
        PlayGUIWidgetsInput playGUIWidgetsInput = ((PlayGUIWidgetsInput)widgetsInput);

        playGUIWidgetsInput.loadPlayMode2Button(
                (Button)elements.get("playButtonS"),
                menuManager
        );

        playGUIWidgetsInput.loadPlayMode1Button(
                (Button)elements.get("playButtonW"),
                menuManager
        );


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
