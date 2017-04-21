package com.mygdx.game.model.worldActors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.screen.EntityVisual;


public class EntityModel extends Actor{
    //actor já tem esta cena toda

    private Body body;

    private EntityVisual visual;

    private float x;

    private float y;

    private float rotation;

    public EntityModel(EntityVisual visual, Body body, float x, float y, float rotation) {
        this.visual = visual;
        this.body = body;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public void draw(Batch batch, float parentAlpha){
        visual.draw(batch);
    }

    @Override
    public void act(float delta){
        update();
        super.act(delta);
    }

    private void update(){
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.rotation = body.getAngle();
    }
}
