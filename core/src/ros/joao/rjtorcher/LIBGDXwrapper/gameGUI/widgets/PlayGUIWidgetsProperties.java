package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FontLoader;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import ros.joao.rjtorcher.MagicNumbers;

public class PlayGUIWidgetsProperties extends WidgetsGeneric {

    public Button loadBackToMenuButton(Table table, Skin skin){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playBackToMenuButton",Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
                //pos. and location
                .prefWidth(screenWidth / 4)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)
                .expand()
                .bottom()
                .fillX()
                .getActor();
    }

    public Button loadSettingsButton(Table table,Skin skin){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playSettingsButton",Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
                //pos. and location
                .prefWidth(screenWidth / 4)
                .minWidth(screenWidth / 10)
                .maxWidth(screenWidth)
                .prefHeight(screenHeight / 6)
                .minHeight(screenHeight / 20)
                .maxHeight(screenHeight)
                .expand()
                .bottom()
                .fillX()
                .getActor();
    }

    public Label loadTopLabel(Table table, String modeName){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Label label = loadLabel(table
                //images
                ,modeName
                ,FontLoader.FONTS.COASTERSHADOW
                ,0)
                //pos. and location
                .width(screenWidth / 3.5f)
                .height(screenHeight / 8)
                .expandX()
                .top()
                .padTop(screenHeight/30)
                .padBottom(screenHeight/30)
                .colspan(3)
                .center()
                .getActor();
        label.setAlignment(Align.center);
/*
        label.setFontScale(1f);
        float xRatio = (screenWidth/3.5f)/label.getWidth();
        float yRatio = (screenHeight/8f)/label.getHeight();
        label.setFontScale(xRatio,yRatio);
*/
        return label;
    }

    public Button loadPlayButton(Table table, Skin skin){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("playModeButton",Button.ButtonStyle.class);

        return loadButton(skin, table
                //images
                ,buttonStyle)
                //pos. and location
                .width(screenWidth / 3)
                .height(screenHeight / 6)
                .expandX()
                .top()
                .getActor();
    }

    public ScrollPane loadTextAreaTutorial(Table table, String text){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        ScrollPane textArea = loadTextArea(table,
                text,
                FontLoader.FONTS.COASTERSHADOW,
                1)
                .fill()
                .expand()
                .colspan(3)
                .height(screenHeight - (screenHeight / 3) - (screenHeight / 8) - (screenHeight/15))
                .padLeft(MagicNumbers.PLAY_MENU_PREFFERED_X_BORDER *screenWidth)
                .padRight(MagicNumbers.PLAY_MENU_PREFFERED_X_BORDER *screenWidth)

                .getActor();

        ScrollPane.ScrollPaneStyle styleScroll = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("vScrollKnobPlayMenu",ScrollPane.ScrollPaneStyle.class);

        textArea.setStyle(styleScroll);
        textArea.setScrollingDisabled(true,false);

        return textArea;
    }

    public Image loadTextbackImageTable(Table table, Drawable background){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Image image = loadImage(table,
                background)
                .grow()
                .height(screenHeight / 6)
                .colspan(3)
                //pos. and location
                .getActor();


        return image;
    }

    public Image loadSwipeImageTable(Table table, Drawable background){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        Image image = loadImage(table,
                background)
                .grow()
                .width(screenWidth / 3)
                .height(screenHeight / 6)
                //pos. and location
                .getActor();
        return image;
    }

}
