package com.mygdx.game.Input;


import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.GameScreen;

public class GyroscopeInput {

    private GameScreen gameScreen;
    private boolean moving;

    private static final float PITCH_CUTOFF_MIN = (float) 3.0;
    private static final float PITCH_CUTOFF_MAX = (float) 25.0;
    private static final float ROLL_CUTOFF_MIN = (float) 0.0;
    private static final float ROLL_CUTOFF_MAX = (float) 10.0;


    public GyroscopeInput(GameScreen gameScreen){
        this.gameScreen = gameScreen;
        this.moving=false;
    }

    public void update(float delta){
        handlePitch();

//        handleRoll();
    }

    private void handlePitch()
    {
        float pitch = Gdx.input.getPitch();

        if(Math.abs(pitch) > PITCH_CUTOFF_MIN)
        {
            if (pitch > PITCH_CUTOFF_MAX)
                pitch = PITCH_CUTOFF_MAX;
            else if ( - pitch > PITCH_CUTOFF_MAX)
                pitch = - PITCH_CUTOFF_MAX;

            gameScreen.sendHeroXMovement(- pitch / PITCH_CUTOFF_MAX);
            moving = true;
        }
        else if (moving)
        {
            gameScreen.sendHeroXMovement(0.0);
            moving = false;
        }


    }

    private void handleRoll()
    {
        float roll = Gdx.input.getRoll();

        if (roll > ROLL_CUTOFF_MIN) {
            if (roll > ROLL_CUTOFF_MAX)
                roll = ROLL_CUTOFF_MAX;

            gameScreen.sendHeroJump( (roll - ROLL_CUTOFF_MIN) / ROLL_CUTOFF_MAX);
        }


    }
}
