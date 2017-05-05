package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;



/**
 * Class that does the actual drawing of objects.
 * Sends input to Hero.
 */
public class GameWorldAdapter {

    protected IGameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    //assets
    protected SpriteBatch drawBatch;
    //constants



    public GameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        drawBatch = new SpriteBatch();
        this.worldXDim = worldDims.x;
        this.worldYDim = worldDims.y;
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
     *
     * @return objects that can send input data to logic world.
     */
    public IGameWorldHeroInputs getLogicWorldInputs() {
        return gameLogicWorld.getWorldInputs();
    }
}

