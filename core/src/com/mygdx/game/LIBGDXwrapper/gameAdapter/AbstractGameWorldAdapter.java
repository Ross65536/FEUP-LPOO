package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.PathConstants;
import com.mygdx.game.LIBGDXwrapper.gameGUI.HUD;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.Vector2D;

/**
 * Class that is the base to display graphics and soudns based on a level type, each level type has a wrapper class, that correctly displays the associated assets
 */
public abstract class AbstractGameWorldAdapter implements IGameWorldAdapter{


    protected SpriteBatch drawBatch;
    protected GameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    protected double cameraHeight;
    protected OrthographicCamera gameCamera;
    protected HUD hud;
    private double currentTime;

    /**
     * Constrcutor
     * @param worldDims dimensions of the world
     * @param gameLogicWorld the world logic object to be wrapped around
     */
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

    /**
     *
     * @return the coordinates which correspond to the center of the screen
     */
    protected Vector2D getCenterScreen(){
        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        double x = hero.getXPos()+hero.getXDim()/2.0;
        double y = hero.getYPos()+hero.getYDim()/2.0;
        return new Vector2D(x,y);
    }

    /**
     * Obtains the camera
     * @param camera
     */
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

    /**
     * Resizes the game graphics
     * @param width
     * @param height
     */
    public void resize(int width, int height){
        if(drawBatch!=null)
            drawBatch.getProjectionMatrix().setToOrtho2D(0,0,width,height);
    }

    /**
     * Updates the world state
     * @param deltaT
     */
    public void updateWorld(float deltaT){
        gameLogicWorld.update(deltaT); //updates all world characters
        if(!gameLogicWorld.isGamePlayable()){
            //TODO: Save Game Score
            hud.getGame().SwicthToMenuScreen(MyGame.MenuInstr.ENDGAME);
        }
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

    /**
     * updates the graphics
     * @param deltaT
     */
    public void updateScreen(float deltaT) {
        Gdx.gl.glClearColor(103 / 255f, 69 / 255f, 117 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling?GL20.GL_COVERAGE_BUFFER_BIT_NV:0));
        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        updateCameraPos(hero, gameCamera);
        drawBatch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
    }

    /**
     * Dispose the graphics in memory
     */
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

    /**
     * Obtains the score
     * @return
     */
    public String getScore(){
        return gameLogicWorld.getScore();
    }

    /**
     * Copies the hud
     * @param hud
     */
    public void setHUD(HUD hud){
        this.hud = hud;
    }
}
