package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;

public class UISkinAssetHandler {

    private UISkinAssetHandler uiAssetHandler;

    private final static String UIAssetsPath = "UIAssets/UIAssets";

    private Skin skin;

    private AssetManager assetManager;

    public UISkinAssetHandler(AssetManager assetManager){
        this.assetManager = assetManager;
        loadAssets();
    }

    private void loadAssets(){
        /*// RESORCES
        FileHandleResolver resolver = new InternalFileHandleResolver();
        app.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        app.assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter params = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        params.fontFileName = "font/stay_writer.ttf";
        params.fontParameters.size = 30;
        app.assetManager.load("stay_writer_30.ttf", BitmapFont.class, params);*/
        // Queue Skin

        System.out.println("asdas");
        assetManager.load(UIAssetsPath+".atlas", TextureAtlas.class);
        assetManager.finishLoading();


        assetManager.load(UIAssetsPath+".json", Skin.class, new SkinLoader.SkinParameter(UIAssetsPath+".atlas"/*,Resources*/));
        assetManager.finishLoading();

        try {
            skin = skin.get(UIAssetsPath + ".json", Skin.class);
        }catch (Exception exception){
            System.out.println(exception.toString() + ": " + exception.getLocalizedMessage());
        }

        System.out.println("4541");
    }

    public <T> T getUIAsset(String nameAsset, Class<T> type){
        return skin.get(nameAsset,type);
    }

    public static String getSkinName(){
        return UIAssetsPath+".json";
    }

}
