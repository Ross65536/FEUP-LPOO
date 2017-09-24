package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

public class SettingsGUI extends AbstractGUI{

    private AbstractGUI backgroundGUI = null;

    public SettingsGUI(MenuManager menuManager, AbstractGUI backgroundGUI){
        super(menuManager);
        this.backgroundGUI = backgroundGUI;
        addComponents();
        addInputProcessors();
    }

    protected SettingsGUI(MenuManager menuManager){
        super(menuManager);
    }

    @Override
    public void draw(){
        this.backgroundGUI.draw();
        super.draw();
    }

    protected void createWidgets(){
        widgetsProperties = new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties();
        widgetsInput = new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new SettingsGUIComponent(menuManager,this));
    }

    public AbstractGUI getBackgroundGUI(){
        return backgroundGUI;
    }

    public void setBackgroundGUI(AbstractGUI abstractGUI){
        this.backgroundGUI = abstractGUI;
        ((SettingsGUIComponent)menuComponets.get(0)).reloadSettings();
    }

    public String toString(){
        return "SettingsGUI";
    }

}
