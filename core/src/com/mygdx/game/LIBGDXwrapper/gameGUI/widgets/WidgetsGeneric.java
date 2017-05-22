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
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;

public class WidgetsGeneric{

    public static Label getLabel(Skin skin, String text, String font, String backgroundImage){
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

        if(backgroundImage!=null) {
            loadToSkin(backgroundImage, backgroundImage, skin);
            labelStyle.background = skin.getDrawable(backgroundImage);
        }

       return new Label(text,labelStyle);
    }

    public static Cell<Label> loadLabel(Skin skin, Table table, String text, String font, String backgroundImage) {
        Label label = getLabel(skin, text, font, backgroundImage);
        return table.add(label);
    }

    public static Button getButton(Skin skin, String upFileName, String downFileName){
        loadToSkin(downFileName, downFileName, skin);
        loadToSkin(upFileName, upFileName, skin);
        Button.ButtonStyle button = new Button.ButtonStyle();
        button.down = skin.getDrawable(downFileName);
        button.up = skin.getDrawable(upFileName);

        return new Button(button);
    }

    public static Cell<Button> loadButton(Skin skin, Table table, String upFileName, String downFileName) {

        Button button = getButton(skin,upFileName,downFileName);

        return table.add(button);
    }



    public static Cell<ScrollPane> loadTextArea(Skin skin, Table table, String text, String font, String background) {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);
        skin.add(font,bitmapFont);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

        if(background!=null) {
            loadToSkin(background, background, skin);
            labelStyle.background = skin.getDrawable(background);
        }
        float screenWidth = DeviceConstants.MENU_VIEWPORT;
        float screenHeight = (float)DeviceConstants.INVERTED_SCREEN_RATIO * DeviceConstants.MENU_VIEWPORT;

        Label label = new Label(text,labelStyle);
        label.setWrap(true);

        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.layout();
        scrollPane.setTouchable(Touchable.enabled);
        return table.add(scrollPane);
    }

    public static Cell<Image> loadImage(Skin skin, Table table, String nameImage) {
        loadToSkin(nameImage, nameImage, skin);
        Image image = new Image(skin, nameImage);
        return table.add(image);
    }

    public static void loadToSkin(String name, String file, Skin skin){
        if(!skin.has(name,Texture.class))
            skin.add(name, new Texture(Gdx.files.internal(file)));
    }

}
