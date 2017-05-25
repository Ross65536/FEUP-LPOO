package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRechargerFeature;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature;

/**
 * Class that draws the mode from the gameLogic class: platWorld
 */
public class PlatGameWorldAdapter extends AbstractGameWorldAdapter{

    /**
     * Component used to print the enemies.
     */
    private DummyEnemyVisualsHandler dummyEnemyVisualsHandler;

    /**
     * Component used to draw all lights in the world.
     */
    private ScreenLightsVisualHandler lightVisualHandler;

    /**
     * Component used to display the platforms in the game.
     */
    private PlatformVisualHandler platformVisualHandler;

    /**
     * Component used to draw the recharger item in the game.
     */
    private LightRechargerVisualHandler lightRechargerVisualHandler;

    /**
     * Component used to draw the lifes left the player has.
     */
    private HeroLifesVisualHandler heroLifesVisualHandler;


    private float distanceToBackgroundX = 2f;
    private float distanceToBackgroundY = 0.3f;
    /**
     * Component used to draw the background and emulate distance from the player to the background.
     */
    private FarAwayBackgroundVisualHandler farAwayBackgroundVisualHandler;

    /**
     * Constructor
     * @param cameraDims Dimensions of the camera
     * @param worldDims Dimensions of the world
     * @param gameLogicWorld The gameWorld holding the logic for this mode(plarforms mode).
     */
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

    /**
     * Function that add all the lights, to display in the world, to the light handler.
     */
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

    /**
     * Function that updates the cameras position acording to the hero's position, in this mode the camera is centered on the hero.
     * @param hero The player.
     * @param gameCamera The Camera
     */
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

    /**
     * Get the camera dimensions.
     * @return camera dimensions.
     */
    @Override
    public Vector2D getCameraSetup () {
        return new Vector2D(cameraWidth, cameraHeight); //camera has maximum world height
    }



    /**
     * function ticks the GameWorld, gets the objects information from it and draws them
     * @param deltaT
     */
    @Override
    public void updateGraphics(float deltaT) {
        super.updateGraphics(deltaT);


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

    /**
     * Function used to draw the hero.
     * In this mode, when the hero gets hit by an enemy he loses a life and becumes immune to all damage for a few seconds.
     * To show when the hero is immune the hero's sprite is drawn with a a lesser alpha.
     */
    @Override
    protected void drawHero(){
        if((gameLogicWorld instanceof HeroLifesFeature) && ((HeroLifesFeature)gameLogicWorld).isImmune()){
            drawBatch.setColor(1,1,1,0.5f);
            super.drawHero();
            drawBatch.setColor(Color.WHITE);
        }else
            super.drawHero();
    }


    /**
     * Draws the background using the 'far away background' component.
     */
    @Override
    protected void drawBackground(){
        farAwayBackgroundVisualHandler.drawFarAwayBackground();
    }


    /**
     * Called when the screen is resized.
     * @param width width of the scree
     * @param height heigh of the screen
     */
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

    /**
     * Disposes the frame buffer in the lights handler component
     */
    @Override
    public void dispose(){
        super.dispose();
        if(lightVisualHandler!=null)
            lightVisualHandler.dispose();
    }
}