package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.LIBGDXwrapper.GameScreen;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainMenuWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainMenuWidgetsProperties;

public class MainMenu extends AbstractGameMenu {

    Table table;

    Skin skin;

    public MainMenu(MenuManager menuManager){
        super(menuManager);

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
        loadInputlisteners();

        table.setDebug(true);
    }

    protected void loadWidgets(){

        skin.add("playButton", MainMenuWidgetsProperties.loadPropertiesPlayButton(table));
        table.row();
        skin.add("hightScoreButton", MainMenuWidgetsProperties.loadPropertiesHighScoreButton(table));
        //table.row();
    }

    protected void loadInputlisteners(){
        MainMenuWidgetsInput.loadInputPlayButton(
                skin.get("playButton", Button.class),
                menuManager
        );

        MainMenuWidgetsInput.loadInputHighScoreButton(
                skin.get("hightScoreButton", Button.class),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }
}
