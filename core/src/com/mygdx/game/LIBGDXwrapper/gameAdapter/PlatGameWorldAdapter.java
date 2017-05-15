package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.LightVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.PlatformVisualHandler;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.Vector2D;

public class PlatGameWorldAdapter extends AbstractGameWorldAdapter{

    private double cameraHeight;

    private DummyEnemyVisualsHandler dummyEnemyVisualsHandler;

    private LightVisualHandler lightVisualHandler;

    private PlatformVisualHandler platformVisualHandler;

    public PlatGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);

        drawBatch = new SpriteBatch();
        drawBatch.enableBlending();//default


        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        dummyEnemyVisualsHandler = new DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

        lightVisualHandler = new LightVisualHandler(gameLogicWorld, drawBatch);

        platformVisualHandler = new PlatformVisualHandler(gameLogicWorld,drawBatch);

    }

    @Override
    public void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();
        final float heroYPos = (float) hero.getYPos();
        final float heroYDim = (float) hero.getYDim();

        gameCamera.position.set(heroXPos + heroXDim/2, heroYPos + heroYDim/2, 0);
        gameCamera.update();
    }

    @Override
    public Vector2D getCameraSetup () {
        return new Vector2D(cameraWidth, cameraHeight); //camera has maximum world height
    }



    /**
     * function ticks the GameWorld, gets the objects information from it and draws them
     * @param deltaT
     */
    @Override
    public void updateScreen(float deltaT) {
        super.updateScreen(deltaT);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();

        dummyEnemyVisualsHandler.drawEnemies();

        drawHero();
        platformVisualHandler.drawPlatforms();
        drawBatch.end();

        lightVisualHandler.drawLight(gameCamera);

    }

    @Override
    public void resize(int width, int height){
        super.resize(width,height);
        if(lightVisualHandler!=null)
            lightVisualHandler.resize( width, height);
    }

    @Override
    public void dispose(){
        super.dispose();
        if(lightVisualHandler!=null)
            lightVisualHandler.dispose();
    }
}