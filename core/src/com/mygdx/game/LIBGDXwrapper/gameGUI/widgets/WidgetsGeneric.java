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
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.game.LIBGDXwrapper.GameScreen;

public class WidgetsGeneric{

    public static Cell<Button> loadButton(Table table, String upFileName, String downFileName) {
        Skin skin = new Skin();
        loadToSkin("downImage", downFileName, skin);
        loadToSkin("upImage", upFileName, skin);
        Button.ButtonStyle button = new Button.ButtonStyle();
        button.down = skin.getDrawable("downImage");
        button.up = skin.getDrawable("upImage");

        Button playButton = new Button(button);

        return table.add(playButton);
    }

    public static Cell<Label> loadLabel(Table table, String text, String font, String backgroundImage) {

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

        if(backgroundImage!=null) {
            Skin skin = new Skin();
            loadToSkin("background", backgroundImage, skin);
            labelStyle.background = skin.getDrawable("background");
        }

        Label label = new Label(text,labelStyle);
        return table.add(label);
    }

    public static Cell<ScrollPane> loadTextArea(Table table, String text, String font, String background) {
        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

        if(background!=null) {
            Skin skin = new Skin();
            loadToSkin("background", background, skin);
            labelStyle.background = skin.getDrawable("background");
        }

        Label label = new Label(text,labelStyle);
        ScrollPane scrollPane = new ScrollPane(label);
        scrollPane.layout();
        scrollPane.setTouchable(Touchable.enabled);
        return table.add(scrollPane);
    }

    private static void loadToSkin(String name, String file, Skin skin){
        skin.add(name, new Texture(Gdx.files.internal(file)));
    }

}
