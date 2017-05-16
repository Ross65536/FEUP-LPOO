package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.PathConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.LightRechargerVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.ScreenLightsVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.PlatformVisualHandler;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature;

public class PlatGameWorldAdapter extends AbstractGameWorldAdapter{


    private DummyEnemyVisualsHandler dummyEnemyVisualsHandler;

    private ScreenLightsVisualHandler lightVisualHandler;

    private PlatformVisualHandler platformVisualHandler;

    private LightRechargerVisualHandler lightRechargerVisualHandler;

    public PlatGameWorldAdapter(final Vector2D cameraDims, final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);

        drawBatch = new SpriteBatch();
        drawBatch.enableBlending();//default


        this.cameraWidth = cameraDims.x;
        this.cameraHeight = cameraDims.y;

        dummyEnemyVisualsHandler = new DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

        lightVisualHandler = new ScreenLightsVisualHandler( drawBatch);

        platformVisualHandler = new PlatformVisualHandler(gameLogicWorld,drawBatch);

        lightRechargerVisualHandler = new LightRechargerVisualHandler(gameLogicWorld,drawBatch);

        addLights();
    }

    private void addLights(){
        if(!(gameLogicWorld instanceof HeroLightFeature)){
            System.out.println("this world does not support the hero light feature");
        }else {
            lightVisualHandler.addNewLight(((HeroLightFeature) gameLogicWorld).getLightInfo());
        }

        if(!(gameLogicWorld instanceof LightRechargerFeature)){
            System.out.println("this world does not support the recharger feature");
        }else
            lightVisualHandler.addNewLight(((LightRechargerFeature)gameLogicWorld).getItemLight());
    }


    @Override
    public void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();
        final float heroYPos = (float) hero.getYPos();
        final float heroYDim = (float) hero.getYDim();
        final float heroYCenter = heroYPos + heroYDim/2;

        float cameraYCenter;
        if (heroYCenter <= cameraHeight / 2f)
            cameraYCenter = (float) cameraHeight / 2f;
        else
            cameraYCenter = heroYCenter;

        gameCamera.position.set(heroXPos + heroXDim/2f, cameraYCenter, 0);
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
        drawWorldEdges();
        dummyEnemyVisualsHandler.drawEnemies();

        drawHero();
        platformVisualHandler.drawPlatforms();
        lightRechargerVisualHandler.drawLightRecharger();
        drawBatch.end();

        lightVisualHandler.drawLight(gameCamera);

    }

    @Override
    public void resize(int width, int height){
        super.resize(width,height);
        if(lightVisualHandler!=null)
            lightVisualHandler.resize( width, height);
    }

    private void drawWorldEdges(){

        final Texture borderTex = GameAssetHandler.getGameAssetHandler().getBorderTexture();
        final HeroInfo heroInfo = gameLogicWorld.getHeroInfo();

        final float backgroundYDim = (float) (cameraHeight * PathConstants.BACKGROUND_PORTION_OF_CAMERA_Y);
        final float backgroundXDim = (float) (backgroundYDim * PathConstants.BACKGROUND_ASPECT_RATIO);

        final float drawYMin = (float) getBackgroundYStart(backgroundYDim);

        final float maxY = (float) (drawYMin + 1.0 * (cameraHeight + backgroundYDim));

        if((heroInfo.getXPos()-cameraWidth/1.5f)<=0) {
            for (float drawY = drawYMin; drawY <= maxY; drawY += backgroundYDim) {
                drawBatch.draw(borderTex, -backgroundXDim,drawY, backgroundXDim, backgroundYDim);
            }
        }

        if((heroInfo.getXPos()+cameraWidth/1.5f)>=worldXDim) {
            for (float drawY = drawYMin; drawY <= maxY; drawY += backgroundYDim) {
                drawBatch.draw(borderTex, (float)worldXDim,drawY, backgroundXDim, backgroundYDim);
            }
        }
    }

    @Override
    protected double getBackgroundYStart(double backgroundYDim) {

        final HeroInfo heroInfo = gameLogicWorld.getHeroInfo();
        final double heroYCenter = heroInfo.getYCenter();
        if (heroYCenter < cameraHeight / 2.0)
            return 0.0;
        else
        {
            final double Yintermediary = heroYCenter - cameraHeight * 0.6;
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