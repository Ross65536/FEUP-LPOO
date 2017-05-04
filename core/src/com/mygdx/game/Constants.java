package com.mygdx.game;


import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.Hero;

/**
 * Contants common for gameLogic and Graphics.
 *
 * Aspect ratio == width / height
 */
public class Constants {
    public final static int NUMBER_OF_GAMEMODES = 3;
    //Class specific information
    public static class CharacterConstants
    {
        private CharacterConstants(final double aspectRatio, final double speedMult, final double dimYMult) { this.aspectRatio = aspectRatio; this.speedMult = speedMult; this.dimYMult = dimYMult; }
        final public double aspectRatio; //aspect ratio of texture to be used
        final public double speedMult; //multiplied to Hero.dimY
        final public double dimYMult; //multiplied to Hero.dimY
    }

    //Hero class
    private static final CharacterConstants constantsHero = new CharacterConstants(129 / (double) 281, 3.0, 1.0);
    //EnemyGround class
    private static final CharacterConstants constantsEnemyGround = new CharacterConstants(129 / (double) 281, 1.5, 0.8);

    public static final CharacterConstants getEnemyConstants(final Class<?> enType)
    {
        if (enType == Hero.class)
            return constantsHero;
        if (enType == EnemyGround.class)
            return constantsEnemyGround;
        else
            return null;
    }
}
