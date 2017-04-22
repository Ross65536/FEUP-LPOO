package com.mygdx.game.controller.gameWorld;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.MyGame;
import com.mygdx.game.controller.gameWorld.controlEntities.worldEntities.MainCharacterControl;
import com.mygdx.game.model.modelEntities.worldEntities.MainCharacterModel;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.visualEntities.worldEntities.MainCharacterView;

import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class WorldStage extends Stage{

    private final World world;

    private MainCharacterControl mainCharacterControl;

    private MyGame game;


    //LEVEL BUILDER GOES HERE

    public WorldStage(MyGame mygame){
        this.game = mygame;
        this.setViewport(
                new FitViewport(
                        (int)(GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER)
                        ,(int)(GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER *  GameScreen.SCREEN_RATIO)
        ));

        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (int)(GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER), (int)(GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER *  GameScreen.SCREEN_RATIO));

        this.world = new World(new Vector2(0,-95f),true);
        addMainCharacter();
    }

    public MainCharacterControl getMainCharacter(){
        return this.mainCharacterControl;
    }

    private void addMainCharacter(){
        float posX = (GameScreen.VIEWPORT_WIDTH)/2.0f;
        float posY = (GameScreen.VIEWPORT_WIDTH*GameScreen.SCREEN_RATIO)/2.0f;

        this.mainCharacterControl = new MainCharacterControl();

        this.mainCharacterControl.createBody(world ,posX ,posY);

        MainCharacterView mainCharacterView = new MainCharacterView(this.game);

        MainCharacterModel mainCharacterModel =
            new MainCharacterModel(
                    mainCharacterView
                    ,this.mainCharacterControl
            );

        mainCharacterControl.setModel(mainCharacterModel);

        mainCharacterView.setModel(mainCharacterModel);


        this.addActor(mainCharacterModel);
    }
    @Override
    public void draw() {
        super.draw();
    }
    @Override
    public void act(float delta){
        world.step(delta, 6, 2);
        super.act(delta);
    }


}
