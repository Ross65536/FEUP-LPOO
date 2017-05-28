package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;

/**
 * This class holds static fucntions with generic funcions called for all menus.
 */
public class WidgetsGeneric{

    public static Label getLabel(Skin skin, String text, String font, String backgroundImage){

        BitmapFont bitmapFont = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("coastershadowfont.ttf",BitmapFont.class);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

       return new Label(text,labelStyle);
    }

    public static Cell<Label> loadLabel(Skin skin, Table table, String text, String font, String backgroundImage) {
        Label label = getLabel(skin, text, font, backgroundImage);
        return table.add(label);
    }

    public static Button getButton(Skin skin, Button.ButtonStyle buttonStyle){

        Button.ButtonStyle button = buttonStyle;

        return new Button(button);
    }



    public static Cell<Button> loadButton(Skin skin, Table table, Button.ButtonStyle buttonStyle) {

        Button button = getButton(skin,buttonStyle);

        return table.add(button);
    }



    public static Cell<ScrollPane> loadTextArea(Table table, String text, String font) {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);

        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

        Label label = new Label(text,labelStyle);
        label.setWrap(true);

        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.layout();
        scrollPane.setTouchable(Touchable.enabled);
        return table.add(scrollPane);
    }

    public static Cell<Image> loadImage(Table table, Drawable background) {
        Image image = new Image(background);
        return table.add(image);
    }

    public static void loadToSkin(String name, String file, Skin skin){
        if(!skin.has(name,Texture.class))
            skin.add(name, new Texture(Gdx.files.internal(file)));
    }

}
