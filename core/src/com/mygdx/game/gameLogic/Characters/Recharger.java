package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

/**
 * Class that represents the recharger item.
 */
public class Recharger extends Entity{


    public static double fractionOfScreenHeightForPlatform = 1.0/10.0;

    public Recharger(final Vector2D position, final Vector2D dimensions){
        super(position, dimensions);
    }

}
