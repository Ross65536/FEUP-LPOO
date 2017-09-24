package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FontLoader;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.MagicNumbers;

import static com.mygdx.game.CommonConsts.ABOUT_MENU_TEXT;

public class AboutGUIWidgetsProperties extends WidgetsGeneric {


    public Label loadHeaderLabel(Table table){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;
        Label label = loadLabel(table,
                ABOUT_MENU_TEXT,
                FontLoader.FONTS.COASTERSHADOW_BLACK,
                MagicNumbers.ABOUT_MENU_FONT_SIZE_INDEX)
                .padTop(screenHeight/MagicNumbers.ABOUT_MENU_LABEL_PADDING_DIV)
                .padBottom(screenHeight/MagicNumbers.ABOUT_MENU_LABEL_PADDING_DIV)
                .top()
                .prefWidth(screenWidth*MagicNumbers.ABOUT_MENU_LABEL_PREF_SIZE)
                .prefHeight(screenHeight*MagicNumbers.ABOUT_MENU_LABEL_PREF_SIZE)
                .getActor();
        label.setAlignment(Align.left);

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
}