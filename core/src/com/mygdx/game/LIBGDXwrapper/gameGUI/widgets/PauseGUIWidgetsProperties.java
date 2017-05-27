package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;

public class PauseGUIWidgetsProperties extends WidgetsGeneric{

    public Button loadPMButton(Table table, Skin skin, String nameImageButton){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset(nameImageButton,Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
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
                .colspan(4)
                .getActor();
        label.setFontScale(1f);

        float xRatio = name.length()*((screenWidth/3f)/label.getWidth())/10;

        float yRatio = (screenHeight/6f)/label.getHeight();

        if(name.contains("\n"))
            yRatio*=1.5;

        label.setFontScale(xRatio,yRatio);

        if(label.getWidth()>((screenWidth*8f/10f))+screenWidth/20){
            label.setWidth(((screenWidth*8f/10f))+screenWidth/20);
        }
        return label;
    }

}
