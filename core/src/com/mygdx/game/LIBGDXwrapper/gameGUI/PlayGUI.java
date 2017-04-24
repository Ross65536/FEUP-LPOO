package com.mygdx.game.LIBGDXwrapper.gameGUI;

public class PlayGUI extends AbstractGUI{

    public PlayGUI(MenuManager menuManager){
        super(menuManager);

        addComponents();
        addInputProcessors();
    }

    protected void addComponents(){
        menuComponets.add(new PlayGUIComponent1(menuManager));
        menuComponets.add(new PlayGUIComponent2(menuManager));
    }


}
