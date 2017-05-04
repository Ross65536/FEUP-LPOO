package com.mygdx.game;


import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;

/**
 * Contants common for gameLogic and Graphics.
 *
 * Aspect ratio == width / height
 */
public class CommonConstants {
    public static final double heroAspectRatio =  129 / (double) 281;
    public static final double characterXDimToSpeedMult = 5;
    public final static int NUMBER_OF_GAMEMODES = 3;
    private static final double[] tierDimYRatio = {1.0}; //when enemies are created their size is based on their tier, this table them how big their DimY is relative to hero.DimY
    private static final double[] tierAspectRatio = {129 / (double) 281}; //widht/height


    public static double getDimYRatio(final Class<?> enType)
    {
        return 1.0;
    }

    public static double getAspectRatio(final Class<?> enType)
    {
        if (enType == Enemy.class)
            return 129 / (double) 281;
        else
            return 0;
    }
}
