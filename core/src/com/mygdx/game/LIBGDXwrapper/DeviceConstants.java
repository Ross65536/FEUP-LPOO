package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Gdx;

public class DeviceConstants {
    public final static double SCREEN_RATIO = ((float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight());
    public final static double INVERTED_SCREEN_RATIO = 1 / SCREEN_RATIO;
    public final static float MENU_VIEWPORT = 1250;
    public final static int NUMBER_OF_GAMEMODES = 1;
}
