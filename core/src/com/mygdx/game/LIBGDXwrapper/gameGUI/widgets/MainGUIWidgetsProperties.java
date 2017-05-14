package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;


public class MainGUIWidgetsProperties extends WidgetsGeneric {



    public Button loadPropertiesPlayButton(Table table, Skin skin) {

        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
                //images
                ,"playButtonUp.png"
                ,"playButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 2)
                .padBottom(50)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)

                .getActor();
    }

    public Button loadPropertiesHighScoreButton( Table table,Skin skin) {

        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
                //images
                ,"highScoreButtonUp.png"
                ,"highScoreButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 2)
                .padBottom(50)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)

                .getActor();
    }

    public Button loadPropertiesSettingsButton(Table table,Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
                //images
                ,"settingsButtonUp.png"
                ,"settingsButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 2)
                .padBottom(50)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)

                .getActor();
    }

    public Button loadPropertiesExitButton(Table table,Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
                //images
                ,"exitButtonUp.png"
                ,"exitButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 2)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)

                .getActor();
    }

}
