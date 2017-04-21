package com.mygdx.game.screen.visualEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.MyGame;
import com.mygdx.game.model.modelEntities.EntityModel;

public abstract class EntityVisual {

    protected Sprite sprite;

    protected MyGame game;

    protected EntityModel model;

    public void setModel(EntityModel model){this.model = model;}

    public EntityVisual(MyGame myGame){
        this.game = myGame;
        loadSprite();
    }

    public final Sprite getSprite(){return sprite;};

    protected abstract void loadSprite();

    public void draw(Batch bash){
        this.sprite.setPosition(model.getX(), model.getY());
        this.sprite.draw(bash);
    }
}
