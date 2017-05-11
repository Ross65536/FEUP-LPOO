package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.List;

public abstract class AbstractGameWorldAdapter implements IGameWorldAdapter{


    protected SpriteBatch drawBatch;
    protected GameWorld gameLogicWorld;
    protected double worldXDim;
    protected double worldYDim; //also camera Height
    protected double cameraWidth;

    public AbstractGameWorldAdapter(final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        drawBatch = new SpriteBatch();
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

    protected void drawHero()
    {
        HeroInfo hero = gameLogicWorld.getHeroInfo();
        final float heroXPos = (float) hero.getXPos();
        final float heroYPos = (float) hero.getYPos();
        final float heroXDim = (float) hero.getXDim();

        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        drawBatch.draw(gameAssetHandler.getHeroTexture(hero), heroXPos, heroYPos, heroXDim , (float) hero.getYDim()); //draw hero
    }



    public void update(float deltaT, OrthographicCamera gameCamera) {
        gameLogicWorld.update(deltaT); //updates all world characters

        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        updateCameraPos(hero, gameCamera);
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
