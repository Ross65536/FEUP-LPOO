package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

import static com.mygdx.game.LIBGDXwrapper.gameGUI.PlayGUIComponent2.ComponentsNames.*;

public class PlayGUIComponent2 extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;

    Table table;

    protected enum ComponentsNames {playButtonS, playButtonW, topLabelS, topLabelW, textAreaS, textAreaW, imageS, imageW};

    public PlayGUIComponent2(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);
        table = new Table(skin);

        elements = new  HashMap<String, Object>();

        this.addActor(table);


        table.setTouchable(Touchable.enabled);
        this.setViewport(
                new StretchViewport(
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
                (Button)elements.get(playButtonS.toString()),
                menuManager
        );

        playGUIWidgetsInput.loadPlayMode1Button(
                (Button)elements.get(playButtonW.toString()),
                menuManager
        );


        playGUIWidgetsInput.loadSlideFunction(
                table,
                menuManager,
                (OrthographicCamera)this.getCamera()
        );

    }

    private void loadLabels(PlayGUIWidgetsProperties playGUIWidgetsProperties){

        elements.put(topLabelS.toString(),  playGUIWidgetsProperties.loadTopLabel(table, "Platforms"));
        elements.put(topLabelW.toString(),  playGUIWidgetsProperties.loadTopLabel(table, "Dodging"));

    }

    private void loadPlayButton(PlayGUIWidgetsProperties playGUIWidgetsProperties){

        Drawable swipeLeft = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("swipeLeft",Drawable.class);

        Drawable swipeRight = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("swipeRight",Drawable.class);

        elements.put(swipeLeft.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                swipeLeft));

        elements.put(playButtonS.toString(),  playGUIWidgetsProperties.loadPlayButton(table,skin));

        elements.put(swipeRight.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                swipeRight));

        elements.put(swipeLeft.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                               swipeLeft));

        elements.put(playButtonW.toString(),  playGUIWidgetsProperties.loadPlayButton(table,skin));

        elements.put(swipeRight.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                swipeRight));


    }

    private void loadTutorials(PlayGUIWidgetsProperties playGUIWidgetsProperties){
        String tutorialS = Gdx.files.internal("tutorialS.txt").readString();
        elements.put(textAreaS.toString(), playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialS
                ));

        String tutorialW = new String(Gdx.files.internal("tutorialW.txt").readString());
        elements.put(textAreaW.toString(), playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialW
                ));

        table.row();

        Drawable background = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("backGroundButtonImg",Drawable.class);

        elements.put(imageS.toString(), playGUIWidgetsProperties.loadTextbackImageTable(table,
                background));

        elements.put(imageW.toString(), playGUIWidgetsProperties.loadTextbackImageTable(table,
                background));
    }



    @Override
    public String toString(){
        return "PlayMenu";
    }
}
