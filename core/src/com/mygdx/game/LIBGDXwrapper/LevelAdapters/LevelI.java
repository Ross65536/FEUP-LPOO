package com.mygdx.game.LIBGDXwrapper.LevelAdapters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;

public interface LevelI {
    /**
     * function ticks the GameWorld, gets the objects from it and draws them
     * @param deltaT
     * @param gameCamera
     */
    void update(float deltaT, OrthographicCamera gameCamera);
    void setupCamera(OrthographicCamera gameCamera);

    /**
     * Wait for asset manager to finish loading and loads the texture fields
     * @param gameAssetManager
     */
    void getLevelAssets(AssetManager gameAssetManager);

    /**
     * mov is multiplied by the maximum poosible speed of the hero to get the horizontal speed of the hero
     *
     * @param mov goes from -1.0 to 1.0, negative being movement to the left and postive to the right, 0.0 stops hero's horintal movement.
     */
    void setHeroXMovement(double mov);
}

