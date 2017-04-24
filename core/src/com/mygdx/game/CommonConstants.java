package com.mygdx.game;

/**
 * Contants common for gameLogic and Graphics.
 *
 * Aspect ratio are width / height
 */
public class CommonConstants {
    public static final double heroAspectRatio =  200 / (double) 283;
    public static final double characterXDimToSpeedMult = 5;
    public static final double[] tierDimRatio = {1.0}; //when enemies are created their size is based on their tier, this table them how big their DimX is relative to hero.DimX
    public static final double[] tierAspectRatio = {200 / (double) 283}; //widht/height
    public final static int NUMBER_OF_GAMEMODES = 2;
}
