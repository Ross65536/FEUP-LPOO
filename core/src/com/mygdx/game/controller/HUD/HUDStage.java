package com.mygdx.game.controller.HUD;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.screen.GameScreen;

public class HUDStage extends Stage{

    private MyGame game;

    public HUDStage(MyGame mygame){
        this.game = mygame;
        this.setViewport(
                new FitViewport(
                        (int)GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER
                        ,(int)GameScreen.VIEWPORT_WIDTH / GameScreen.PIXEL_TO_METER *  GameScreen.SCREEN_RATIO)
        );
    }
}
