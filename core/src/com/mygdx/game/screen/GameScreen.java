package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.MyGame;
import com.mygdx.game.controller.HUD.HUDStage;
import com.mygdx.game.controller.World.WorldStage;


public class GameScreen extends ScreenAdapter{
    public static int WORLD_WIDTH = 100;

    public static int WORLD_HEIGHT = 50;

    public final static float PIXEL_TO_METER = 0.04f;

    public static final float VIEWPORT_WIDTH = 50;

    public final static float SCREEN_RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    private Stage gameHUD;

    private Stage gameWorld;

    private MyGame game;

    public GameScreen(MyGame myGame){
        this.game = myGame;
        loadAssets();
        gameHUD = new HUDStage(this.game);
        gameWorld = new WorldStage(this.game);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        gameWorld.act(delta);
        gameHUD.act(delta);
        gameWorld.draw();
        gameHUD.draw();
    }

    private void loadAssets() {
        this.game.getAssetManager().load( "person.png" , Texture.class);
        this.game.getAssetManager().load( "badlogic.jpg" , Texture.class);
        this.game.getAssetManager().finishLoading();
    }
}
