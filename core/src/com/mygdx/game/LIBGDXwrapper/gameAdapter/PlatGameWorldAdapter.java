package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.PlatWorld;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.ArrayList;

/**
 * Created by João on 06/05/2017.
 */

public class PlatGameWorldAdapter extends AbstractGameWorldAdapter{

    protected double cameraHeight;
    //assets
    protected FrameBuffer frambuffer;

    protected DummyEnemyVisualsHandler dummyEnemyVisualsHandler;

    public PlatGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);

        drawBatch = new SpriteBatch();
        drawBatch.enableBlending();

        try {
            frambuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }catch (GdxRuntimeException e){ // device doesn't support 8888
            frambuffer = new FrameBuffer(Pixmap.Format.RGB565,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }

        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        dummyEnemyVisualsHandler = new DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

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
     * @param gameCamera
     */
    @Override
    public void update(float deltaT, OrthographicCamera gameCamera) {

        super.update(deltaT,gameCamera);

        drawBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        drawBatch.begin();
        drawBatch.setProjectionMatrix(gameCamera.combined);

        dummyEnemyVisualsHandler.drawEnemies();

        drawHero();
        drawPlatforms();
        drawBatch.end();

        drawLight(gameCamera);

    }

    private void drawSurroundingDarkness(OrthographicCamera gameCamera, Texture lightTextute, Light lightInfo){
        frambuffer.begin();

        Gdx.gl.glClearColor(1-((PlatWorld)gameLogicWorld).getDangerLevel(),(1-((PlatWorld)gameLogicWorld).getDangerLevel())*0.647059f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        drawBatch.begin();
        drawBatch.draw(lightTextute, (float)lightInfo.getXPos(), (float)lightInfo.getYPos(), (float)lightInfo.getRadious() , (float)lightInfo.getRadious()); //draw hero
        drawBatch.end();

        frambuffer.end();
    }

    protected void drawLight(OrthographicCamera gameCamera){


        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        Texture lightTexture = gameAssetHandler.getLightTexture();
        Light light = ((PlatWorld)gameLogicWorld).getLightInfo();

        drawSurroundingDarkness(gameCamera,lightTexture,light);

        drawBatch.setProjectionMatrix(drawBatch.getProjectionMatrix().idt());

        drawBatch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);

        drawBatch.begin();

        drawBatch.draw(frambuffer.getColorBufferTexture(), -1, 1, 2, -2);
        drawBatch.end();

    }

    private void drawPlatforms(){

        ArrayList<Platform> platforms = ((PlatWorld)gameLogicWorld).getPlatforms();

        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();

        for(Platform platform : platforms){
            drawBatch.draw(gameAssetHandler.getPlatformTexture(platform)
                    ,(float) platform.getXPos()
                    ,(float) platform.getYPos()
                    ,(float) platform.getXDim()
                    ,(float) platform.getYDim()
            );
        }
    }
}