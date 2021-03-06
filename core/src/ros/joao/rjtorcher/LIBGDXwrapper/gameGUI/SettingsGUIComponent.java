package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.HashMap;

import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUIComponent.ComponentsNames.*;

public class SettingsGUIComponent extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;

    ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI settingsGUI;

    Table table;

    private double viewportWidth = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT);
    private double viewportHeight = (ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT* ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO);

    private double xPos = viewportWidth/7f;
    private double yPos = viewportHeight/6f;
    private double width = viewportWidth*5f/7f;
    private double height = viewportHeight*4f/6f;

    protected enum ComponentsNames {EXIT};


    public SettingsGUIComponent(MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.SettingsGUI settingsGUI){
        super(menuManager, settingsGUI.widgetsProperties, settingsGUI.widgetsInput);

        table = new Table();
        this.settingsGUI = settingsGUI;
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

        
        loadWidgets();

    }


    protected void loadWidgets(){
        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties settingsGUIWidgetsProperties = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties)widgetsProperties);

        settingsGUIWidgetsProperties.loadHeaderLabel(table);

        addExitButton();

        table.row();

        loadSrollOptions();

        loadInputlisteners();
    }

    private void loadSrollOptions(){

        Table scrollTable = new Table();

        scrollTable.setRound(false);

        loadVolumeBar(scrollTable);

        addScrollMenuToTable(scrollTable);
    }

    private void addScrollMenuToTable(Table scrollTable){

        ScrollPane.ScrollPaneStyle scrollPaneStyle = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("vScrollKnobForSettings",ScrollPane.ScrollPaneStyle.class);


        final ScrollPane scroll = new ScrollPane(scrollTable,scrollPaneStyle);

        scroll.setScrollingDisabled(true,false);
        table.add(scroll).grow().padBottom((float)height/20f);
    }

    protected void loadInputlisteners(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput settingsGUIWidgetsInput = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput)widgetsInput);

        settingsGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get(EXIT.toString()),
                menuManager,
                settingsGUI.getBackgroundGUI().getClass()
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
                ((float)viewportWidth/7f) - (buttonSize/2f)
                ,((float)viewportHeight*5f/6f)  - (buttonSize/2f)
        );
        this.addActor(exitButton);

        elements.put(EXIT.toString(),exitButton);
    }


    private void loadVolumeBarInput(Slider volumeSlider){
        volumeSlider.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        double volumeValue = ((Slider)actor).getValue();//0-100
                        if(volumeValue>50) {

                            Slider.SliderStyle sliderStyle = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("volumeSliderStyle51_100",Slider.SliderStyle.class);
                            volumeSlider.setStyle(sliderStyle);
                        }else
                            if(volumeValue>0){

                                Slider.SliderStyle sliderStyle = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("volumeSliderStyle1_50",Slider.SliderStyle.class);
                                volumeSlider.setStyle(sliderStyle);
                            }else
                                {

                                    Slider.SliderStyle sliderStyle = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("volumeSliderStyle0",Slider.SliderStyle.class);
                                    volumeSlider.setStyle(sliderStyle);
                                }
                    }

                }
        );
        volumeSlider.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                event.stop();
                return false;
        }});
    }


    private void loadVolumeBar(Table scrollTable){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Slider.SliderStyle sliderStyle = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("volumeSliderStyle51_100",Slider.SliderStyle.class);

        Slider volumeSlider = new Slider(0,100,1,false,sliderStyle);
        volumeSlider.setValue(100);


        loadVolumeBarInput(volumeSlider);


        scrollTable.add(volumeSlider)
                .grow()
                .width(screenWidth/2)
                .height(screenHeight/8)
                .center();


    }

    public void reloadSettings(){
        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput settingsGUIWidgetsInput = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput)widgetsInput);

        settingsGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get(EXIT.toString()),
                menuManager,
                settingsGUI.getBackgroundGUI().getClass()
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
