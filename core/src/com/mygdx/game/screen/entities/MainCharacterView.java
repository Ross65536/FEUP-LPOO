package com.mygdx.game.screen.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.MyGame;
import com.mygdx.game.screen.EntityVisual;

public class MainCharacterView extends EntityVisual{

    public MainCharacterView(MyGame myGame){
        super(myGame);
    }

    protected void loadSprite() {
        Texture mainCharacter = this.game.getAssetManager().get("person.png");
        this.sprite = new Sprite(mainCharacter);
    }


    @Override
    public void draw(Batch bash){
        this.sprite.draw(bash);
    }
}
