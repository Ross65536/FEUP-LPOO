package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;

public class PlayGUIWidgetsProperties extends WidgetsGeneric {

    public Button loadBackToMenuButton(Table table, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playBackToMenuButton",Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
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

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playSettingsButton",Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
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
                .padBottom(screenHeight/30)
                .colspan(3)
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

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playModeButton",Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
                //pos. and location
                .width(screenWidth / 3)
                .height(screenHeight / 6)
                .expandX()
                .top()
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
                .colspan(3)
                .height(screenHeight - (screenHeight / 3) - (screenHeight / 8) - (screenHeight/15))
                //.padBottom(screenHeight / 6)
                //pos. and location
                .getActor();

        skin.add("vScrollKnobPlayMenu",new Texture(Gdx.files.internal("vScrollKnobSettings.png")));

        ScrollPane.ScrollPaneStyle styleScroll = new ScrollPane.ScrollPaneStyle();
        styleScroll.vScrollKnob = skin.getDrawable("vScrollKnobPlayMenu");

        textArea.setStyle(styleScroll);
        textArea.setScrollingDisabled(true,false);

        return textArea;
    }

    public Image loadTextbackImageTable(Table table, String backgroundImage, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Image image = loadImage(skin, table,
                backgroundImage)
                .grow()
                .height(screenHeight / 6)
                .colspan(3)
                //pos. and location
                .getActor();


        return image;
    }

    public Image loadSwipeImageTable(Table table, String swipeImage, Skin skin){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Image image = loadImage(skin, table,
                swipeImage)
                .grow()
                .width(screenWidth / 3)
                .height(screenHeight / 6)
                //pos. and location
                .getActor();
        return image;
    }

}
