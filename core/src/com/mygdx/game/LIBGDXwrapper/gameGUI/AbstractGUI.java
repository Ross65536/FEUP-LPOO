package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;


public abstract class AbstractGUI extends InputMultiplexer{

    ArrayList<AbstractSingleStageGUI> menuComponets;

    protected MenuManager menuManager;

    protected abstract void addComponents();

    public AbstractGUI(MenuManager menuManager){
        menuComponets = new ArrayList<AbstractSingleStageGUI>();
        this.menuManager = menuManager;
    }

    protected void addInputProcessors(){
        for(InputProcessor input: menuComponets)
            this.addProcessor(input);
    }

    void act(float delta){
        for(Stage stage: menuComponets)
            stage.act(delta);
    }

    void draw(){
        for(Stage stage: menuComponets)
            stage.draw();
    }

    public void updateViewPorts(int width,int height, boolean centerCamera){
        for(Stage stage: menuComponets)
            stage.getViewport().update(width,height,centerCamera);
    }
}
