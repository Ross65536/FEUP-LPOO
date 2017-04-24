package com.mygdx.game.LIBGDXwrapper.gameGUI;

public class MainGUI extends AbstractGUI{

    public MainGUI(MenuManager menuManager){
        super(menuManager);

        addComponents();
        addInputProcessors();
    }

    protected void addComponents(){
        menuComponets.add(new MainGUIComponet(menuManager));
    }

}
