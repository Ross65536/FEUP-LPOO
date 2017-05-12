package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.LightVisualHandler;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;



/**
 * Class that does the actual drawing of objects.
 * Sends input to Hero.
 */
public class DiscGameWorldAdapter extends AbstractGameWorldAdapter{

    private DummyEnemyVisualsHandler dummyEnemyVisuals;

    private LightVisualHandler lightVisualHandler;

    public DiscGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);
    }

    public void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();

        gameCamera.position.set(heroXPos + heroXDim/2, (float) worldYDim / 2, 0);
        gameCamera.update();

        dummyEnemyVisuals = new DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

        lightVisualHandler = new LightVisualHandler(gameLogicWorld, drawBatch);
    }

    public Vector2D getCameraSetup () {
        cameraWidth = worldYDim * DeviceConstants.SCREEN_RATIO;
        return new Vector2D(cameraWidth, worldYDim); //camera has maximum world height
    }

    /**
     * function ticks the GameWorld, gets the objects information from it and draws them
     * @param deltaT
     * @param gameCamera
     */
    public void update(float deltaT, OrthographicCamera gameCamera) {
        super.update(deltaT, gameCamera);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();
        dummyEnemyVisuals.drawEnemies();
        drawHero();
        drawBatch.end();


        lightVisualHandler.drawLight(gameCamera);
    }

}

