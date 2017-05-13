package com.mygdx.game;


import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Platform;

/**
 * Contants common for gameLogic and Graphics.
 *
 * Aspect ratio == width / height
 */
public class Constants {
    public final static int NUMBER_OF_GAMEMODES = 3;
    public static boolean INPUT_DEBUG = true;
    //Class specific information
    public static class CharacterConstants
    {
        private CharacterConstants(final double aspectRatio, final double speedMult, final double speedXPadding, final double dimYMult, final double dimYPadding) { this.aspectRatio = aspectRatio; this.speedMult = speedMult; this.dimYMult = dimYMult; this.speedXPadding =speedXPadding; this.dimYPadding =dimYPadding;}
        final public double aspectRatio; //aspect ratio of texture to be used
        final public double speedMult; //multiplied to Hero.dimY
        final public double speedXPadding; // speedMult + speedXPadding * externalFraction
        final public double dimYMult; //multiplied to Hero.dimY
        final public double dimYPadding; // dimYMult + dimYPadding * externalFraction
        //speedXPadding and dimYPadding scale linearly when used
    }

    //Hero class
    private static final CharacterConstants constantsHero =
            new CharacterConstants(129 / (double) 281, 4.0, 0, 1.0 ,0);
    //EnemyGround class
    private static final CharacterConstants constantsEnemyGround =
            new CharacterConstants(129 / (double) 281, 1.5, 1.0, 0.8, -0.2);
    //Flying Enemy class
    private static final CharacterConstants constantsEnemyFlying =
            new CharacterConstants(200 / (double) 100, 3.0, 1.0, 0.5, -0.2);

    //platform
    private static final CharacterConstants constantsPlatform =
            new CharacterConstants(400 / (double) 50, 0, 0, 0, 0);

    public static final CharacterConstants getEnemyConstants(final Class<?> enType)
    {
        if (enType == Hero.class)
            return constantsHero;
        else if (enType == EnemyGround.class)
            return constantsEnemyGround;
        else if (enType == EnemyFlying.class)
            return constantsEnemyFlying;
        else if (enType == Platform.class)
            return constantsPlatform;
        else
            return null;
    }
}
