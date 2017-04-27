package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

import static com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric.loadButton;
import static com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric.loadLabel;

public class PlayGUIWidgetsProperties {

    public static Button loadBackToMenuButton(Table table){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(table
                //images
                ,"playBackToMenuButtonUp.png"
                ,"playBackToMenuButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 4)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)
                .expand()
                .bottom()
                .fillX()
                .getActor();
    }

    public static Button loadSettingsButton(Table table){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(table
                //images
                ,"playSettingsButtonDown.png"
                ,"playSettingsButtonUp.png")
                //pos. and location
                .prefWidth(screenWidth / 4)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)
                .expand()
                .bottom()
                .fillX()
                .getActor();
    }

    public static Label loadTopLabel(Table table, String modeName){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadLabel(table
                //images
                ,modeName
                ,"gameFont")
                //pos. and location
                .prefWidth(screenWidth / 3)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 9)
                .minHeight(screenHeight / 30)
                .maxHeight(screenHeight/3)
                .expand()
                .top()
                .padTop(20)
                .getActor();
    }



}