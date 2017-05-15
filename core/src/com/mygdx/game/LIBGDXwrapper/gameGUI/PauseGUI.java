package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.IGameWorldAdapter;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties;

public class PauseGUI extends AbstractGUI{

    IGameWorldAdapter gameScreen;

    public PauseGUI(MenuManager menuManager, IGameWorldAdapter gameScreen){
        super(menuManager);
        this.gameScreen = gameScreen;
        addComponents();
        addInputProcessors();
    }

    protected void createWidgets(){
        widgetsProperties = new PauseGUIWidgetsProperties();
        widgetsInput = new PauseGUIWidgetsInput();
    }

    private void addComponents(){
        this.addComponent(new PauseGUIComponent(menuManager, widgetsProperties, widgetsInput));
    }

    @Override
    public void draw(){
        gameScreen.updateScreen(0f);
        super.draw();
    }

    public String toString(){
        return "PauseGUI";
    }
}
