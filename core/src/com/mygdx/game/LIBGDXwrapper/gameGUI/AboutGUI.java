package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;

public class AboutGUI extends AbstractGUI{

    private AbstractGUI backgroundGUI = null;

    public AboutGUI(MenuManager menuManager, AbstractGUI backgroundGUI){
        super(menuManager);
        this.backgroundGUI = backgroundGUI;
        addComponents();
        addInputProcessors();
    }

    @Override
    protected void createWidgets(){
        widgetsProperties = new AboutGUIWidgetsProperties();
        widgetsInput = new AboutGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new AboutGUIComponent(menuManager,this));

    }

    @Override
    public void draw(){
        this.backgroundGUI.draw();
        super.draw();
    }

    public AbstractGUI getBackgroundGUI(){
        return backgroundGUI;
    }

    public void setBackgroundGUI(AbstractGUI abstractGUI){
        this.backgroundGUI = abstractGUI;
        ((AboutGUIComponent)menuComponets.get(0)).reloadSettings();
    }

    public String toString(){
        return "AboutGUI";
    }

}
