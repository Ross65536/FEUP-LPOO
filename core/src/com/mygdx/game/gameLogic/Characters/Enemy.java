package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.CommonConstants;
import com.mygdx.game.gameLogic.Vector2D;

public class Enemy extends Character implements EnemyInfo {
    int tier; // tier is >= 0 and should be an indicator of the strength/difficulty of the enemy, should be used by a LevelAI to spawn enemies

    /**
     *
     * @param Xpos
     * @param Ypos
     * @param Xspeed
     * @param heroYDim unit of measurement
     * @param tier
     */
    public Enemy (final double Xpos, final double Ypos, final double Xspeed, final double heroYDim, int tier)
    {
        this (new Vector2D(Xpos, Ypos), Xspeed, heroYDim, tier);
    }

    public Enemy (final Vector2D pos, final double Xspeed, final double heroYDim, int tier)
    {
        this.characterPosition = new Vector2D(pos);
        this.characterSpeed = new Vector2D(Xspeed, 0);

        final double scale = CommonConstants.tierDimRatio[tier];
        final double aspectRatio = CommonConstants.tierAspectRatio[tier];

        final double enYDim = scale * heroYDim;
        final double enXDim = enYDim * aspectRatio;

        this.characterDimensions = new Vector2D(enXDim, enYDim);

        this.tier=tier;
    }

    @Override
    public int getTier() {
        return tier;
    }
}
