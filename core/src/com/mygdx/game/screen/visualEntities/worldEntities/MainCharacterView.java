package com.mygdx.game.screen.visualEntities.worldEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.MyGame;
import com.mygdx.game.screen.visualEntities.EntityVisual;

public class MainCharacterView extends EntityVisual {

    public MainCharacterView(MyGame myGame){
        super(myGame);
    }

    protected void loadSprite() {
        Texture mainCharacter = this.game.getAssetManager().get("person.png");
        this.sprite = new Sprite(mainCharacter);
    }


    @Override
    public void draw(Batch bash){

        this.sprite.setPosition(model.getX()-this.sprite.getWidth()/2, model.getY());
        this.sprite.draw(bash);
    }
}
