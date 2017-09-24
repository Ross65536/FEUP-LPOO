package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsInput;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsProperties;

public class AboutGUI extends ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AbstractGUI {

    private ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AbstractGUI backgroundGUI = null;

    public AboutGUI(MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AbstractGUI backgroundGUI){
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
        this.addComponent(new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AboutGUIComponent(menuManager,this));

    }

    @Override
    public void draw(){
        this.backgroundGUI.draw();
        super.draw();
    }

    public ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AbstractGUI getBackgroundGUI(){
        return backgroundGUI;
    }

    public void setBackgroundGUI(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AbstractGUI abstractGUI){
        this.backgroundGUI = abstractGUI;
        ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AboutGUIComponent)menuComponets.get(0)).reloadSettings();
    }

    public String toString(){
        return "AboutGUI";
    }

}
