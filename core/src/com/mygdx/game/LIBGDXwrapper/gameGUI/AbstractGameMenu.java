package com.mygdx.game.LIBGDXwrapper.gameGUI;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.LIBGDXwrapper.GameSettings;
import com.mygdx.game.LIBGDXwrapper.Settings;

public abstract class AbstractGameMenu extends Stage{

    protected MenuManager menuManager;

    public AbstractGameMenu(MenuManager menuManager){
       this.menuManager = menuManager;
    }

    protected abstract void loadWidgets();

    protected abstract void loadInputlisteners();

}
