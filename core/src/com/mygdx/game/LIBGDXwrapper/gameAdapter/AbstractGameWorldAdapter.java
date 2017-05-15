package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.Vector2D;


public abstract class AbstractGameWorldAdapter implements IGameWorldAdapter{


    protected SpriteBatch drawBatch;
    protected GameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    protected double cameraHeight;
    protected OrthographicCamera gameCamera;
    private double currentTime;


    public AbstractGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {

        drawBatch = new SpriteBatch();
        drawBatch.getProjectionMatrix().setToOrtho2D(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.worldXDim = worldDims.x;
        this.worldYDim = worldDims.y;
        this.gameLogicWorld = gameLogicWorld;

    }

    public double getCreationTime(){
        return currentTime;
    }

    protected Vector2D getCenterScreen(){
        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        double x = hero.getXPos()+hero.getXDim()/2.0;
        double y = hero.getYPos()+hero.getYDim()/2.0;
        return new Vector2D(x,y);
    }

    @Override
    public void setCamera(OrthographicCamera camera){
        gameCamera = camera;
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

    public void resize(int width, int height){
        if(drawBatch!=null)
            drawBatch.getProjectionMatrix().setToOrtho2D(0,0,width,height);
    }

    public void updateWorld(float deltaT){
        gameLogicWorld.update(deltaT); //updates all world characters
    }

    protected static double floorDouble(final double numerator, final double divisor)
    {
        double numMult = numerator / divisor;
        numMult = Math.floor(numMult);
        return numMult * divisor;
    }

    protected abstract double getBackgroundYStart(double backgroundYDim);

    private static final float DRAW_Y_LEEWAY = 0.5f;
    private static final float DRAW_X_LEEWAY = 0.2f;

    protected void drawBackground()
    {
        final Texture backgroundTex = GameAssetHandler.getGameAssetHandler().getBackgroundTexture();
        final HeroInfo heroInfo = gameLogicWorld.getHeroInfo();

        final float backgroundYDim = (float) (cameraHeight * PathConstants.BACKGROUND_PORTION_OF_CAMERA_Y);
        final float backgroundXDim = (float) (backgroundYDim * PathConstants.BACKGROUND_ASPECT_RATIO);

        final float drawXMin = (float) floorDouble(heroInfo.getXPos() - cameraHeight, backgroundXDim); //max 2:1 screen
        final float drawYMin = (float) getBackgroundYStart(backgroundYDim);

        final float maxX = (float) (drawXMin + 2.0 * (cameraHeight + backgroundXDim)); //should be 2:1 max screen
        final float maxY = (float) (drawYMin + 1.0 * (cameraHeight + backgroundYDim));

        for (float drawY = drawYMin; drawY <= maxY; drawY += backgroundYDim)
        {
            for (float drawX = drawXMin; drawX <= maxX; drawX += backgroundXDim)
            {
                drawBatch.draw(backgroundTex, drawX,drawY, backgroundXDim, backgroundYDim);
            }
        }


    }

    public void updateScreen(float deltaT) {
        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        updateCameraPos(hero, gameCamera);
        drawBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void dispose(){
        if(drawBatch!=null)
            drawBatch.dispose();
    }

    /**
     *
     * @return objects that can send input data to logic world.
     */
    public IGameWorldHeroInputs getLogicWorldInputs() {
        return gameLogicWorld.getWorldInputs();
    }

    public abstract void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera);
    public abstract Vector2D getCameraSetup ();
}
