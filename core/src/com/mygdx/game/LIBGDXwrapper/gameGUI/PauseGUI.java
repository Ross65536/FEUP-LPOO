package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;

public class PauseGUI extends AbstractGUI{

    public static enum pauseType {ENDGAME,PAUSE};

    private pauseType thisPauseType;

    IGameWorldAdapter gameScreen;

    public PauseGUI(MenuManager menuManager, IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        super(menuManager);
        this.gameScreen = gameScreen;
        this.thisPauseType = pauseType;
        addComponents();
        addInputProcessors();
    }

    protected void createWidgets(){
        widgetsProperties = new PauseGUIWidgetsProperties();
        widgetsInput = new PauseGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new PauseGUIComponent(gameScreen, menuManager, widgetsProperties, widgetsInput, thisPauseType));
    }

    @Override
    public void draw(){
        gameScreen.updateScreen(0f);
        super.draw();
    }

    public IGameWorldAdapter getGameScreen(){
        return gameScreen;
    }

    public pauseType getPauseType(){
        return thisPauseType;
    }

    public void update(){
        ((PauseGUIComponent)menuComponets.get(0)).update();
    }

    public String toString(){
        return "PauseGUI";
    }
}
