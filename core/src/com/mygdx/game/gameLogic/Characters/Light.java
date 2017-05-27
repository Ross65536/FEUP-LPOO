package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

/**
 * The light used around around the hero and the rechargers.
 */
public class Light extends Entity{

    protected boolean oscilating;

    private double oscilationVelocity=Math.PI;//per second

    private double oscilationAngle = 0;

    private double oscilationLength;

    private double radious;

    private double originalRadious;

    private double velocityDisappearance;

    /**
     * Constructor.
     * @param position
     * @param radious
     * @param oscilating
     * @param oscilationLengthFraction
     */
    public Light (final Vector2D position, final double radious, boolean oscilating, double oscilationLengthFraction) {
        super(position, new Vector2D(radious,radious));
        this.radious = radious;
        this.originalRadious = radious;
        this.oscilating = oscilating;
        this.oscilationLength = radious*oscilationLengthFraction;
        this.velocityDisappearance = radious/40;
    }

    /**
     * Sets the velocity of the light's fading out.
     * NOTE: the argument is the inverse of the time it stays alight.
     * (i.e (1/10) means 10 seconds alight)
     * @param perDis percentage of the original radious that the lights fades for each second
     */
    public void setVelocityDisappearance(double perDis){
        this.velocityDisappearance = originalRadious*perDis;
    }

    /**
     * Resets the radious of the light.
     */
    public void resetRadious(){
        this.radious = this.originalRadious;
        this.setInnerRadious(this.originalRadious);
    }

    /**
     * Sets the radious to the argument.
     * @param radious
     */
    public void setInnerRadious(double radious){
        characterDimensions.x = radious;
        characterDimensions.y = radious;
    }

    /**
     * Updates the oscilation of the light, is it is oscilating.
     * @param deltaT
     */
    public void updateOscilation(double deltaT){
        if(oscilating){
            oscilationAngle+=oscilationVelocity*deltaT;
            oscilationAngle %= Math.PI*2;
            this.setInnerRadious(this.radious+oscilationLength*Math.sin(oscilationAngle)*Math.sin(oscilationAngle));
        }
    }

    /**
     * Updates the radious of the light.
     * @param deltaT
     */
    public void updateRadious(double deltaT){
        this.radious-=this.velocityDisappearance*deltaT;
        if(this.radious>this.originalRadious)
            this.radious = this.originalRadious;
        if(this.radious<0)
            this.radious = 0;
        if(!isOscilating())
            this.setInnerRadious(this.radious);
    }

    public float getRadiousPercentage(){
        return (float)(this.radious/this.originalRadious);
    }

    /**
     * Returns the inner radious.
     * @return
     */
    public double getRadious(){
        return characterDimensions.y;
    }

    public double getRealRadious() { return this.radious;}

    public boolean isOscilating(){
        return oscilating;
    }

}
