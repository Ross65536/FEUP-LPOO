package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.LightVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.PlatformVisualHandler;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.Vector2D;

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
        final float heroXDim = (float) hero.getXDim();
        final float heroYCenter = (float) hero.getYCenter();
        final float heroXCenter = (float) hero.getXCenter();

        //camera Y
        float cameraYCenter;
        final float cameraYMinLimit = (float) worldYDim / 2;
        if (heroYCenter <= cameraYMinLimit)
            cameraYCenter = cameraYMinLimit;
        else
            cameraYCenter = heroYCenter;

        //camera X
        float cameraXCenter;
        final float cameraXMinLimit = (float) (cameraWidth / 2 + heroXDim * PathConstants.HERO_WORLD_EDGE_LEEWAY);
        final float cameraXMaxLimit = (float) (worldXDim - cameraWidth / 2 - PathConstants.HERO_WORLD_EDGE_LEEWAY/**/);
        if(heroXCenter <= cameraXMinLimit) //left edge
            cameraXCenter = cameraXMinLimit;
        else if(heroXCenter >= cameraXMaxLimit) //right edge
            cameraXCenter = cameraXMaxLimit;
        else
            cameraXCenter = heroXCenter;

        gameCamera.position.set(cameraXCenter, cameraYCenter, 0);
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
        drawBackground();
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
    protected double getBackgroundYStart(double backgroundYDim) {

        final HeroInfo heroInfo = gameLogicWorld.getHeroInfo();
        final double heroYCenter = heroInfo.getYCenter();
        if (heroYCenter < worldYDim / 2.0)
            return 0.0;
        else
        {
            final double Yintermediary = heroYCenter - worldYDim * 0.6;
            return floorDouble(Yintermediary, backgroundYDim);
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        if(lightVisualHandler!=null)
            lightVisualHandler.dispose();
    }
}