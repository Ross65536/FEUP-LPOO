package com.mygdx.game.LIBGDXwrapper.LevelAdapters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Characters.HeroInputs;


/**
 * Class that does the actual drawing of objects.
 * Sends input to Hero.
 */
public abstract class GameLevel implements LevelI {

    protected GameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    //assets
    protected SpriteBatch drawBatch;
    protected Texture heroTex;


    public GameLevel()
    {
        drawBatch = new SpriteBatch();
    }

    protected void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();

        gameCamera.position.set(heroXPos + heroXDim/2, (float) worldYDim / 2, 0);
        gameCamera.update();
    }

    protected void drawHero()
    {
        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        final float heroXPos = (float) hero.getXPos();
        final float heroYPos = (float) hero.getYPos();
        final float heroXDim = (float) hero.getXDim();

        drawBatch.draw(heroTex, heroXPos, heroYPos, heroXDim , (float) hero.getYDim()); //draw hero
    }





    @Override
    public void setupCamera(OrthographicCamera gameCamera) {
        cameraWidth = (float) worldYDim * DeviceConstants.SCREEN_RATIO;
        gameCamera.setToOrtho(false, (float) cameraWidth, (float) worldYDim); //camera has maximum world height
    }

    public void getLevelAssets(AssetManager gameAssetManager) {
        gameAssetManager.finishLoading();

        heroTex = gameAssetManager.get(LevelAssetsConstants.heroImagePath);
    }

    public void setHeroXMovement(double mov){
        HeroInputs hero = gameLogicWorld.getHeroInput();
        hero.setXMovement(mov);
    }
}

