package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

public class Platform extends Entity{

    public static double fractionOfScreenHeightForPlatform = 1.0/10.0;

    public Platform (final Vector2D position, final Vector2D dimensions){
        super(position, dimensions);

    }

    private static final double PLATFORM_HERO_X_LEEWAY = 0.3;
    private static final double PLATFORM_Y_LEEWAY = 0.5;


    private boolean isIntersecting(final Character ch){

        double charPosX = ch.getXPos();
        double charSizeX = ch.getXDim();
        if(ch instanceof Hero){
            charPosX = ch.getXPos()+PLATFORM_HERO_X_LEEWAY*ch.getXDim();
            charSizeX = (1-2*PLATFORM_HERO_X_LEEWAY)*ch.getXDim();
        }

        final boolean charXLeft = (characterPosition.x + characterDimensions.x) < charPosX;
        final boolean charXRight = characterPosition.x > (charPosX + charSizeX);
        final boolean charYDown = (characterPosition.y + characterDimensions.y) < ch.characterPosition.y;
        final boolean charYUp = characterPosition.y > (ch.characterPosition.y + ch.characterDimensions.y);
        return !(charXLeft || charXRight || charYUp || charYDown);
    }


    private boolean checkWentTrough(Entity ch){
        Vector2D prevPosition = ((Character)ch).getPrevPosition();
        if(prevPosition == null)
            return false;
        if(prevPosition.y - ch.characterPosition.y >= (characterDimensions.y*(1-PLATFORM_Y_LEEWAY))){
            if((prevPosition.x <= (characterPosition.x + characterDimensions.x))&&(prevPosition.x >= characterPosition.x)){
                if(prevPosition.y>(characterPosition.y+characterDimensions.y) && ch.characterPosition.y<(characterPosition.y + (characterDimensions.y*PLATFORM_Y_LEEWAY))){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean checkCollision(Entity ch){

        if(checkWentTrough(ch)){
            return true;
        }

        double charPosX = ch.getXPos();
        double charSizeX = ch.getXDim();
        if(ch instanceof Hero){
            charPosX = ch.getXPos()+PLATFORM_HERO_X_LEEWAY*ch.getXDim();
            charSizeX = (1-2*PLATFORM_HERO_X_LEEWAY)*ch.getXDim();
        }

        return ((Character)ch).isFalling() &&
               isIntersecting((Character)ch) &&
                ((ch.characterPosition.y>(characterPosition.y + PLATFORM_Y_LEEWAY * characterDimensions.y))
/*                ||
                //stairs effect
                        ((
                (charPosX>(charPosX + charSizeX - (charSizeX*1.5))) ||
                (charPosX<(charPosX + (charSizeX*1.5)))))
                        && (characterPosition.y+(characterDimensions.y/2))<=(ch.characterPosition.y+0.25*ch.characterDimensions.y)
                */
            );
    }

    public boolean isCharacterOnThisPlatform(final Character ch){
        return isIntersecting(ch) && !ch.isMovingY();
    }

    public double getTopY(){
        return characterPosition.y + characterDimensions.y*(PLATFORM_Y_LEEWAY+0.1);
    }


}
