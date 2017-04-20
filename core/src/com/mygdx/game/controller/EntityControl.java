package com.mygdx.game.controller;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class EntityControl {
    protected Body body;

    public final Body getBody(){
        return body;
    }

    public Body createBody(World world, float x, float y){return null;}
}
