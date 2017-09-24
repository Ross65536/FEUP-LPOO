package ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FontLoader;
import ros.joao.rjtorcher.MagicNumbers;


public class AboutGUIWidgetsProperties extends WidgetsGeneric {


    public ScrollPane loadAboutMenuText(Table table){
        float screenWidth = ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float) ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.INVERTED_SCREEN_RATIO * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.MENU_VIEWPORT;

        ScrollPane textArea = loadTextArea(table,
                MagicNumbers.ABOUT_MENU_TEXT,
                FontLoader.FONTS.COASTERSHADOW,
                1)
                .fill()
                .expand()
                .height(screenHeight * MagicNumbers.ABOUT_MENU_HEIGHT_MULD)
                .padLeft(MagicNumbers.ABOUT_MENU_PREFFERED_X_BORDER *screenWidth)
                .padRight(MagicNumbers.ABOUT_MENU_PREFFERED_X_BORDER *screenWidth)

                .getActor();



        return textArea;
    }
}