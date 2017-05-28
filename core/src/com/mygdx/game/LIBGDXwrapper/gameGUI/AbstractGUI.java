package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.ArrayList;

/**
 * Main class for every menu. Can contain multiple stages.
 */
public abstract class AbstractGUI extends InputMultiplexer{

    protected ArrayList<InputProcessor> menuComponets;

    protected WidgetsGeneric widgetsProperties;

    protected WidgetsInput widgetsInput;

    protected MenuManager menuManager;

    /**
     * Add a new stage component to the menu.
     * @param component the stage component.
     */
    protected void addComponent(AbstractSingleStageGUI component){
        menuComponets.add(component);
    }

    /**
     * Constructor.
     * @param menuManager The menumanager.
     */
    public AbstractGUI(MenuManager menuManager){
        menuComponets = new ArrayList<InputProcessor>();
        this.menuManager = menuManager;
        createWidgets();
    }

    /**
     * Fucntions used to add all componts as input.
     */
    protected void addInputProcessors(){
        for(InputProcessor input: menuComponets)
            this.addProcessor(input);
    }

    /**
     * Abstract function needed in all GUIs to create the widgets.
     */
    protected abstract void createWidgets();

    //************************************************************//
    //*****************STAGE RELATED FUCNTIONS********************//
    //************************************************************//

    /**
     * Calls 'act' for all stages.
     * @param delta
     */
    public void act(float delta){

        for(InputProcessor stage: menuComponets) {
            if(stage instanceof Stage)
                ((Stage)stage).act(delta);
        }
    }

    /**
     * Class 'draw' for all stages.
     */
    public void draw(){
        for(int i = menuComponets.size()-1; i >=0; i--){
            ((Stage)menuComponets.get(i)).draw();
        }
    }


    /**
     * Updates the viewport for all stages.
     * @param width Screen width.
     * @param height Screen height.
     * @param centerCamera True if to center the screen.
     */
    public void updateViewPorts(int width,int height, boolean centerCamera){
        for(InputProcessor stage: menuComponets) {
            if (stage instanceof Stage) {
                ((Stage) stage).getViewport().update(width, height, centerCamera);
                ((Stage) stage).getViewport().getCamera().update();
                ((Stage) stage).getViewport().apply();
            }
        }
    }

    /**
     * Disposes all stages.
     */
    public void dispose(){
        for(InputProcessor comp: menuComponets){
            if(comp instanceof AbstractSingleStageGUI){
                ((AbstractSingleStageGUI)comp).dispose();
            }else
                if(comp instanceof AbstractGUI){
                    ((AbstractGUI)comp).dispose();
                }

        }
    }
}
