package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

public class Platform extends Entity{

    public static double fractionOfScreenHeightForPlatform = 1.0/10.0;

    public Platform (final Vector2D position, final Vector2D dimensions){
        super(position, dimensions);

    }

    public static final double PLATFORM_HERO_X_LEEWAY = 0.3;
    public static final double PLATFORM_Y_LEEWAY = 0.5;


    private boolean isIntersecting(final Character ch){

        double charPosX = ch.getXPos();
        double charSizeX = ch.getXDim();
        double charPosY = ch.getYPos();
        double charSizeY = ch.getYDim();

        if(ch instanceof Hero){
            charPosX = ch.getXPos()+PLATFORM_HERO_X_LEEWAY*ch.getXDim();
            charSizeX = (1-2*PLATFORM_HERO_X_LEEWAY)*ch.getXDim();
        }

        final boolean charXLeft = (characterPosition.x + characterDimensions.x) < charPosX;
        final boolean charXRight = characterPosition.x > (charPosX + charSizeX);
        final boolean charYDown = (characterPosition.y + characterDimensions.y) < charPosY;
        final boolean charYUp = characterPosition.y > (charPosY + charSizeY);
        return !(charXLeft || charXRight || charYUp || charYDown);
    }


    private boolean checkWentTrough(Entity ch){

        double charPosY = ch.getYPos();

        Vector2D prevPosition = ((Character)ch).getPrevPosition();
        if(prevPosition == null)
            return false;
        if(prevPosition.y - charPosY >= (characterDimensions.y*(1-PLATFORM_Y_LEEWAY))){
            if((prevPosition.x <= (characterPosition.x + characterDimensions.x))&&(prevPosition.x >= characterPosition.x)){
                if(prevPosition.y>(characterPosition.y+characterDimensions.y) && charPosY<(characterPosition.y + (characterDimensions.y*PLATFORM_Y_LEEWAY))){
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

        double charPosY = ch.getYPos();

        return ((Character)ch).isFalling() &&
               isIntersecting((Character)ch) &&
                ((charPosY>=(characterPosition.y + PLATFORM_Y_LEEWAY * characterDimensions.y))
            );
    }

    public boolean isCharacterOnThisPlatform(final Character ch){
        return isIntersecting(ch) && !ch.isMovingY();
    }

    public double getTopY(){
        return characterPosition.y + characterDimensions.y*(PLATFORM_Y_LEEWAY+0.1);
    }


}
