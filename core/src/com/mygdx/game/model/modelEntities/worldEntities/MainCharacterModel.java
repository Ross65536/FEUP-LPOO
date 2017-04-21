package com.mygdx.game.model.modelEntities.worldEntities;

import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.controller.gameWorld.controlEntities.EntityControl;
import com.mygdx.game.model.modelEntities.EntityModel;
import com.mygdx.game.screen.visualEntities.EntityVisual;

public class MainCharacterModel extends EntityModel {

    public MainCharacterModel(EntityVisual visual, EntityControl body){
        super(visual, body);
    }
}
