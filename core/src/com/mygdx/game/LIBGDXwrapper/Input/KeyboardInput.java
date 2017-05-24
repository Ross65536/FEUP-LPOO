package com.mygdx.game.LIBGDXwrapper.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.LIBGDXwrapper.GameScreen;

/**
 * Class that receives keyboard and mouse input and sends it to the player controlled hero,
 * arrow keys are used to move the hero left and right and to jump
 * Mouse clicks or touch on smartphones make the hero jump
 */
public class KeyboardInput extends InputAdapter {
        private GameScreen gameScreen;

        public KeyboardInput (GameScreen gameScreen)
        {
            this.gameScreen=gameScreen;
        }

    /**
     * When key is pressed down
     * @param keycode
     * @return
     */
    @Override
        public boolean keyDown (int keycode) {
            switch (keycode){
                case Input.Keys.UP://up
                    gameScreen.sendHeroJump(1.0);
                    break;
                case Input.Keys.LEFT://left
                    gameScreen.sendHeroXMovement(-1.0);
                    break;
                case Input.Keys.RIGHT://right
                    gameScreen.sendHeroXMovement(1.0);
                    break;
                default:

                    return false;
            }
            return true;
        }

    /**
     * When key is released
     * @param keycode
     * @return
     */
    @Override
        public boolean keyUp (int keycode) {
            switch (keycode){
                case Input.Keys.UP://up
                    gameScreen.sendHeroJump(1.0);
                    break;
                case Input.Keys.LEFT://left
                case Input.Keys.RIGHT://right
                    gameScreen.sendHeroXMovement(0.0);
                    break;
                default:
                    return false;
            }
            return true;
        }

    public boolean keyTyped (char character) {
        return false;
    }

    /**
     * Mouse click, or touch on smartphone, make the hero jump
     * @param x
     * @param y
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        gameScreen.sendHeroJump(1.0);
        return true;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        gameScreen.sendHeroJump(0.0);
        return true;

    }

    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    public boolean mouseMoved (int x, int y) {
        return false;
    }

    public boolean scrolled (int amount) {
        return false;
    }
}

