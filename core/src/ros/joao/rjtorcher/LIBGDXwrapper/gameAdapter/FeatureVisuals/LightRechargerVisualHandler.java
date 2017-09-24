package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LightRechargerVisualHandler {

    private ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;

    public LightRechargerVisualHandler(ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld, SpriteBatch drawBatch){
        this.gameLogicWorld = gameLogicWorld;
        this.drawBatch = drawBatch;
    }


    public void drawLightRecharger(){
        if(!(gameLogicWorld instanceof ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature)){
            System.out.println("This world does not support the recharge light feature.");
            return;
        }
        final ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler gameAssetHandler = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.GameAssetHandler.getGameAssetHandler();
        ros.joao.rjtorcher.gameLogic.Characters.Recharger recharger = ((ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature)gameLogicWorld).getItemInfo();
        this.drawBatch.draw(gameAssetHandler.getLightRechargerTexture()
                ,(float)recharger.getXPos()
                ,(float)recharger.getYPos()
                ,(float)recharger.getXDim()
                ,(float)recharger.getYDim()
        );
    }
}
