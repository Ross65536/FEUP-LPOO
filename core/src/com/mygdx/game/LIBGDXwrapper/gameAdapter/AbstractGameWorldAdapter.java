package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.gameLogic.Vector2D;

public abstract class AbstractGameWorldAdapter implements IGameWorldAdapter{


    protected SpriteBatch drawBatch;
    protected GameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;
    protected OrthographicCamera gameCamera;

    public AbstractGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        drawBatch = new SpriteBatch();
        drawBatch.getProjectionMatrix().setToOrtho2D(0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.worldXDim = worldDims.x;
        this.worldYDim = worldDims.y;
        this.gameLogicWorld = gameLogicWorld;

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
