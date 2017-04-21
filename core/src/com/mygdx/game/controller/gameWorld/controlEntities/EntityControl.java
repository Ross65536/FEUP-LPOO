package com.mygdx.game.controller.gameWorld.controlEntities;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.mygdx.game.model.modelEntities.EntityModel;

public abstract class EntityControl{
    protected Body body;

    public float getX() {
        return body.getPosition().x;
    }

    public float getY() {
        return body.getPosition().y;
    }

    protected EntityModel model;

    public void setModel(EntityModel model){this.model = model;};

    public final Body getBody(){
        return body;
    }

    public abstract Body createBody(World world, float x, float y);

    public abstract void calculate(float delta);
}
