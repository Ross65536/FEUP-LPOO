package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PlayGUIComponent1.ComponentsNames.backToMenu;
import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PlayGUIComponent1.ComponentsNames.settings;

public class PlayGUIComponent1 extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;

    Table table;

    protected enum ComponentsNames {backToMenu, settings};


    public PlayGUIComponent1(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);
        table = new Table(skin);

        elements = new HashMap<String, Object>();

        table.setTouchable(Touchable.enabled);
        table.setFillParent(true);
        this.addActor(table);
        this.setViewport(
                new StretchViewport(
                        (int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT)
                        ,(int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT*  ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO)
                )
        );
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT), (int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT *  ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO));
        loadWidgets();

    }

    protected void loadWidgets(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties playGUIWidgetsProperties = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties)widgetsProperties);


        elements.put(backToMenu.toString(), playGUIWidgetsProperties.loadBackToMenuButton(table,skin));
        elements.put(settings.toString(), playGUIWidgetsProperties.loadSettingsButton(table,skin));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        PlayGUIWidgetsInput playGUIWidgetsInput = ((PlayGUIWidgetsInput)widgetsInput);

        playGUIWidgetsInput.loadSettingsButton(
            (Button)elements.get(settings.toString()),
            menuManager
        );

        playGUIWidgetsInput.loadBackToMenuButton(
            (Button)elements.get(backToMenu.toString()),
            menuManager
        );



    }

    @Override
    public String toString(){
        return "Menu";
    }
}
