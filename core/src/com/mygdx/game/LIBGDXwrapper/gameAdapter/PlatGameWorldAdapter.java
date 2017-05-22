package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.LIBGDXwrapper.GameScreen;
import com.mygdx.game.LIBGDXwrapper.PathConstants;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.FarAwayBackgroundVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.HeroLifesVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.LightRechargerVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.ScreenLightsVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals.PlatformVisualHandler;
import com.mygdx.game.LIBGDXwrapper.gameGUI.HUD;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.LogicWorlds.PlatWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature;

public class PlatGameWorldAdapter extends AbstractGameWorldAdapter{

    private DummyEnemyVisualsHandler dummyEnemyVisualsHandler;

    private ScreenLightsVisualHandler lightVisualHandler;

    private PlatformVisualHandler platformVisualHandler;

    private LightRechargerVisualHandler lightRechargerVisualHandler;

    private HeroLifesVisualHandler heroLifesVisualHandler;

    private float distanceToBackgroundX = 2f;
    private float distanceToBackgroundY = 0.3f;
    private FarAwayBackgroundVisualHandler farAwayBackgroundVisualHandler;


    public PlatGameWorldAdapter(final Vector2D cameraDims, final Vector2D worldDims, GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);

        drawBatch = new SpriteBatch();
        drawBatch.enableBlending();//default

        this.cameraWidth = cameraDims.x;
        this.cameraHeight = cameraDims.y;

        dummyEnemyVisualsHandler = new DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

        lightVisualHandler = new ScreenLightsVisualHandler( drawBatch);

        platformVisualHandler = new PlatformVisualHandler(gameLogicWorld,drawBatch);

        lightRechargerVisualHandler = new LightRechargerVisualHandler(gameLogicWorld,drawBatch);

        heroLifesVisualHandler = new HeroLifesVisualHandler(gameLogicWorld,drawBatch);

        Texture backgroundTex = GameAssetHandler.getGameAssetHandler().getNightBackgroundTexture();
        farAwayBackgroundVisualHandler = new FarAwayBackgroundVisualHandler(backgroundTex,this.distanceToBackgroundX, this.distanceToBackgroundY, gameLogicWorld.getHeroInfo(), drawBatch, worldDims, cameraDims);

        addLights();
    }

    @Override
    public void setHUD(HUD hud){
        super.setHUD(hud);
        heroLifesVisualHandler.setHUD(hud);
    }


    private void addLights(){
        if(!(gameLogicWorld instanceof HeroLightFeature)){
            System.out.println("this world does not support the hero light feature");
        }else {
            lightVisualHandler.addNewLight(((HeroLightFeature) gameLogicWorld).getLightInfo());
        }

        if(!(gameLogicWorld instanceof LightRechargerFeature)){
            System.out.println("this world does not support the recharger feature");
        }else
            lightVisualHandler.addNewLight(((LightRechargerFeature)gameLogicWorld).getItemLight());
    }


    @Override
    public void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();
        final float heroYPos = (float) hero.getYPos();
        final float heroYDim = (float) hero.getYDim();
        final float heroYCenter = heroYPos + heroYDim/2;
        final float heroXCenter = heroXPos + heroXDim/2;

        float cameraYCenter;
        if (heroYCenter <= cameraHeight / 2f)
            cameraYCenter = (float) cameraHeight / 2f;
        else
            cameraYCenter = heroYCenter;

        float cameraXCenter = heroXCenter;
        if (heroXCenter <  cameraWidth / 2f)
            cameraXCenter = (float) cameraWidth / 2f;
          else
            if(heroXCenter >=  (worldXDim-(cameraWidth / 2f)))
                cameraXCenter = (float) (worldXDim-(cameraWidth / 2f));

        gameCamera.position.set(cameraXCenter, cameraYCenter, 0);
        gameCamera.update();
    }

    @Override
    public Vector2D getCameraSetup () {
        return new Vector2D(cameraWidth, cameraHeight); //camera has maximum world height
    }



    /**
     * function ticks the GameWorld, gets the objects information from it and draws them
     * @param deltaT
     */
    @Override
    public void updateScreen(float deltaT) {
        super.updateScreen(deltaT);


        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();

        heroLifesVisualHandler.drawHeroLifes();
        drawBackground();
        dummyEnemyVisualsHandler.drawEnemies();


        drawHero();

        platformVisualHandler.drawPlatforms();
        lightRechargerVisualHandler.drawLightRecharger();
        drawBatch.end();

        lightVisualHandler.drawLight(gameCamera);

    }

    @Override
    protected void drawHero(){
        if((gameLogicWorld instanceof HeroLifesFeature) && ((HeroLifesFeature)gameLogicWorld).isImmune()){
            drawBatch.setColor(1,1,1,0.5f);
            super.drawHero();
            drawBatch.setColor(Color.WHITE);
        }else
            super.drawHero();
    }


    @Override
    protected void drawBackground(){
        farAwayBackgroundVisualHandler.drawFarAwayBackground();
    }




    @Override
    public void resize(int width, int height){
        super.resize(width,height);
        if(lightVisualHandler!=null)
            lightVisualHandler.resize( width, height);
    }

    @Override
    protected double getBackgroundYStart(double backgroundYDim) {

        final HeroInfo heroInfo = gameLogicWorld.getHeroInfo();
        final double heroYCenter = heroInfo.getYCenter();
        if (heroYCenter < cameraHeight / 2.0)
            return 0.0;
        else
        {
            final double Yintermediary = heroYCenter - cameraHeight * 0.6;
            return floorDouble(Yintermediary, backgroundYDim);
        }
    }

    @Override
    public void dispose(){
        super.dispose();
        if(lightVisualHandler!=null)
            lightVisualHandler.dispose();
    }
}