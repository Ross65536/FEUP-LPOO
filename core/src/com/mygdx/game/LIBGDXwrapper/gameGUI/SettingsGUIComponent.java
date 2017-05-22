package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
                new FitViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)viewportWidth, (int)viewportHeight);

        loadWidgets();

        table.setRound(false);
        table.setDebug(true);
    }


    protected void loadWidgets(){
        SettingsGUIWidgetsProperties settingsGUIWidgetsProperties = ((SettingsGUIWidgetsProperties)widgetsProperties);

        settingsGUIWidgetsProperties.loadHeaderLabel(table,skin);

        addExitButton();

        loadSrollOptions();

        loadInputlisteners();
    }

    private void loadVolumeBar(Table scrollTable){
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        skin.add("knockOver", new Texture(Gdx.files.internal("volumeKnockOver.png")));
        skin.add("knockDown", new Texture(Gdx.files.internal("volumeKnockDown.png")));
        Slider.SliderStyle volumeSliderStyle = new Slider.SliderStyle(skin.getDrawable("knockOver"),skin.getDrawable("knockDown"));
        skin.add("volumeSliderStyle",volumeSliderStyle);
        Slider volumeSlider = new Slider(0,100,1,false,skin,"volumeSliderStyle");

        scrollTable.add(volumeSlider)
                    .center()
                    .prefWidth(screenWidth/2)
                    .height(screenHeight/8);

    }


    private void loadSrollOptions(){
        table.row();
        String[] options = {" option1", " option2", " option3", " option4"};

        SettingsGUIWidgetsProperties settingsGUIWidgetsProperties = ((SettingsGUIWidgetsProperties)widgetsProperties);
        Table scrollTable = new Table();
        table.setRound(false);

        loadVolumeBar(scrollTable);
        scrollTable.row();

        loadVolumeBar(scrollTable);

        for(int i = 0; i < options.length; i++){
            scrollTable.row().height((float)viewportHeight/5f).expandX().fillX();
            settingsGUIWidgetsProperties.loadOptionLabel(scrollTable,skin,options[i]);
        }


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

        Container container = new Container(exitButton);

        container.setSize(buttonSize, buttonSize);
        exitButton.setLayoutEnabled(true);
        exitButton.layout();
        exitButton.align(Align.center);
        exitButton.center();
        exitButton.setTransform(true);
        exitButton.setFillParent(true);

        container.align(Align.center);

        container.setPosition(
                ((float)viewportWidth/7f) - (buttonSize/2f)
                ,((float)viewportHeight*5f/6f)  - (buttonSize/2f)
        );

        container.setDebug(true);
        this.addActor(container);

        elements.put("exitButton",exitButton);
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
