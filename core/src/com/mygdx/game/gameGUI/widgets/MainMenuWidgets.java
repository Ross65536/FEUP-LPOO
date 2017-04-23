package com.mygdx.game.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.GameScreen;


public class MainMenuWidgets extends WidgetsGeneric {



    public static Button loadPlayButton(Table table) {

        float screenWidth = GameScreen.MENU_VIEWPORT;
        float screenHeight = (float)GameScreen.SCREEN_RATIO * GameScreen.MENU_VIEWPORT;

        return loadButton(table
                //names
                ,"playButtonUpImage"
                ,"playButtonDownImage"
                //images
                ,"android\\assets\\playButtonUp.png"
                ,"android\\assets\\playButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 4)
                .padBottom(50)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)

                .getActor();
    }

    public static Button loadHighScoreButton(Table table) {

        float screenWidth = GameScreen.MENU_VIEWPORT;
        float screenHeight = (float)GameScreen.SCREEN_RATIO * GameScreen.MENU_VIEWPORT;

        return loadButton(table
                //names
                ,"highScoreButtonUpImage"
                ,"highScoreButtonDownImage"
                //images
                ,"android\\assets\\highScoreButtonUp.png"
                ,"android\\assets\\highScoreButtonDown.png")
                //pos. and location
                .prefWidth(screenWidth / 4)
                .padBottom(50)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)

                .getActor();
    }


}
