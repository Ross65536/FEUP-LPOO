package com.mygdx.game.controller.World;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.model.worldActors.MainCharacterModel;
import com.mygdx.game.controller.World.bodys.MainCharacterControl;
import com.mygdx.game.screen.GameScreen;
import com.mygdx.game.screen.entities.MainCharacterView;

public class WorldStage extends Stage{

    private final World world;

    private MainCharacterControl mainCharacterControl;

    private MyGame game;

    public WorldStage(MyGame mygame){
        this.game = mygame;
        this.setViewport(
                new FitViewport(
                        GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER
                        ,GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER *  GameScreen.SCREEN_RATIO)
        );
        this.world = new World(new Vector2(0,-3),true);
        addMainCharacter();
    }

    private void addMainCharacter(){
        float posX = (GameScreen.VIEWPORT_WIDTH/GameScreen.PIXEL_TO_METER)/2.0f;
        float posY = (GameScreen.VIEWPORT_WIDTH*GameScreen.SCREEN_RATIO/GameScreen.PIXEL_TO_METER)/2.0f;

        this.mainCharacterControl = new MainCharacterControl();

        this.mainCharacterControl.createBody(
                world
                ,posX
                ,posY
        );

        MainCharacterModel mainCharacterModel =
                new MainCharacterModel(
                        new MainCharacterView(this.game)
                        ,this.mainCharacterControl.getBody()
                        ,posX
                        ,posY
                        , 0.0f
                );

        this.addActor(mainCharacterModel);
    }

    @Override
    public void act(float delta){

        world.step(delta, 6, 2);

        BoundsMainCharacter();
        super.act(delta);
    }

    private void BoundsMainCharacter(){
        Body mainCharBody = mainCharacterControl.getBody();
        if(mainCharBody.getPosition().y<0)
            mainCharBody.setTransform(mainCharBody.getPosition().x
                    ,0
                    ,mainCharBody.getAngle()
            );

    }

}
