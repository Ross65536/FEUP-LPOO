package com.mygdx.game.model.worldActors;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.screen.EntityVisual;

public class MainCharacterModel extends EntityModel{
    public MainCharacterModel(EntityVisual visual, Body body, float x, float y, float rotation){
        super(visual, body, x, y, rotation);
    }
}
