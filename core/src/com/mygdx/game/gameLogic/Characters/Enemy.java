package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.CommonConstants;

public class Enemy extends Character implements EnemyInfo {
    int tier; // tier is >= 0 and should be an indicator of the strength/difficulty of the enemy, should be used by a LevelAI to spawn enemies

    /**
     *
     * @param charXPos
     * @param charYPos
     * @param heroXDim used as unit of measurement
     * @param tier
     */
    public Enemy (double charXPos, double charYPos, double heroXDim, int tier)
    {
        super(charXPos, charYPos, 0, 0);

        final double scale = CommonConstants.tierDimRatio[tier];
        final double aspectRatio = CommonConstants.tierAspectRatio[tier];
        final double enXDim = scale * heroXDim;
        final double enYDim = enXDim / aspectRatio;
        this.setXYDims(enXDim, enYDim);

        this.tier=tier;
    }

    @Override
    public int getTier() {
        return tier;
    }
}
