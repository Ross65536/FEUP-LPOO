package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameLogic.Hero;
import com.mygdx.game.gameLogic.GameWorld;


public class GameScreen extends ScreenAdapter {
    private double worldXDim;
    private double worldYDim; //also camera Height
    private double cameraWidth;
    private final static double SCREEN_RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
    private GameWorld gameLogicWorld;

    private OrthographicCamera gameCamera;
    private SpriteBatch drawBatch;
    private AssetManager gameAssetManager;
    private MyGame game;


    public GameScreen(MyGame myGame) {
        this.game = myGame;
        gameAssetManager = new AssetManager(new InternalFileHandleResolver());
        drawBatch = new SpriteBatch();

        gameCamera = new OrthographicCamera();

        inputHandler();
    }

    /**
     * Start of Periodic function.
     *
     * @param deltaT
     */
    @Override
    public void render(float deltaT) {
        super.render(deltaT);
        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        gameLogicWorld.update(deltaT); //updates all world characters

        final Hero hero = gameLogicWorld.getHero(); //to get hero position to be used for drawing
        final float heroXPos = (float) hero.getXPos();
        final float heroYPos = (float) hero.getYPos();


        gameCamera.position.set(heroXPos,(float) worldYDim/2, 0);
        gameCamera.update();

        Texture heroTex = gameAssetManager.get("person.png");

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();

        drawBatch.draw(heroTex, heroXPos, heroYPos, (float) hero.getXDim(), (float) hero.getYDim()); //draw hero

        drawBatch.end();
    }

    private void inputHandler() {

    }

    public void createTestLevel() {
        gameAssetManager.load(  "person.png" , Texture.class);

        worldXDim = 100;
        worldYDim = SCREEN_RATIO * worldXDim / 10;
        Hero hero = new Hero(worldXDim / 2f, 0, worldYDim/8, worldYDim/4); //worldYDim/8, worldYDim/4 are wrong
        gameLogicWorld = new GameWorld(worldXDim, worldYDim, hero);

        cameraWidth = (float) worldYDim * 1f / SCREEN_RATIO;
        gameCamera.setToOrtho(false, (float) cameraWidth, (float) worldYDim); //camera has maximum world height

        gameAssetManager.finishLoading();
    }
}
