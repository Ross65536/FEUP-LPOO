package com.mygdx.game.LIBGDXwrapper.gameGUI;

public class MainGUI extends AbstractGUI{

    public MainGUI(MenuManager menuManager){
        super(menuManager);

        addComponents();
        addInputProcessors();
    }

    private void addComponents(){
        this.addComponent(new MainGUIComponet(menuManager));
    }

}
