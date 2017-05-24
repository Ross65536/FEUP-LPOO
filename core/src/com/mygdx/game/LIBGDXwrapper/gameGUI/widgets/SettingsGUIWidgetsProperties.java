package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;

public class SettingsGUIWidgetsProperties extends WidgetsGeneric {

    public Button loadPropertiesExitButton(Table table,Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("exitButtonSettings",Button.ButtonStyle.class);

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

    public Label loadHeaderLabel(Table table, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        Label label = loadLabel(skin, table,
                "Settings",
                "gameFont",
                null)
                .padTop(screenHeight/25)
                .padBottom(screenHeight/25)
                .center().top()
                .prefWidth(screenWidth / 3)
                .prefHeight(screenHeight/10)
                .getActor();
        label.setFontScale(1f);
        float xRatio = (screenWidth/3f)/label.getWidth();
        float yRatio = (screenHeight/10f)/label.getHeight();
        label.setFontScale(xRatio,yRatio);
        return label;
    }

    public Label loadOptionLabel(Table table, Skin skin, String option){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        Label label = loadLabel(skin, table,
                option,
                "gameFont",
                null)
                .center()
                .prefWidth(screenWidth/3)
                .prefHeight(screenHeight/8)
                .getActor();
        label.setFontScale(1f);
        float xRatio = (screenWidth/3f)/label.getWidth();
        float yRatio = (screenHeight/8f)/label.getHeight();
        label.setFontScale(xRatio,yRatio);
        return label;
    }

}