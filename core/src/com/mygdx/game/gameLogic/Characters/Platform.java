package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.Random;

public class Platform extends Character{

    public static double fractionOfScreenHeightForPlatform = 1.0/15.0;

    public Platform (final Vector2D position, final Vector2D dimensions){
        super(position, dimensions, (new Vector2D(0,0)));

    }

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

        return isIntersecting(ch) && (ch.characterPosition.y>(characterPosition.y + (1-PLATFORM_Y_LEEWAY) * characterDimensions.y)) && ch.isFalling();
    }

    public boolean isCharacterOnThisPlatform(final Character ch){
        return isIntersecting(ch) && !ch.isFlying();
    }

    public double getTopY(){
        return characterPosition.y + characterDimensions.y*0.5;
    }



    /*


    public static Platform tryCreate(double diffPosX,double diffPosY,double diffNegX, double diffNegY, double heroXPos, double heroYPos, Vector2D camaraPos){
        Random randomGenerator = new Random();
        if(randomGenerator.nextInt(10)!=0){
            return null;
        }
        if(diffPosY!=0.0){
            if(diffPosX!=0.0){
                double newXPos = heroXPos+camaraPos.x;
                double newYPos = heroYPos + randomGenerator.nextDouble()*camaraPos.y;

                double randomMinY = (randomGenerator.nextDouble()+0.5);
                if(randomMinY>1.0)
                    randomMinY =1.0;
                double randomMinX = (randomGenerator.nextDouble()+0.5);
                if(randomMinX>1.0)
                    randomMinX =1.0;

                double newXDim = randomMinX*MAX_X_DIM;
                double newYDim = randomMinY*MAX_Y_DIM;

                return new Platform(new Vector2D(newXPos, newYPos), new Vector2D(newXDim, newYDim));
            }
            if(diffNegX!=0.0){

            }
        }
        if(diffNegY!=0){
            if(diffPosX!=0.0){

            }
            if(diffNegX!=0){

            }
        }
        return null;
    }
*/
    public String getAssociatedImagePath(){
        return PathConstants.PLATFORM_IMAGE_PATH;
    }

}
