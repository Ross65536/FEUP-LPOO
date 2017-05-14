package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

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

    public static Cell<Button> loadButton(Skin skin, Table table, String upFileName, String downFileName) {
        loadToSkin(downFileName, downFileName, skin);
        loadToSkin(upFileName, upFileName, skin);
        Button.ButtonStyle button = new Button.ButtonStyle();
        button.down = skin.getDrawable(downFileName);
        button.up = skin.getDrawable(upFileName);

        Button playButton = new Button(button);

        return table.add(playButton);
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

        Label label = new Label(text,labelStyle);
        label.setWrap(true);
        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.layout();
        scrollPane.setTouchable(Touchable.enabled);
        return table.add(scrollPane);
    }

    public static void loadToSkin(String name, String file, Skin skin){
        skin.add(name, new Texture(Gdx.files.internal(file)));
    }

}
