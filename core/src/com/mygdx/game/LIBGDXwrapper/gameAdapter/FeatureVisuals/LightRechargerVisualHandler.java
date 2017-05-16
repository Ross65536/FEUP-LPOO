package com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.gameLogic.Characters.Recharger;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature;

public class LightRechargerVisualHandler {

    private GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;

    public LightRechargerVisualHandler(GameWorld gameLogicWorld,SpriteBatch drawBatch){
        this.gameLogicWorld = gameLogicWorld;
        this.drawBatch = drawBatch;
    }


    public void drawLightRecharger(){
        if(!(gameLogicWorld instanceof LightRechargerFeature)){
            System.out.println("This world does not support the recharge light feature.");
            return;
        }
        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();
        Recharger recharger = ((LightRechargerFeature)gameLogicWorld).getItemInfo();
        this.drawBatch.draw(gameAssetHandler.getLightRechargerTexture()
                ,(float)recharger.getXPos()
                ,(float)recharger.getYPos()
                ,(float)recharger.getXDim()
                ,(float)recharger.getYDim()
        );
    }
}
