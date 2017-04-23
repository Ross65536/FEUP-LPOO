package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

public interface LevelI {
    void update(float deltaT, OrthographicCamera gameCamera);
    void setupCamera(OrthographicCamera gameCamera);
    void loadLevelAssets(AssetManager gameAssetManager);
    void finishLoadingAssets(AssetManager gameAssetManager);
    void unloadLevelAssets(AssetManager gameAssetManager);
}
