package com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.PlatWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature;

import java.util.List;

import javax.swing.plaf.synth.SynthEditorPaneUI;

public class PlatformVisualHandler {

    private GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;

    public PlatformVisualHandler(GameWorld gameLogicWorld, SpriteBatch drawBatch){
        this.gameLogicWorld = gameLogicWorld;
        this.drawBatch = drawBatch;
    }

    public void drawPlatforms(){

        List<Platform> platforms;
        if(gameLogicWorld instanceof PlatformFeature){
            platforms = ((PlatformFeature)gameLogicWorld).getPlatforms();
        }
        else {
            System.out.println("This world does not support platforms");
            return;
        }

        final GameAssetHandler gameAssetHandler = GameAssetHandler.getGameAssetHandler();

        for(Platform platform : platforms){
            drawBatch.draw(gameAssetHandler.getPlatformTexture(platform)
                    ,(float) platform.getXPos()
                    ,(float) platform.getYPos()
                    ,(float) platform.getXDim()
                    ,(float) platform.getYDim()
            );
        }
    }
}
