package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.gameLogic.LogicWorlds.PlatWorld;
import com.mygdx.game.gameLogic.Vector2D;

import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import static com.badlogic.gdx.graphics.GL20.GL_ZERO;
import static com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888;

/**
 * Created by João on 06/05/2017.
 */

public class GameWorldAdapter2 implements  IGameWorldAdapter{

    protected PlatWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    protected double cameraHeight;
    //assets
    protected SpriteBatch drawBatch;
    protected FrameBuffer frambuffer;
    protected Texture surroundingTexture; //surrounds the light

    //void resize() //<-manditory


    public GameWorldAdapter2(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        drawBatch = new SpriteBatch();
        drawBatch.enableBlending();

        try {
            frambuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }catch (GdxRuntimeException e){ // device doesn't support 8888
            frambuffer = new FrameBuffer(Pixmap.Format.RGB565,  Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        }

        this.worldXDim = worldDims.x;
        this.worldYDim = worldDims.y;
        this.gameLogicWorld = (PlatWorld)gameLogicWorld;
        this.cameraWidth = worldDims.x/10;
        this.cameraHeight = cameraWidth * DeviceConstants.INVERTED_SCREEN_RATIO;

        Pixmap pixMap = new Pixmap(100,300, Pixmap.Format.RGB565);
        pixMap.setColor(Color.BLACK);
        pixMap.fill();
        surroundingTexture = new Texture(pixMap);


    }

    public void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();
        final float heroYPos = (float) hero.getYPos();
        final float heroYDim = (float) hero.getYDim();

        gameCamera.position.set(heroXPos + heroXDim/2, heroYPos + heroYDim/2, 0);
        gameCamera.update();
    }

    public Vector2D getCameraSetup () {
        return new Vector2D(cameraWidth, cameraHeight); //camera has maximum world height
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




        drawBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        drawBatch.begin();
        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawEnemies();
        drawHero();
        drawPlatforms();
        drawBatch.end();

        drawLight(gameCamera);

    }

    protected void drawLight(OrthographicCamera gameCamera){


        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        Texture lightTexture = gameAssetHandler.getLightTexture();
        Light light = gameLogicWorld.getLightInfo();

        frambuffer.begin();

        Gdx.gl.glClearColor(0f,0f,0f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE);
        drawBatch.begin();
        drawBatch.draw(lightTexture, (float)light.getXPos(), (float)light.getYPos(), (float)light.getRadious() , (float)light.getRadious()); //draw hero
        drawBatch.end();

        frambuffer.end();

        drawBatch.setProjectionMatrix(drawBatch.getProjectionMatrix().idt());

        drawBatch.setBlendFunction(GL20.GL_ZERO, GL20.GL_SRC_COLOR);

        drawBatch.begin();
        drawBatch.draw(frambuffer.getColorBufferTexture(), -1, 1, 2, -2);
        drawBatch.end();

    }

    protected Vector2D getCenterScreen(){
        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        double x = hero.getXPos()+hero.getXDim()/2.0;
        double y = hero.getYPos()+hero.getYDim()/2.0;
        return new Vector2D(x,y);
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