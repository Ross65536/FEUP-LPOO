package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.HashMap;

import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.MainGUIComponet.ComponentsNames.*;

public class MainGUIComponet extends AbstractSingleStageGUI {

    Table table;

    HashMap<String, Object> elements;

    protected enum ComponentsNames {PLAYBUTTON,HIGHSCOREBUTTON,SETTINGSBUTTON,ABOUTBUTTON};

    public MainGUIComponet(MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric widgetsProperties, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);
        table = new Table(skin);

        elements = new HashMap<String, Object>();
        table.setFillParent(true);
        this.addActor(table);


        double viewportWidth = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT);
        double viewportHeight = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT* ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO);

        this.setViewport(
                new StretchViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)viewportWidth, (int)viewportHeight);

        loadWidgets();

    }

    protected void loadWidgets(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties mainGUIWidgetsProperties = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsProperties)widgetsProperties);

        elements.put(PLAYBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesPlayButton(table,skin));
        table.row();
        elements.put(HIGHSCOREBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesHighScoreButton(table,skin));
        table.row();
        elements.put(SETTINGSBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesSettingsButton(table,skin));
        table.row();
        elements.put(ABOUTBUTTON.toString(), mainGUIWidgetsProperties.loadPropertiesAboutButton(table,skin));

        loadInputlisteners();
    }

    protected void loadInputlisteners(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput mainGUIWidgetsInput = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.MainGUIWidgetsInput)widgetsInput);

        mainGUIWidgetsInput.loadInputPlayButton(
                (Button)elements.get(PLAYBUTTON.toString()),
                menuManager
        );

        mainGUIWidgetsInput.loadInputHighScoreButton(
                (Button)elements.get(HIGHSCOREBUTTON.toString()),
                menuManager
        );

        mainGUIWidgetsInput.loadInputSettingsButton(
                (Button)elements.get(SETTINGSBUTTON.toString()),
                menuManager
        );

        mainGUIWidgetsInput.loadInputAboutButton(
                (Button)elements.get(ABOUTBUTTON.toString()),
                menuManager
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }


}
