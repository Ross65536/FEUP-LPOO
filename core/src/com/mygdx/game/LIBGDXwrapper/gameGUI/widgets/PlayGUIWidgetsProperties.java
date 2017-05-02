package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

import static com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric.loadButton;
import static com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric.loadLabel;
import static com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric.loadTextArea;

public class PlayGUIWidgetsProperties extends WidgetsGeneric {

    public Button loadBackToMenuButton(Table table){
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

    public Button loadSettingsButton(Table table){
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

    public Label loadTopLabel(Table table, String modeName){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Label label = loadLabel(table
                //images
                ,modeName
                ,"gameFont"
                ,null)
                //pos. and location
                .prefWidth(screenWidth / 3)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 9)
                .minHeight(screenHeight / 30)
                .maxHeight(screenHeight/3)
                .expandX()
                .top()
                .padTop(20)
                .getActor();
        label.setFontScale(2);
        label.setAlignment(0);
        return label;
    }

    public Button loadPlayButton(Table table){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(table
                //images
                ,"playBackToMenuButtonDown.png"
                ,"playBackToMenuButtonUp.png")
                //pos. and location
                .prefWidth(screenWidth / 4)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 7)
                .minHeight(screenHeight / 30)
                .maxHeight(screenHeight/3)
                .expandX()
                .top()
                .padTop(20)
                .getActor();
    }

    public ScrollPane loadTextAreaTutorial(Table table, String text, String backgroundImage){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        ScrollPane textArea = loadTextArea(table,
                text,
                "tutorial",
                backgroundImage)
                .fill()
                .expand()
                .padBottom(screenHeight / 6)
                //pos. and location
                .getActor();
        textArea.setBounds(0,0,DeviceConstants.MENU_VIEWPORT,500);
        //((Label)textArea.getChildren().pop()).setWrap(true);
        return textArea;
    }

}
