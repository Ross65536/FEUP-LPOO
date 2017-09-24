package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsInput;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsInput;

import java.util.HashMap;

import static ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.PlayGUIComponent2.ComponentsNames.*;

public class PlayGUIComponent2 extends AbstractSingleStageGUI {

    HashMap<String, Object> elements;

    Table table;

    protected enum ComponentsNames {playButtonS, playButtonW, topLabelS, topLabelW, textAreaS, textAreaW, imageS, imageW};

    public PlayGUIComponent2(MenuManager menuManager, ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric widgetsProperties, WidgetsInput widgetsInput){
        super(menuManager, widgetsProperties, widgetsInput);
        table = new Table(skin);

        elements = new  HashMap<String, Object>();

        this.addActor(table);


        table.setTouchable(Touchable.enabled);
        this.setViewport(
                new StretchViewport(
                        (int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT)
                        ,(int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT*  ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO)
                )
        );
        table.setWidth((int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT) * CommonConsts.NUMBER_OF_GAMEMODES);
        table.setHeight((int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT*  ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO));
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT), (int)(ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT *  ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO));

        loadWidgets();
        loadInputlisteners();
    }


    protected void loadWidgets(){

        ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties playGUIWidgetsProperties = ((ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties)widgetsProperties);

        table.center().top();

        loadLabels(playGUIWidgetsProperties);
        table.row();

        loadPlayButton(playGUIWidgetsProperties);
        table.row();

        loadTutorials(playGUIWidgetsProperties);

    }

    protected void loadInputlisteners(){
        PlayGUIWidgetsInput playGUIWidgetsInput = ((PlayGUIWidgetsInput)widgetsInput);

        playGUIWidgetsInput.loadPlayMode2Button(
                (Button)elements.get(playButtonS.toString()),
                menuManager
        );

        playGUIWidgetsInput.loadPlayMode1Button(
                (Button)elements.get(playButtonW.toString()),
                menuManager
        );


        playGUIWidgetsInput.loadSlideFunction(
                table,
                menuManager,
                (OrthographicCamera)this.getCamera()
        );

    }

    private void loadLabels(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties playGUIWidgetsProperties){

        elements.put(topLabelS.toString(),  playGUIWidgetsProperties.loadTopLabel(table, "Platformer"));
        elements.put(topLabelW.toString(),  playGUIWidgetsProperties.loadTopLabel(table, "Survival"));

    }

    private void loadPlayButton(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties playGUIWidgetsProperties){

        Drawable swipeLeft = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("swipeLeft",Drawable.class);

        Drawable swipeRight = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("swipeRight",Drawable.class);

        elements.put(swipeLeft.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                swipeLeft));

        elements.put(playButtonS.toString(),  playGUIWidgetsProperties.loadPlayButton(table,skin));

        elements.put(swipeRight.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                swipeRight));

        elements.put(swipeLeft.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                               swipeLeft));

        elements.put(playButtonW.toString(),  playGUIWidgetsProperties.loadPlayButton(table,skin));

        elements.put(swipeRight.toString(), playGUIWidgetsProperties.loadSwipeImageTable(table,
                swipeRight));


    }

    private void loadTutorials(ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets.PlayGUIWidgetsProperties playGUIWidgetsProperties){
        String tutorialS = Gdx.files.internal("tutorialS.txt").readString();
        elements.put(textAreaS.toString(), playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialS
                ));

        String tutorialW = new String(Gdx.files.internal("tutorialW.txt").readString());
        elements.put(textAreaW.toString(), playGUIWidgetsProperties.loadTextAreaTutorial(table,
                tutorialW
                ));

        table.row();

        Drawable background = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("backGroundButtonImg",Drawable.class);

        elements.put(imageS.toString(), playGUIWidgetsProperties.loadTextbackImageTable(table,
                background));

        elements.put(imageW.toString(), playGUIWidgetsProperties.loadTextbackImageTable(table,
                background));
    }



    @Override
    public String toString(){
        return "PlayMenu";
    }
}
