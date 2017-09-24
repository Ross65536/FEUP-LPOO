package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FontLoader;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;

import static com.mygdx.game.CommonConsts.ABOUT_MENU_TEXT;

public class AboutGUIWidgetsProperties extends WidgetsGeneric {


    public Label loadHeaderLabel(Table table){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        Label label = loadLabel(table,
                ABOUT_MENU_TEXT,
                FontLoader.FONTS.COASTERSHADOW,
                1)
                .padTop(screenHeight/25)
                .padBottom(screenHeight/25)
                //.center()
                .top()
                .prefWidth(screenWidth)
                .prefHeight(screenHeight)
                .getActor();
        label.setAlignment(Align.center);

        label.setWrap(true);

        // Pack label
        label.pack(); // This might not be necessary, unless you're changing other attributes such as font scale.
//
//        // Manual sizing
//        label.setWidth(textWidth); // Set the width directly
//        label.pack(); // Label calculates it's height here, but resets width to 0 (bug?)
//        label.setWidth(textWidth); // Set width again

        return label;
    }

    int FACTOR_PAD = 4;
    public Label loadCreditsText(Table table){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        Label label = loadLabel(table,
                "Hiiiiiiiiiiiiii",
                FontLoader.FONTS.COASTERSHADOW,
                1)
                .padTop(screenHeight/25 / FACTOR_PAD)
                .padBottom(screenHeight/25 / FACTOR_PAD)
                .center().top()
                .prefWidth(screenWidth / 3 )
                .prefHeight(screenHeight/10 * 2)
                .getActor();
        label.setAlignment(Align.center);
        return label;
    }

}