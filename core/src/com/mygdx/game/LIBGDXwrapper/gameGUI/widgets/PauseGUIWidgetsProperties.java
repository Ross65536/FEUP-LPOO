package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
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
                .width((screenWidth*8f/10f)/4.2f)
                .height((screenHeight*8f/10f)/3.5f)
                .growY()
                .bottom()
                .padBottom(screenHeight/20)
                .getActor();
    }
}
