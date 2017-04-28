package com.mygdx.game.LIBGDXwrapper.gameGUI;

public class PlayGUI extends AbstractGUI{

    public PlayGUI(MenuManager menuManager){
        super(menuManager);

        addComponents();
        addInputProcessors();
    }


    private void addComponents(){
        this.addComponent(new PlayGUIComponent1(menuManager));
        this.addComponent(new PlayGUIComponent2(menuManager));
    }


}
