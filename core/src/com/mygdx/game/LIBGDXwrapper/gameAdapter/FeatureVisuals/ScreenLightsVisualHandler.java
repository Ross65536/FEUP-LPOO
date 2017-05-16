package com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.gameLogic.Characters.Light;

import java.util.ArrayList;

public class ScreenLightsVisualHandler {


    private FrameBuffer frameBuffer;
    private SpriteBatch drawBatch;

    private ArrayList<Light> lights;

    public ScreenLightsVisualHandler(SpriteBatch drawBatch){
        try {
            frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }catch (GdxRuntimeException e){ // device doesn't support 8888
            frameBuffer = new FrameBuffer(Pixmap.Format.RGB565,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }

        this.drawBatch = drawBatch;
        lights = new ArrayList<Light>();
    }

    public void addNewLight(Light lightInfo){
        lights.add(lightInfo);
    }

    public void drawLight(OrthographicCamera gameCamera){

        drawSurroundingDarkness(gameCamera);

        drawBatch.setProjectionMatrix(drawBatch.getProjectionMatrix().idt());

        drawBatch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);

        drawBatch.begin();

        drawBatch.draw(frameBuffer.getColorBufferTexture(), -1, 1, 2, -2);
        drawBatch.end();

    }

    private void drawSurroundingDarkness(OrthographicCamera gameCamera){

        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        Texture lightTexture = gameAssetHandler.getLightTexture();

        frameBuffer.begin();

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        drawBatch.begin();

        for(Light light: lights){
            drawBatch.draw(lightTexture, (float)light.getXPos(), (float)light.getYPos(), (float)light.getRadious() , (float)light.getRadious()); //draw hero
        }

        drawBatch.end();

        frameBuffer.end();
    }

    public void resize(int width, int height){
        if (frameBuffer != null && (frameBuffer.getWidth() != width || frameBuffer.getHeight() != height)){
            frameBuffer.dispose();
            frameBuffer = null;
            try {
                frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
            } catch (GdxRuntimeException e){ // device doesn't support 8888
                frameBuffer = new FrameBuffer(Pixmap.Format.RGB565, width, height, false);
            }
        }
    }

    public void dispose(){
        if(frameBuffer!=null)
            frameBuffer.dispose();
    }
}
