package com.mygdx.game;


import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Platform;

import java.util.ArrayList;
import java.util.List;

/**
 * Contants common for gameLogic and Graphics.
 *
 * Aspect ratio == width / height
 */
public class CommonConsts {

    public final static int NUMBER_OF_GAMEMODES = 2;
    public static boolean INPUT_DEBUG = false;

    //global arrays that contain information about each enemy, each index is essentially the type of enemy (ground, flying, etc)
    public static final int ENEMY_GROUND_ARRAY_INDEX;
    public static final int ENEMY_FLYING_ARRAY_INDEX;
    public static final int ENEMY_ARRAY_SIZE;
    static
    {
        int i=0;
        ENEMY_GROUND_ARRAY_INDEX = i; i++;
        ENEMY_FLYING_ARRAY_INDEX = i; i++;
        ENEMY_ARRAY_SIZE = i;
    }

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
            new CharacterConstants(31.0 / 24, 4.0, 0, 1.0 ,0);
    //EnemyGround class
    private static final CharacterConstants constantsEnemyGround =
            new CharacterConstants(1.0f, 1.0, 0.5, 0.5, 0.2);
    private static final CharacterConstants constantsEnemyGroundBoss =
            new CharacterConstants(1.0f, 3.0, 0.5, 0.6, 0.1);
    //Flying Enemy class
    private static final CharacterConstants constantsEnemyFlying =
            new CharacterConstants(200 / (double) 100, 1.0, 1.0, 0.7, -0.2);
    private static final CharacterConstants constantsEnemyFlyingBoss =
            new CharacterConstants(200 / (double) 100, 0.6, 0.4, 1.2, -0.1);


    //platform
    private static final CharacterConstants constantsPlatform =
            new CharacterConstants(400 / (double) 100, 0, 0, 0, 0);

    public static final List<CharacterConstants> enemyIndexToConstants;
    static
    {
        enemyIndexToConstants = new ArrayList<>(ENEMY_ARRAY_SIZE * 2);
        //common
        enemyIndexToConstants.add(ENEMY_GROUND_ARRAY_INDEX, constantsEnemyGround);
        enemyIndexToConstants.add(ENEMY_FLYING_ARRAY_INDEX, constantsEnemyFlying);

        //boss - boss should ALWAYS have index as base class index + num base enemy classes
        enemyIndexToConstants.add(ENEMY_GROUND_ARRAY_INDEX + ENEMY_ARRAY_SIZE, constantsEnemyGroundBoss);
        enemyIndexToConstants.add(ENEMY_FLYING_ARRAY_INDEX + ENEMY_ARRAY_SIZE, constantsEnemyFlyingBoss);
    }


    public static final CharacterConstants getEnemyConstants(final int enArrayIndex)
    {
        if (enArrayIndex > enemyIndexToConstants.size())
            return null;
        else
            return enemyIndexToConstants.get(enArrayIndex);

    }

    public static final CharacterConstants getCharacterConstants(final Class<?> enType)
    {
            if (enType == Hero.class)
                return constantsHero;
            else if (enType == Platform.class)
                return constantsPlatform;
            else if (enType == EnemyGround.class)
                return constantsEnemyGround;
            else if (enType == EnemyFlying.class)
                return constantsEnemyFlying;
            else
                return null;
    }
}
