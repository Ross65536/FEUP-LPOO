package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Characters.HeroInputs;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;



/**
 * Class that does the actual drawing of objects.
 * Sends input to Hero.
 */
public class GameWorldAdapter {

    protected GameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    //assets
    protected SpriteBatch drawBatch;


    public GameWorldAdapter(double worldXDim, double worldYDim, GameWorld gameLogicWorld)
    {
        drawBatch = new SpriteBatch();
        this.worldXDim = worldXDim;
        this.worldYDim = worldYDim;
        this.gameLogicWorld = gameLogicWorld;
    }

    protected void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();

        gameCamera.position.set(heroXPos + heroXDim/2, (float) worldYDim / 2, 0);
        gameCamera.update();
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
        gameLogicWorld.update(deltaT); //updates all world characters

        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        updateCameraPos(hero, gameCamera);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();
        drawEnemies();
        drawHero();
        drawBatch.end();
    }

    protected void drawHero()
    {
        HeroInfo hero = gameLogicWorld.getHeroInfo();
        final float heroXPos = (float) hero.getXPos();
        final float heroYPos = (float) hero.getYPos();
        final float heroXDim = (float) hero.getXDim();

        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        drawBatch.draw(gameAssetHandler.getHeroTexture(hero), heroXPos, heroYPos, heroXDim , (float) hero.getYDim()); //draw hero
    }

    private void drawEnemies()
    {
        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        List<EnemyInfo> enemies = gameLogicWorld.getEnemiesInfo(); //should be unmodifiable

        for (EnemyInfo enemy : enemies)
        {
            Texture enemyTex = gameAssetHandler.getCharacterTexture(enemy);

            drawBatch.draw(enemyTex, (float) enemy.getXPos(), (float) enemy.getYPos(), (float) enemy.getXDim(), (float) enemy.getYDim());
        }
    }

    /**
     * Moves the hero on the horizontal axis.
     * mov is multiplied by the maximum poosible speed of the hero to get the horizontal speed of the hero
     *
     * @param mov goes from -1.0 to 1.0, negative being movement to the left and postive to the right, 0.0 stops hero's horintal movement.
     */
    public void setHeroXMovement(double mov)
    {
        if (mov < -1.0)
            mov = -1.0;
        else if (mov > 1.0)
            mov = 1.0;

        HeroInputs hero = gameLogicWorld.getHeroInput();
        hero.setXMovement(mov);
    }

    /**
     * makes the hero jump onscreen
     * @param strength indicates the "strength" of the jump, can go from 0.5 (half strength) to 1.0 (full strength)
     */
    public void setHeroJump(double strength)
    {
        double gravityStrength = 1.0 - strength;
        final double minLimit = 0.6;
        if (gravityStrength < minLimit)
            gravityStrength = minLimit;

        else if (gravityStrength > 1.0)
            gravityStrength = 1.0;

        HeroInputs hero = gameLogicWorld.getHeroInput();
        hero.jump(gravityStrength);
    }

}

