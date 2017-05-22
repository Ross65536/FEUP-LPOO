package com.mygdx.game.LIBGDXwrapper.gameAdapter.FeatureVisuals;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.HeroInfo;

public class FarAwayBackgroundVisualHandler {
    private Texture backgroundTex;
    private HeroInfo heroInfo;
    private SpriteBatch drawBatch;
    private double cameraWidth;
    private double cameraHeight;
    private double worldYDim;
    private double worldXDim;
    private double verticalMargins;
    private double horizontalMargins;
    private float heroXCenter;
    private float heroYCenter;
    private double worldLengthX;
    private double worldLengthY;

    public FarAwayBackgroundVisualHandler(Texture backgroundTex, float distanceX ,float distanceY, HeroInfo hero, SpriteBatch drawBatch, Vector2D worldDims, Vector2D cameraDims){
        this.backgroundTex = backgroundTex;
        this.heroInfo = hero;
        this.drawBatch = drawBatch;
        this.cameraWidth = cameraDims.x;
        this.cameraHeight = cameraDims.y;
        this.worldXDim = worldDims.x;
        this.worldYDim = worldDims.y;

        worldLengthX = worldXDim-(cameraWidth);
        verticalMargins = cameraWidth * distanceX;

        worldLengthY = 1.5*worldYDim-(cameraHeight);
        horizontalMargins = cameraHeight * distanceY;

    }

    /**
     * Draws backgroundTex according to the distance given.
     */
    public void drawFarAwayBackground(){

        float startX = getFarAwayBackgroundStartX();
        float startY = getFarAwayBackgroundStartY();

        drawBatch.draw(backgroundTex,startX ,startY,(float)(cameraWidth+verticalMargins), (float)(cameraHeight + horizontalMargins));

    }

    protected float getFarAwayBackgroundStartY(){


        heroYCenter = (float)(heroInfo.getYPos() + heroInfo.getYDim()/2.0);
        double verticalMarginY = heroYCenter-(cameraHeight/2f);

        if(verticalMarginY<0)
            verticalMarginY = 0;
        else
            if(verticalMarginY>worldLengthY)
                verticalMarginY = worldLengthY;

        double horizontalMarginPercentageY = verticalMarginY/worldLengthY;

        float startY = (float)((heroYCenter-cameraHeight/2f)-horizontalMargins*horizontalMarginPercentageY);

        if (startY <  0)
            startY = 0;
        else
            if(heroYCenter >=  (1.5*worldYDim-(cameraHeight / 2f)))
                startY = (float)(1.5*worldYDim-horizontalMargins-cameraHeight);

        return startY;
    }

    protected float getFarAwayBackgroundStartX(){

        heroXCenter = (float)(heroInfo.getXPos() + heroInfo.getXDim()/2.0);
        double verticalMarginX = heroXCenter-(cameraWidth/2f);

        if(verticalMarginX<0)
            verticalMarginX = 0;
        else
            if(verticalMarginX>worldLengthX)
                verticalMarginX = worldLengthX;

        double verticalMarginPercentage = verticalMarginX/worldLengthX;

        float startX = (float)((heroXCenter-cameraWidth/2f)-verticalMargins*verticalMarginPercentage);

        if (startX <  0)
            startX = 0;
        else
            if(heroXCenter >=  (worldXDim-(cameraWidth / 2f)))
                startX = (float)(worldXDim-verticalMargins-cameraWidth);

        return startX;
    }
}
