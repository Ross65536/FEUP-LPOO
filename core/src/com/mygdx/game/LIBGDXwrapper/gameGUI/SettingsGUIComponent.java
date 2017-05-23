package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.SettingsGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;

import java.util.HashMap;

import static java.lang.Float.min;

public class SettingsGUIComponent extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;

    SettingsGUI settingsGUI;

    Table table;

    private double viewportWidth = (DeviceConstants.MENU_VIEWPORT);
    private double viewportHeight = (DeviceConstants.MENU_VIEWPORT*DeviceConstants.INVERTED_SCREEN_RATIO);

    private double xPos = viewportWidth/7f;
    private double yPos = viewportHeight/6f;
    private double width = viewportWidth*5f/7f;
    private double height = viewportHeight*4f/6f;


    public SettingsGUIComponent(MenuManager menuManager, SettingsGUI settingsGUI){
        super(menuManager, settingsGUI.widgetsProperties, settingsGUI.widgetsInput);

        table = new Table(skin);
        this.settingsGUI = settingsGUI;
        elements = new HashMap<String, Object>();

        this.addActor(table);

        skin.add("settingsBackgroundImage",new Texture(Gdx.files.internal("settingsBackgroundImage.png")));

        table.setBackground("settingsBackgroundImage");
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

        table.setDebug(true);
    }


    protected void loadWidgets(){
        SettingsGUIWidgetsProperties settingsGUIWidgetsProperties = ((SettingsGUIWidgetsProperties)widgetsProperties);

        settingsGUIWidgetsProperties.loadHeaderLabel(table,skin);

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
        ScrollPane.ScrollPaneStyle scrollPaneStyle = new  ScrollPane.ScrollPaneStyle();
        skin.add("vScrollKnobSettings",new Texture(Gdx.files.internal("vScrollKnobSettings.png")));
        scrollPaneStyle.vScrollKnob = skin.getDrawable("vScrollKnobSettings");
        final ScrollPane scroll = new ScrollPane(scrollTable,scrollPaneStyle);

        scroll.setScrollingDisabled(true,false);
        table.add(scroll).grow().padBottom((float)height/20f);
    }

    protected void loadInputlisteners(){

        SettingsGUIWidgetsInput settingsGUIWidgetsInput = ((SettingsGUIWidgetsInput)widgetsInput);

        settingsGUIWidgetsInput.loadInputExitButton(
                (Button)elements.get("exitButton"),
                menuManager,
                settingsGUI.getBackgroundGUI().getClass()
        );
    }


    private Button getExitButton(){
        String downImage = "settingsExitButtonDown.png";
        String upImage = "settingsExitButtonUp.png";

        WidgetsGeneric.loadToSkin(downImage,downImage, skin);
        WidgetsGeneric.loadToSkin(upImage,upImage, skin);

        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        buttonStyle.down = skin.getDrawable(downImage);
        buttonStyle.up = skin.getDrawable(upImage);

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

        elements.put("exitButton",exitButton);
    }


    private void loadVolumeBarInput(Slider volumeSlider){
        volumeSlider.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        double volumeValue = ((Slider)actor).getValue();//0-100
                        if(volumeValue>50) {
                            Drawable oldVolumeKnockDown = skin.getDrawable("knockDown51_100");
                            if(volumeSlider.getStyle().knobDown != oldVolumeKnockDown){
                                volumeSlider.getStyle().knobDown = skin.getDrawable("knockDown51_100");
                                volumeSlider.getStyle().knob = skin.getDrawable("knockDown51_100");
                            }
                        }else
                            if(volumeValue>0){
                                Drawable oldVolumeKnockDown = skin.getDrawable("knockDown1_50");
                                if(volumeSlider.getStyle().knobDown != oldVolumeKnockDown){
                                    volumeSlider.getStyle().knobDown = skin.getDrawable("knockDown1_50");
                                    volumeSlider.getStyle().knob = skin.getDrawable("knockDown1_50");
                                }
                            }else
                                {
                                    Drawable oldVolumeKnockDown = skin.getDrawable("knockDown0");
                                    if(volumeSlider.getStyle().knobDown != oldVolumeKnockDown){
                                        volumeSlider.getStyle().knobDown = skin.getDrawable("knockDown0");
                                        volumeSlider.getStyle().knob = skin.getDrawable("knockDown0");
                                    }
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
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        skin.add("knockOver", new Texture(Gdx.files.internal("volumeKnockOver.png")));
        skin.add("knockDown0", new Texture(Gdx.files.internal("volumeKnockDown0.png")));
        skin.add("knockDown1_50", new Texture(Gdx.files.internal("volumeKnockDown1_50.png")));
        skin.add("knockDown51_100", new Texture(Gdx.files.internal("volumeKnockDown51_100.png")));

        Slider.SliderStyle volumeSliderStyle = new Slider.SliderStyle(skin.getDrawable("knockOver"),skin.getDrawable("knockDown51_100"));
        skin.add("volumeSliderStyle",volumeSliderStyle);
        Slider volumeSlider = new Slider(0,100,1,false,skin,"volumeSliderStyle");
        volumeSlider.setValue(100);


        loadVolumeBarInput(volumeSlider);


        scrollTable.add(volumeSlider)
                .grow()
                .width(screenWidth/2)
                .height(screenHeight/8)
                .center();


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
