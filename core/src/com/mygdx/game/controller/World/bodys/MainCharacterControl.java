package com.mygdx.game.controller.World.bodys;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.EntityControl;
import com.mygdx.game.screen.GameScreen;



public class MainCharacterControl extends EntityControl{

    //caracteristicas todas desta classe no mundo
    public Body createBody(World world, float x, float y) {
        // Create the ball body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        this.body = world.createBody(bodyDef);

        this.body.setTransform(GameScreen.VIEWPORT_WIDTH / 2
                , (GameScreen.VIEWPORT_WIDTH * GameScreen.SCREEN_RATIO) / 2
                , 0
        ); // Middle of the viewport, no rotation

        // Create circle shape
        PolygonShape bodyshape = new PolygonShape();
        bodyshape.set(new float[]{0,0,0,20,7,0,7,20});

        // Create ball fixture
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyshape;
        fixtureDef.density = .5f;      // how heavy is the ball
        fixtureDef.friction =  .5f;    // how slippery is the ball
        fixtureDef.restitution =  .5f; // how bouncy is the ball

        // Attach fixture to body
        this.body.createFixture(fixtureDef);

        // Dispose of circle shape
        bodyshape.dispose();

        return this.body;
    }


}
