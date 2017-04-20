package com.mygdx.game.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.MyGame;

abstract public class EntityVisual {

    protected Sprite sprite;

    protected MyGame game;

    public EntityVisual(MyGame myGame){
        loadSprite();
    }

    public final Sprite getSprite(){return sprite;};

    protected abstract void loadSprite();

    public void draw(Batch bash){
        this.sprite.draw(bash);
    }
}
