package com.mygdx.game.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.GameScreen;
import com.mygdx.game.gameGUI.widgets.MainMenuWidgets;

public class MainMenu extends Stage{

    Table table;

    Skin skin;

    public MainMenu(){
        skin = new Skin();
        table = new Table();
        table.setFillParent(true);
        this.addActor(table);
        this.setViewport(
                new FitViewport(
                        (int)(GameScreen.MENU_VIEWPORT)
                        ,(int)(GameScreen.MENU_VIEWPORT*  GameScreen.SCREEN_RATIO)
                )
        );
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(GameScreen.MENU_VIEWPORT), (int)(GameScreen.MENU_VIEWPORT *  GameScreen.SCREEN_RATIO));
        loadWidgets();


        table.setDebug(true);
    }

    private void loadWidgets(){

        skin.add("playButton", MainMenuWidgets.loadPlayButton(table));
        table.row();
        skin.add("hightScoreButton", MainMenuWidgets.loadHighScoreButton(table));
        //table.row();
    }
}
