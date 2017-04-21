package com.mygdx.game.model.modelEntities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.controller.gameWorld.controlEntities.EntityControl;
import com.mygdx.game.screen.visualEntities.EntityVisual;


public class EntityModel extends Actor{
    private EntityControl control;

    private EntityVisual visual;

    public EntityModel(EntityVisual visual, EntityControl control) {
        this.visual = visual;
        this.control = control;
    }


    @Override
    public void draw(Batch batch, float parentAlpha){
        this.visual.draw(batch);
    }

    @Override
    public void act(float delta){
        this.control.calculate(delta);

        super.act(delta);//ainda não faz nada
    }

    @Override
    public void setPosition(float x, float y){
        super.setPosition(x, y);
        super.positionChanged();
    }
}
