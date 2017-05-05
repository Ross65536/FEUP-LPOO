package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;

public class SettingsGUI extends AbstractGUI{

    AbstractGUI backgroundGUI;

    public SettingsGUI(MenuManager menuManager, AbstractGUI backgroundGUI){
        super(menuManager);

        this.backgroundGUI = backgroundGUI;
        addComponents();
        addInputProcessors();
    }

    @Override
    public void draw(){
        this.backgroundGUI.draw();
        super.draw();
    }

    protected void createWidgets(){
        widgetsProperties = new SettingsGUIWidgetsProperties();
        widgetsInput = new SettingsGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new SettingsGUIComponent(menuManager,this));
    }
    public AbstractGUI getBackgroundGUI(){
        return backgroundGUI;
    }
    public String toString(){
        return "SettingsGUI";
    }
}
