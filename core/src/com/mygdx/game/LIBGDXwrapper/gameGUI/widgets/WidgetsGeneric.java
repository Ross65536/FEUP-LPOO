package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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

    public static Cell<Label> loadLabel(Table table, String text, String font) {

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);
        Label.LabelStyle labelStyle = new Label.LabelStyle();

        labelStyle.font = bitmapFont;

        Label label = new Label(text,labelStyle);
        label.setFontScale(2);
        label.setAlignment(0);
        return table.add(label);
    }

    public static Cell<TextArea> loadTextArea(Table table, String text, String font, String background) {
        Skin skin = new Skin();

        loadToSkin("background", background,skin);

        BitmapFont bitmapFont = new BitmapFont(Gdx.files.internal(font+".fnt"),
                Gdx.files.internal(font + ".png"), false);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.background = skin.getDrawable("background");
        textFieldStyle.font = bitmapFont;
        TextArea textArea = new TextArea(text, textFieldStyle);
        textArea.pack();
        return table.add(textArea).fill();
    }

    private static void loadToSkin(String name, String file, Skin skin){
        skin.add(name, new Texture(Gdx.files.internal(file)));
    }

}
