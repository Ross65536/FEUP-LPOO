package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.ScreenLightsVisualHandler;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature;


/**
 * Class that does the actual drawing of objects.
 * Sends input to Hero.
 */
public class SurvivalGameWorldAdapter extends AbstractGameWorldAdapter{

    private DummyEnemyVisualsHandler dummyEnemyVisuals;

    private ScreenLightsVisualHandler /**/lightVisualHandler;

    /**
     * Constrcutor
     * @param worldDims dimensison of hte world
     * @param gameLogicWorld the world object
     */
    public SurvivalGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);

        dummyEnemyVisuals = new DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

        lightVisualHandler = new ScreenLightsVisualHandler(drawBatch);

        this.cameraHeight = worldYDim;

        addLights();
    }

    private void addLights(){
        if(!(gameLogicWorld instanceof HeroLightFeature)){
            System.out.println("this world does not support the hero light feature");
        }else {
            lightVisualHandler.addNewLight(((HeroLightFeature) gameLogicWorld).getLightInfo());
        }
    }

    /**
     * Updates the camera, which should display the action correctly
     * @param hero hero informaiton
     * @param gameCamera camera
     */
    public void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();

        gameCamera.position.set(heroXPos + heroXDim/2, (float) worldYDim / 2, 0);
        gameCamera.update();

    }

    /**
     *
     * @return dimensiosn of the camera, which enable to setup the camera correctly
     */
    public Vector2D getCameraSetup () {
        cameraWidth = worldYDim * DeviceConstants.SCREEN_RATIO;
        return new Vector2D(cameraWidth, worldYDim); //camera has maximum world height
    }

    /**
     * function ticks the GameWorld, gets the objects information from it and draws them
     * @param deltaT
     */
    @Override
    public void updateScreen(float deltaT) {
        super.updateScreen(deltaT );

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();
        drawBackground();
        dummyEnemyVisuals.drawEnemies();
        drawHero();
        drawBatch.end();

        lightVisualHandler.drawLight(gameCamera);

    }

    /**
     * Resizes the game
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height){
        super.resize(width,height);
        if(lightVisualHandler != null)
            lightVisualHandler.resize( width, height);
    }

    @Override
    protected double getBackgroundYStart(double backgroundYDim) {
        return 0.0;
    }
}

