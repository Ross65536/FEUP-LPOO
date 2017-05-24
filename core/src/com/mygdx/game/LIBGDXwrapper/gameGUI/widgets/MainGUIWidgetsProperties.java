package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;


public class MainGUIWidgetsProperties extends WidgetsGeneric {



    public Button loadPropertiesPlayButton(Table table, Skin skin) {

        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playButtonMainMenu",Button.ButtonStyle.class);

        //playButtonMainMenu
        return loadButton(skin, table
                //images
                ,buttonStyle)
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

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("highScoreButtonMainMenu",Button.ButtonStyle.class);

        //highScoreButtonMainMenu
        return loadButton(skin, table
                //images
                ,buttonStyle)
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

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("settingsButtonMainMenu",Button.ButtonStyle.class);

        //settingsButtonMainMenu
        return loadButton(skin, table
                //images
                ,buttonStyle)
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

    public Button loadPropertiesAboutButton(Table table,Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("aboutButtonMainMenu",Button.ButtonStyle.class);

        //aboutButtonMainMenu
        return loadButton(skin, table
                //images
                ,buttonStyle)
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
