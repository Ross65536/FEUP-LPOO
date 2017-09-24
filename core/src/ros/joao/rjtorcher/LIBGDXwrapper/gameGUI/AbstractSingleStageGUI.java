package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;


import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

/**
 * A component of the AbstractGUI.
 * Extends a stage.
 */
public abstract class AbstractSingleStageGUI extends Stage{
    protected Skin skin;

    protected WidgetsInput widgetsInput;

    protected ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric widgetsProperties;

    protected MenuManager menuManager;

    public AbstractSingleStageGUI(MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        this.widgetsInput = widgetsInput;
        this.widgetsProperties = widgetsProperties;
        this.menuManager = menuManager;
        skin = new Skin();
    }

    protected abstract void loadWidgets();

    protected abstract void loadInputlisteners();

    public void dispose(){
        super.dispose();
        skin.dispose();
    }

}
