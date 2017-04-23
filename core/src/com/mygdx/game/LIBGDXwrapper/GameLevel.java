package com.mygdx.game.LIBGDXwrapper;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Hero;


/**
 * Class that does the actual drawing of objects
 */
public abstract class GameLevel implements LevelI {
    protected GameWorld gameLogicWorld;
    protected SpriteBatch drawBatch;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    public final static double SCREEN_RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
    protected Texture heroTex;

    public GameLevel()
    {
        drawBatch = new SpriteBatch();
    }

    @Override
    public void update(float deltaT, OrthographicCamera gameCamera) {

        gameLogicWorld.update(deltaT); //updates all world characters

        final Hero hero = gameLogicWorld.getHero(); //to get hero position to be used for drawing
        final float heroXPos = (float) hero.getXPos();
        final float heroYPos = (float) hero.getYPos();


        gameCamera.position.set(heroXPos, (float) worldYDim / 2, 0);
        gameCamera.update();

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();

        drawBatch.draw(heroTex, heroXPos, heroYPos, (float) hero.getXDim(), (float) hero.getYDim()); //draw hero

        drawBatch.end();
    }



    @Override
    public void setupCamera(OrthographicCamera gameCamera) {
        cameraWidth = (float) worldYDim * 1f / SCREEN_RATIO;
        gameCamera.setToOrtho(false, (float) cameraWidth, (float) worldYDim); //camera has maximum world height
    }

}

