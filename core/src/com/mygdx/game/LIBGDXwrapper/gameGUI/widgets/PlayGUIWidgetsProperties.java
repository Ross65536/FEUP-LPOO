package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

public class PlayGUIWidgetsProperties extends WidgetsGeneric {

    public Button loadBackToMenuButton(Table table, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
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

    public Button loadSettingsButton(Table table,Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
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

    public Label loadTopLabel(Table table, String modeName, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Label label = loadLabel(skin, table
                //images
                ,modeName
                ,"gameFont"
                ,null)
                //pos. and location
                .width(screenWidth / 3.5f)
                .height(screenHeight / 8)
                .expandX()
                .top()
                .padTop(screenHeight/30)
                .getActor();

        label.setFontScale(1f);
        float xRatio = (screenWidth/3.5f)/label.getWidth();
        float yRatio = (screenHeight/8f)/label.getHeight();
        label.setFontScale(xRatio,yRatio);

        return label;
    }

    public Button loadPlayButton(Table table, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
                //images
                ,"playBackToMenuButtonDown.png"
                ,"playBackToMenuButtonUp.png")
                //pos. and location
                .width(screenWidth / 3)
                .height(screenHeight / 6)
                .expandX()
                .top()
                .padTop(screenHeight/30)
                .getActor();
    }

    public ScrollPane loadTextAreaTutorial(Table table, String text, String backgroundImage, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        ScrollPane textArea = loadTextArea(skin, table,
                text,
                "tutorial",
                backgroundImage)
                .fill()
                .expand()
                .padBottom(screenHeight / 6)
                //pos. and location
                .getActor();

        skin.add("vScrollKnobPlayMenu",new Texture(Gdx.files.internal("vScrollKnobSettings.png")));

        ScrollPane.ScrollPaneStyle styleScroll = new ScrollPane.ScrollPaneStyle();
        styleScroll.vScrollKnob = skin.getDrawable("vScrollKnobPlayMenu");

        textArea.setStyle(styleScroll);
        textArea.setScrollingDisabled(true,false);

        return textArea;
    }

}
