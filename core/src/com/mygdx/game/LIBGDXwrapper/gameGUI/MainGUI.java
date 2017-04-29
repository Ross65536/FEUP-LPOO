package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;

public class MainGUI extends AbstractGUI{

    public MainGUI(MenuManager menuManager){
        super(menuManager);


        addComponents();
        addInputProcessors();
    }


    protected void createWidgets(){
        widgetsProperties = new MainGUIWidgetsProperties();
        widgetsInput = new MainGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new MainGUIComponet(menuManager,widgetsProperties , widgetsInput));
    }

}
