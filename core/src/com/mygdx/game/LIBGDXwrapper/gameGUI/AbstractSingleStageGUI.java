package com.mygdx.game.LIBGDXwrapper.gameGUI;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

public abstract class AbstractSingleStageGUI extends Stage{

    protected WidgetsInput widgetsInput;

    protected WidgetsGeneric widgetsProperties;

    protected MenuManager menuManager;

    public AbstractSingleStageGUI(MenuManager menuManager, WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        this.widgetsInput = widgetsInput;
        this.widgetsProperties = widgetsProperties;
        this.menuManager = menuManager;
    }

    protected abstract void loadWidgets();

    protected abstract void loadInputlisteners();

}
