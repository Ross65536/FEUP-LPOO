package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/** A ScalingViewport that uses {@link Scaling#fit} so it keeps the aspect ratio by scaling the world up to fit the screen, adding
 * black bars (letterboxing) for the remaining space.
 * @author Daniel Holderbaum
 * @author Nathan Sweet */
public class CustomViewport extends ScalingViewport {
    /** Creates a new viewport using a new {@link OrthographicCamera}. */
    public CustomViewport (float worldWidth, float worldHeight) {
        super(Scaling.none, worldWidth, worldHeight);
    }

    public CustomViewport (float worldWidth, float worldHeight, Camera camera) {
        super(Scaling.none, worldWidth, worldHeight, camera);
    }
}
