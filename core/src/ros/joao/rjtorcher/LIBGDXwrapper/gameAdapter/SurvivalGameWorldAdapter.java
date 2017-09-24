package ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.graphics.OrthographicCamera;


/**
 * Class that does the actual drawing of objects.
 * Sends input to Hero.
 */
public class SurvivalGameWorldAdapter extends AbstractGameWorldAdapter{

    private ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler dummyEnemyVisuals;

    private ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals.ScreenLightsVisualHandler /**/lightVisualHandler;

    /**
     * Constrcutor
     * @param worldDims dimensison of hte world
     * @param gameLogicWorld the world object
     */
    public SurvivalGameWorldAdapter(final ros.joao.rjtorcher.Vector2D worldDims, ros.joao.rjtorcher.gameLogic.LogicWorlds.GameWorld gameLogicWorld)
    {
        super(worldDims,gameLogicWorld);

        dummyEnemyVisuals = new ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals.DummyEnemyVisualsHandler(gameLogicWorld,drawBatch);

        lightVisualHandler = new ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.FeatureVisuals.ScreenLightsVisualHandler(drawBatch);

        this.cameraHeight = worldYDim;

        addLights();
    }

    /**
     * Function that add all the lights, to display in the world, to the light handler.
     */
    private void addLights(){
        if(!(gameLogicWorld instanceof ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature)){
            System.out.println("this world does not support the hero light feature");
        }else {
            lightVisualHandler.addNewLight(((ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLightFeature) gameLogicWorld).getLightInfo());
        }
    }

    /**
     * Updates the camera, which should display the action correctly
     * @param hero hero informaiton
     * @param gameCamera camera
     */
    public void updateCameraPos(ros.joao.rjtorcher.gameLogic.Characters.CharacterInfo hero, OrthographicCamera gameCamera)
    {
        final float heroXPos = (float) hero.getXPos();
        final float heroXDim = (float) hero.getXDim();

        gameCamera.position.set(heroXPos + heroXDim/2, (float) worldYDim / 2, 0);
        gameCamera.update();

    }

    /**
     *
     * @return dimensiosn of the camera, which enable to setup the camera correctly
     */
    public ros.joao.rjtorcher.Vector2D getCameraSetup () {
        cameraWidth = worldYDim * ros.joao.rjtorcher.LIBGDXwrapper.DeviceConstants.SCREEN_RATIO;
        return new ros.joao.rjtorcher.Vector2D(cameraWidth, worldYDim); //camera has maximum world height
    }

    /**
     * function ticks the GameWorld, gets the objects information from it and draws them
     * @param deltaT
     */
    @Override
    public void updateGraphics(float deltaT) {
        super.updateGraphics(deltaT );

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();
        drawBackground();
        dummyEnemyVisuals.drawEnemies();
        drawHero();
        drawBatch.end();

        lightVisualHandler.drawLight(gameCamera);

    }

    /**
     * Resizes the game
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height){
        super.resize(width,height);
        if(lightVisualHandler != null)
            lightVisualHandler.resize( width, height);
    }

    @Override
    protected double getBackgroundYStart(double backgroundYDim) {
        return 0.0;
    }
}

