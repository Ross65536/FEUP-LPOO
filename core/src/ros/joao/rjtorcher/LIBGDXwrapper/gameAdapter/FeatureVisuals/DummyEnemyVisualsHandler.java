package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import ros.joao.rjtorcher.LIBGDXwrapper.PathConstants;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import ros.joao.rjtorcher.gameLogic.Characters.EnemyInfo;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.DummyEnemyFeature;

import java.util.List;

public class DummyEnemyVisualsHandler {

    private ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;

    public DummyEnemyVisualsHandler(ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld, SpriteBatch drawBatch){
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
                drawBatch.setColor(PathConstants.BOSS_COLOR); //boss color

            drawBatch.draw(enemyTex, (float) enemy.getXPos(), (float) enemy.getYPos(), (float) enemy.getXDim(), (float) enemy.getYDim());

            if (isBossEnemy)
                drawBatch.setColor(PathConstants.DEFAULT_COLOR); //reset color
        }


    }
}
