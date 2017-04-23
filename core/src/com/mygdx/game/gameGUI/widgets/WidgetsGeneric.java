package com.mygdx.game.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.mygdx.game.LIBGDXwrapper.GameScreen;

public class WidgetsGeneric{

    public static Cell<Button> loadButton(Table table, String upName, String downName, String upFileName, String downFileName) {
        Skin skin = new Skin();
        loadToSkin(downName, downFileName, skin);
        loadToSkin(upName, upFileName, skin);
        Button.ButtonStyle playButtonStyle = new Button.ButtonStyle();
        playButtonStyle.down = skin.getDrawable(downName);
        playButtonStyle.up = skin.getDrawable(upName);

        Button playButton = new Button(playButtonStyle);

        return table.add(playButton);
    }

    private static void loadToSkin(String name, String file, Skin skin){
        skin.add(name, new Texture(Gdx.files.internal(file)));
    }

}
