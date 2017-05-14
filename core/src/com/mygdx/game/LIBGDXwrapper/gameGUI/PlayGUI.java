package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;

public class PlayGUI extends AbstractGUI{

    public PlayGUI(MenuManager menuManager){
        super(menuManager);


        addComponents();
        addInputProcessors();
    }

    protected void createWidgets(){
        widgetsProperties = new PlayGUIWidgetsProperties();
        widgetsInput = new PlayGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new PlayGUIComponent1(menuManager, widgetsProperties, widgetsInput));
        this.addComponent(new PlayGUIComponent2(menuManager, widgetsProperties, widgetsInput));

    }

    public String toString(){
        return "PlayGUI";
    }
}
