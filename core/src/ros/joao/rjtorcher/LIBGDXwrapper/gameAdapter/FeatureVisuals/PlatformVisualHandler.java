package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;

public class PlatformVisualHandler {

    private ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;

    public PlatformVisualHandler(ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld, SpriteBatch drawBatch){
        this.gameLogicWorld = gameLogicWorld;
        this.drawBatch = drawBatch;
    }

    public void drawPlatforms(){

        List<ros.joao.rjtorcher.gameLogic.Characters.Platform> platforms;
        if(gameLogicWorld instanceof ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature){
            platforms = ((ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature)gameLogicWorld).getPlatforms();
        }
        else {
            System.out.println("This world does not support platforms");
            return;
        }

        final ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler gameAssetHandler = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler();

        for(ros.joao.rjtorcher.gameLogic.Characters.Platform platform : platforms){
            drawBatch.draw(gameAssetHandler.getPlatformTexture(platform)
                    ,(float) platform.getXPos()
                    ,(float) platform.getYPos()
                    ,(float) platform.getXDim()
                    ,(float) platform.getYDim()
            );
        }
    }
}
