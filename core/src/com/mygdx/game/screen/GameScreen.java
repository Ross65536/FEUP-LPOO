package com.mygdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.controller.HUD.HUDStage;
import com.mygdx.game.controller.gameWorld.WorldStage;
import com.mygdx.game.controller.gameWorld.controlEntities.worldEntities.MainCharacterControl;


public class GameScreen extends ScreenAdapter{
    public static int WORLD_WIDTH = 100;

    public static int WORLD_HEIGHT = 50;

    public final static float PIXEL_TO_METER = 0.04f;

    public static final float VIEWPORT_WIDTH = 50;

    public final static float SCREEN_RATIO = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());

    private Stage gameHUD;

    private WorldStage gameWorld;

    private MyGame game;

    public GameScreen(MyGame myGame){
        this.game = myGame;

        loadAssets();
        loadStages();
        inputHandler();
    }

    private void loadStages(){
        gameHUD = new HUDStage(this.game);
        gameWorld = new WorldStage(this.game);
    }

    @Override
    public void render(float delta){
        super.render(delta);
        Gdx.gl.glClearColor( 103/255f, 69/255f, 117/255f, 1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );


        update(delta);
        draw();
    }

    private void inputHandler(){
        Gdx.input.setInputProcessor(new InputAdapter(){
            @Override
            public boolean keyDown (int keycode) {
                switch (keycode){
                    case Input.Keys.UP://up
                        gameWorld.getMainCharacter().jump();
                        break;
                    case Input.Keys.LEFT://left
                        gameWorld.getMainCharacter().walkLeft(false);
                        break;
                    case Input.Keys.RIGHT://right
                        gameWorld.getMainCharacter().walkRight(false);
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp (int keycode) {
                switch (keycode){
                    case Input.Keys.LEFT://left
                        gameWorld.getMainCharacter().walkLeft(true);
                        break;
                    case Input.Keys.RIGHT://right
                        gameWorld.getMainCharacter().walkRight(true);
                        break;
                }
                return true;
            }
        });
    }

    private void draw(){
        gameWorld.draw();
        gameHUD.draw();
    }

    private void update(float delta){
        gameWorld.act(delta);
        gameHUD.act(delta);
    }

    private void loadAssets() {
        this.game.getAssetManager().load(  "person.png" , Texture.class);
        this.game.getAssetManager().finishLoading();
    }

    /*@Override
    public void resize(int width, int height) {
        gameHUD.getViewport().update(width, height, true);
        gameWorld.getViewport().update(width, height, true);
    }*/
}
