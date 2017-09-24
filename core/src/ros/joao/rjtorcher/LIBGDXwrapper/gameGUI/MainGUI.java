package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties;

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
        this.addComponent(new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MainGUIComponet(menuManager,widgetsProperties , widgetsInput));
    }

    public String toString(){
        return "MainMenu";
    }
}
