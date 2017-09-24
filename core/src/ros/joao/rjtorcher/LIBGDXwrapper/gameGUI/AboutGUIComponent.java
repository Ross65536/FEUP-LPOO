package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.HashMap;

import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.AboutGUIComponent.ComponentsNames.EXIT;

public class AboutGUIComponent extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;

    AboutGUI aboutGUI;

    Table table;

    private double viewportWidth = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT);
    private double viewportHeight = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT* ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO);

    private double xPos = 0;//viewportWidth/20f;
    private double yPos = 0.0;//viewportHeight/6f;
    private double exitButtonxPos = 0;//viewportWidth * 0.05;
    private double exitButtonyPos = viewportHeight * 0.85;
    private double width = viewportWidth;//*5f/7f;
    private double height = viewportHeight;//*4f/6f;


    protected enum ComponentsNames {EXIT};


    public AboutGUIComponent(MenuManager menuManager, AboutGUI aboutGUI){
        super(menuManager, aboutGUI.widgetsProperties, aboutGUI.widgetsInput);

        table = new Table();
        this.aboutGUI = aboutGUI;
        elements = new HashMap<String, Object>();

        this.addActor(table);

        Drawable backgroundImg = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("settingsBackgroundImg",Drawable.class);

        table.setBackground(backgroundImg);
        table.setPosition((float)xPos,(float)yPos);
        table.setSize((float)width,(float)height);

        this.setViewport(
                new StretchViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)viewportWidth, (int)viewportHeight);

        table.setDebug(true);
        loadWidgets();

    }


    protected void loadWidgets(){
        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsProperties aboutGUIWidgetsProperties = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsProperties)widgetsProperties);

        aboutGUIWidgetsProperties.loadAboutMenuText(table);
      //  aboutGUIWidgetsProperties.loadCreditsText(table);

        addExitButton();

        table.row();


        loadInputlisteners();
    }


    protected void loadInputlisteners(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsInput aboutGUIWidgetsInput = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsInput)widgetsInput);

        aboutGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get(EXIT.toString()),
                menuManager,
                aboutGUI.getBackgroundGUI().getClass()
        );
    }


    private Button getExitButton(){

        Button.ButtonStyle buttonStyle = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("exitButtonSettings",Button.ButtonStyle.class);

        return new Button(buttonStyle);
    }


    private void addExitButton(){

        Button exitButton = getExitButton();

        float buttonSize = Math.min(((float)viewportWidth/7f),(float)viewportHeight/6f);
        exitButton.setSize(buttonSize, buttonSize);
        exitButton.setPosition(
                ((float) exitButtonxPos) //+ (buttonSize/2f)
                ,((float)exitButtonyPos)  //+ (buttonSize/2f)
        );
        this.addActor(exitButton);

        elements.put(EXIT.toString(),exitButton);
    }




    public void reloadSettings(){
        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsInput aboutGUIWidgetsInput = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.AboutGUIWidgetsInput)widgetsInput);

        aboutGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get(EXIT.toString()),
                menuManager,
                aboutGUI.getBackgroundGUI().getClass()
        );
    }

    @Override
    public String toString(){
        return "Menu";
    }


    @Override
    public void draw(){
        super.draw();

    }
}
