package com.mygdx.game.LIBGDXwrapper.gameGUI;


import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractSingleStageGUI extends Stage{

    protected MenuManager menuManager;

    public AbstractSingleStageGUI(MenuManager menuManager){
       this.menuManager = menuManager;
    }

    protected abstract void loadWidgets();

    protected abstract void loadInputlisteners();
}
