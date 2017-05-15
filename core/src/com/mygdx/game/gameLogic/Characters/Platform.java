package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

public class Platform extends Character{

    public static double fractionOfScreenHeightForPlatform = 1.0/15.0;

    public Platform (final Vector2D position, final Vector2D dimensions){
        super(position, dimensions, (new Vector2D(0,0)));

    }

    private static final double PLATFORM_X_LEEWAY = 0.05;
    private static final double PLATFORM_Y_LEEWAY = 0.5;

    private static final double MAX_Y_DIM = 2;
    private static final double MAX_X_DIM = 5;

    private boolean isIntersecting(final Character ch){
        final boolean charXLeft = (characterPosition.x + characterDimensions.x) < ch.characterPosition.x;
        final boolean charXRight = characterPosition.x > (ch.characterPosition.x + ch.characterDimensions.x);
        final boolean charYDown = (characterPosition.y + characterDimensions.y) < ch.characterPosition.y;
        final boolean charYUp = characterPosition.y > (ch.characterPosition.y + ch.characterDimensions.y);
        return !(charXLeft || charXRight || charYUp || charYDown);
    }

    public boolean checkCollision(final Character ch){

        //ch.checkPrevPosition - ch.position > platformHeight {checkIfWentTrough}
        Vector2D prevPosition = ch.getPrevPosition();
        if(prevPosition == null)
            return false;
        if(prevPosition.y - ch.characterPosition.y >= (characterDimensions.y*(1-PLATFORM_Y_LEEWAY))){
            if((prevPosition.x <= (characterPosition.x + characterDimensions.x))&&(prevPosition.x >= characterPosition.x)){
                if(prevPosition.y>(characterPosition.y+characterDimensions.y) && ch.characterPosition.y<(characterPosition.y + (characterDimensions.y*PLATFORM_Y_LEEWAY))){
                    return true;
                }
            }
        }

        return ch.isFalling() &&
               isIntersecting(ch) &&
                ((ch.characterPosition.y>(characterPosition.y + PLATFORM_Y_LEEWAY * characterDimensions.y)) ||

                //stairs effect
                        ((
                (ch.characterPosition.x>(characterPosition.x + characterDimensions.x - (ch.characterDimensions.x*1.5))) ||
                (ch.characterPosition.x<(characterPosition.x + (ch.characterDimensions.x*1.5)))))
                        && (characterPosition.y+(characterDimensions.y/2))<=(ch.characterPosition.y+0.25*ch.characterDimensions.y));
    }

    public boolean isCharacterOnThisPlatform(final Character ch){
        return isIntersecting(ch) && !ch.isMovingY();
    }

    public double getTopY(){
        return characterPosition.y + characterDimensions.y*(PLATFORM_Y_LEEWAY+0.1);
    }


}
