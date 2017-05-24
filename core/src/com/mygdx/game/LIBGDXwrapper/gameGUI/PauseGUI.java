package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties;

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
        gameScreen.updateGraphics(0f);
        super.draw();
    }

    public String toString(){
        return "PauseGUI";
    }
}
