package com.mygdx.game.Input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.mygdx.game.LIBGDXwrapper.GameScreen;

public class KeyboardInput extends InputAdapter {
        private GameScreen gameScreen;

        public KeyboardInput (GameScreen gameScreen)
        {
            this.gameScreen=gameScreen;
        }

        @Override
        public boolean keyDown (int keycode) {
            switch (keycode){
                case Input.Keys.UP://up

                    break;
                case Input.Keys.LEFT://left
                    gameScreen.sendHeroXMovement(-0.5);
                    break;
                case Input.Keys.RIGHT://right
                    gameScreen.sendHeroXMovement(0.5);
                    break;
                default:
                    return false;
            }
            return true;
        }

        @Override
        public boolean keyUp (int keycode) {
            switch (keycode){
                case Input.Keys.UP://up

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

    public boolean touchDown (int x, int y, int pointer, int button) {
        return false;
    }

    public boolean touchUp (int x, int y, int pointer, int button) {
        return false;
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
