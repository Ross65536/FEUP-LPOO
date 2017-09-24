package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.mygdx.game.MagicNumbers;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.mygdx.game.LIBGDXwrapper.gameAdapter.FontLoader.FONTS.*;

public class FontLoader {

    private static TreeMap<FONTS, int[]> fonts;

    public enum FONTS {COASTERSHADOW("coastershadow",Color.ORANGE, Color.BLACK, 5),COASTERSHADOW_BLACK("coastershadow",Color.BLACK, Color.BLACK, MagicNumbers.COASTERSHADOW_BLACK_BORDER_SIZE);

        public String fontName;

        public Color backColor;

        public Color borderColor;

        public int borderWidth;

        FONTS(String fontName, Color colorFont, Color border, int borderWidth){
            this.fontName = fontName;
            this.backColor = colorFont;
            this.borderColor = border;
            this.borderWidth = borderWidth;
        }
    };

    static{

        double screenWidth = Gdx.graphics.getWidth();

        fonts = new TreeMap<FONTS, int[]>();



        //Coastershadow font
        fonts.put(COASTERSHADOW, new int[] {
                (int)(screenWidth/10.0),
                (int)(screenWidth/20.0),
                (int)(screenWidth/30.0),
                (int)(screenWidth/40.0)}
        );

        fonts.put(COASTERSHADOW_BLACK, new int[] {
                (int)(screenWidth/10.0),
                (int)(screenWidth/20.0),
                (int)(screenWidth/30.0),
                (int)(screenWidth/40.0),}
        );


    }

    public static Integer getSize(FONTS font , int index){

        if((fonts.get(font).length <= index) && !fonts.containsKey(font)){
            return null;
        }
        return fonts.get(font)[index];
    }

    public static String getFontName(String fontName, int size){
        return fontName + size + ".ttf";
    }

    public static void loadFonts(AssetManager assetManager, Skin skin){


        for (Map.Entry<FONTS,int[]> font: fonts.entrySet()) {
            FONTS fontT = font.getKey();
            for (int size :font.getValue()) {
                loadFont(assetManager, skin, fontT, size);
            }
        }

    }

    public static void loadFont(AssetManager assetManager, Skin skin, FONTS fontArg, int size){

        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();

        params.fontFileName = "fonts/" + fontArg.fontName +".ttf";
        params.fontParameters.size = size;
        params.fontParameters.borderColor = fontArg.borderColor;
        params.fontParameters.borderWidth = fontArg.borderWidth;
        params.fontParameters.color = fontArg.backColor;
        params.fontParameters.minFilter = Texture.TextureFilter.Linear;
        params.fontParameters.magFilter = Texture.TextureFilter.Linear;

        String fontName = fontArg.fontName+size+".ttf";

        assetManager.load(fontName, BitmapFont.class, params);

        try {
            assetManager.finishLoadingAsset(fontName);
        }catch (Exception exception){

            System.out.println(exception.toString());
        }
        skin.add(fontName, assetManager.get(fontName, BitmapFont.class));
    }

}
