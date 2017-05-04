package com.mygdx.game.Input;


import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.GameScreen;

public class GyroscopeInput {

    private float roll;

    private boolean movingRight;

    private boolean movingLeft;

    private boolean stopped;

    private GameScreen gameScreen;

    public GyroscopeInput(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public void update(float delta){
        roll = Gdx.input.getRoll();
        if(Gdx.input.getPitch()>2 && !movingRight){
            gameScreen.sendHeroXMovement(-0.5);

            movingRight = true;
            stopped = false;
            movingLeft = false;
        }else
            if(Gdx.input.getPitch()<-2 && !movingLeft){
                gameScreen.sendHeroXMovement(0.5);

                movingLeft = true;
                stopped = false;
                movingRight = false;
            }else
                if(!stopped){
                    gameScreen.sendHeroXMovement(0);

                    stopped = true;
                    movingLeft = false;
                    movingRight = false;
                }

    }
}
