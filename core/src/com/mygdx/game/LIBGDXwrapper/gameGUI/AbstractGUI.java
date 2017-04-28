package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;


public abstract class AbstractGUI extends InputMultiplexer{

    ArrayList<InputProcessor> menuComponets;

    protected MenuManager menuManager;

    protected void addComponent(AbstractSingleStageGUI component){
        menuComponets.add(component);
    }

    public AbstractGUI(MenuManager menuManager){
        menuComponets = new ArrayList<InputProcessor>();
        this.menuManager = menuManager;
    }

    protected void addInputProcessors(){
        for(InputProcessor input: menuComponets)
            this.addProcessor(input);
    }



    //************************************************************//
    //*****************STAGE RELATED FUCNTIONS********************//
    //************************************************************//
    void act(float delta){
        for(InputProcessor stage: menuComponets) {
            if(stage instanceof Stage)
                ((Stage)stage).act(delta);
        }
    }

    void draw(){
        for(InputProcessor stage: menuComponets)
            if(stage instanceof Stage)
                ((Stage)stage).draw();
    }

    public void updateViewPorts(int width,int height, boolean centerCamera){
        for(InputProcessor stage: menuComponets)
            if(stage instanceof Stage)
                ((Stage)stage).getViewport().update(width,height,centerCamera);
    }
}
