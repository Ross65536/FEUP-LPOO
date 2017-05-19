package com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild;
import com.mygdx.game.LIBGDXwrapper.gameGUI.HUD;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.PlatformFeature;

import java.util.List;

public class HeroLifesVisualHandler {

    private GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;
    private HUD hud;
    private int numberOfLifes = PlatLevelBuild.numberOfLifes;

    public HeroLifesVisualHandler(GameWorld gameLogicWorld, SpriteBatch drawBatch){
        this.gameLogicWorld = gameLogicWorld;
        this.drawBatch = drawBatch;

    }

    public void setHUD(HUD hud){
        this.hud = hud;
        this.hud.addLifeHearts(((HeroLifesFeature)gameLogicWorld).getNumberOfLifes());
    }

    public void drawHeroLifes(){

        if(!(gameLogicWorld instanceof HeroLifesFeature)){
            System.out.println("This world does not support hero's life feature");
            return;
        }

        int numOflifes = ((HeroLifesFeature)gameLogicWorld).getNumberOfLifes();

        if((this.numberOfLifes - numOflifes)!=0){
            for(int i = 0; i < (this.numberOfLifes - numOflifes);i++){
                hud.removeHeart();
            }
            this.numberOfLifes = numOflifes;
        }

    }
}
