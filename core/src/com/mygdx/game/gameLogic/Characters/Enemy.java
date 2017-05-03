package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.gameLogic.Vector2D;

public class Enemy extends Character implements EnemyInfo {
    int tier; // tier is >= 0 and should be an indicator of the strength/difficulty of the enemy, should be used by a LevelAI to spawn enemies

    public Enemy (final Vector2D pos, final Vector2D dims, final double Xspeed, int tier)
    {
        super(pos, dims, new Vector2D(Xspeed, 0));
        this.tier=tier;
    }

    public Enemy() {
    }

    @Override
    public int getTier() {
        return tier;
    }
}
