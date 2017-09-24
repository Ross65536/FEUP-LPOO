package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ros.joao.rjtorcher.LIBGDXwrapper.gameGUI.HUD;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;

public class HeroLifesVisualHandler {

    private ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld;
    private SpriteBatch drawBatch;
    private HUD hud;
    private int numberOfLifes = ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild.numberOfLifes;

    public HeroLifesVisualHandler(ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld, SpriteBatch drawBatch){
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
