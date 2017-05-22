package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

public class PauseGUIWidgetsProperties extends WidgetsGeneric{

    public Button loadPMButton(Table table, Skin skin, String nameButton){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        return loadButton(skin, table
                //images
                ,nameButton + "Up.png"
                ,nameButton + "Down.png")
                //pos. and location
                .width((screenWidth*8f/10.5f)/4.2f)
                .height((screenHeight*8f/10f)/3.5f)
                .growY()
                .bottom()
                .getActor();
    }

    public Label loadPMLabel(Table table, Skin skin, String name){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        Label label = loadLabel(skin, table,
                name,
                "gameFont",
                null)
                /*
                .padTop(screenHeight/25)
                .padBottom(screenHeight/25)
                .expand().center().top()
                .prefWidth(screenWidth / 3)
                .prefHeight(screenHeight/10)
                */
                .colspan(4)
                .getActor();
        label.setFontScale(1f);
        float xRatio = (screenWidth/3f)/label.getWidth();
        float yRatio = (screenHeight/10f)/label.getHeight();
        label.setFontScale(xRatio,yRatio);
        return label;
    }

}
