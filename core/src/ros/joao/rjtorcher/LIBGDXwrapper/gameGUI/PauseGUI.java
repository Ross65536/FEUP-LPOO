package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsInput;

public class PauseGUI extends AbstractGUI{

    public static enum pauseType {ENDGAME,PAUSE};

    private pauseType thisPauseType;

    ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter gameScreen;

    public PauseGUI(MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        super(menuManager);
        this.gameScreen = gameScreen;
        this.thisPauseType = pauseType;
        addComponents();
        addInputProcessors();
    }

    protected void createWidgets(){
        widgetsProperties = new ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PauseGUIWidgetsProperties();
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

    public void reciclePauseGUI(ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter gameScreen, PauseGUI.pauseType pauseType){
        this.gameScreen = gameScreen;

        if(pauseType != thisPauseType){
            ((PauseGUIComponent)menuComponets.get(0)).remakeLabels(pauseType, gameScreen);
            thisPauseType = pauseType;
        }else {
            updateScore();
        }
    }


    public ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.IGameWorldAdapter getGameScreen(){
        return gameScreen;
    }

    public pauseType getPauseType(){
        return thisPauseType;
    }

    public void updateScore(){
        ((PauseGUIComponent)menuComponets.get(0)).update(gameScreen);
    }

    public String toString(){
        return "PauseGUI";
    }
}
