package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

public class PlayGUI extends AbstractGUI{

    public PlayGUI(MenuManager menuManager){
        super(menuManager);


        addComponents();
        addInputProcessors();
    }

    protected void createWidgets(){
        widgetsProperties = new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties();
        widgetsInput = new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new PlayGUIComponent1(menuManager, widgetsProperties, widgetsInput));
        this.addComponent(new PlayGUIComponent2(menuManager, widgetsProperties, widgetsInput));

    }

    public String toString(){
        return "PlayGUI";
    }
}
