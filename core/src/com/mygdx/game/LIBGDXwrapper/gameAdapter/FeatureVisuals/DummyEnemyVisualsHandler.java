package com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemyFeature;

import java.util.List;

public class DummyEnemyVisualsHandler {

    private GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;

    public DummyEnemyVisualsHandler(GameWorld gameLogicWorld, SpriteBatch drawBatch){
        this.gameLogicWorld = gameLogicWorld;
        this.drawBatch = drawBatch;
    }

    public void drawEnemies()
    {
        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();

        List<EnemyInfo> enemies = null;

        if(gameLogicWorld instanceof DummyEnemyFeature){
            enemies =  ((DummyEnemyFeature)gameLogicWorld).getEnemiesInfo();
        }else {
            System.out.println("World does not implement the Dummy enemy feature");
            return;
        }



        for (EnemyInfo enemy : enemies)
        {
            TextureRegion enemyTex = gameAssetHandler.getEnemyTexture(enemy);
            final boolean isBossEnemy = enemy.isBossType();
            if (isBossEnemy)
                drawBatch.setColor(1,0,1,1); //boss color

            drawBatch.draw(enemyTex, (float) enemy.getXPos(), (float) enemy.getYPos(), (float) enemy.getXDim(), (float) enemy.getYDim());

            if (isBossEnemy)
                drawBatch.setColor(1,1,1,1); //reset color
        }


    }
}
