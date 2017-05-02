package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;

public class SettingsGUI extends AbstractGUI{

    public SettingsGUI(MenuManager menuManager){
        super(menuManager);


        addComponents();
        addInputProcessors();
    }


    protected void createWidgets(){
        widgetsProperties = new SettingsGUIWidgetsProperties();
        widgetsInput = new SettingsGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new SettingsGUIComponent(menuManager,widgetsProperties , widgetsInput));
    }

}
