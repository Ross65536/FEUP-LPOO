package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Class to manage the Skin used to make the user interface.
 */
public class UISkinAssetHandler {

    /**
     * Path to all UI files (.atlas, .JSON, .png)
     */
    public final static String UIAssetsPath = "UIAssets/UIAssets";

    private Skin skin;

    private AssetManager assetManager;

    /**
     * Constructor.
     * @param assetManager
     */
    public UISkinAssetHandler(AssetManager assetManager){
        this.assetManager = assetManager;
        loadAssets();
    }

    /**
     * Function that loads the texture altas and the skin of the GUI
     */
    private void loadAssets(){

        assetManager.load(UIAssetsPath+".atlas", TextureAtlas.class);
        assetManager.finishLoadingAsset(UIAssetsPath+".atlas");

        assetManager.load(UIAssetsPath+".json", Skin.class, new SkinLoader.SkinParameter(UIAssetsPath+".atlas"/*,Resources*/));
        assetManager.finishLoadingAsset(UIAssetsPath+".json");

        try {
            skin = assetManager.get(UIAssetsPath + ".json", Skin.class);
        }catch (Exception exception){//shoudn't be done
            System.out.println(exception.toString() + ": " + exception.getLocalizedMessage());
        }


        loadFonts();
    }

    /**
     * Loads the Fonts.
     */
    private void loadFonts(){
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "fonts/coastershadow.ttf";
        params.fontParameters.size = 75;

        assetManager.load("coastershadowfont.ttf", BitmapFont.class, params);

        try {
            assetManager.finishLoadingAsset("coastershadowfont.ttf");
        }catch (Exception exception){

            System.out.println(exception.toString());
        }
        skin.add("coastershadowfont.ttf", assetManager.get("coastershadowfont.ttf", BitmapFont.class));



    }

    /**
     * Returns a asset from the skin.
     * @param nameAsset name of the asset
     * @param type type  of the asset
     * @param <T>
     * @return
     */
    public <T> T getUIAsset(String nameAsset, Class<T> type){
        return skin.get(nameAsset,type);
    }

    /**
     * Returns the name of the JSON file.
     * @return name of the JSON file.
     */
    public static String getSkinName(){
        return UIAssetsPath+".json";
    }

    /**
     * Disposes of the skin.
     */
    public void dispose(){
        skin.dispose();
    }
}
