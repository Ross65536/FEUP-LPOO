package ros.joao.rjtorcher.LIBGDXwrapper.Input;


import com.badlogic.gdx.Gdx;
import ros.joao.rjtorcher.LIBGDXwrapper.GameScreen;

/**
 * Class that recevies smartphone inputs, the orientatio of the phone, to allow the player controleld character to jump and move
 */
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

    /**
     * Method that sends player controlls to the hero, based on smartphone orientation (pitch and roll)
     */
    public void pollOrientation(){
        handlePitch();

        handleRoll();
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
