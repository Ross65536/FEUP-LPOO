package com.mygdx.game.gameLogic.Characters;

import com.mygdx.game.Vector2D;

public class Light extends Entity{

    private boolean oscilating;

    private double oscilationVelocity=Math.PI;//per second

    private double oscilationAngle = 0;

    private double oscilationLength;

    private double radious;

    private double originalRadious;

    private double velocityDisappearance;

    public Light (final Vector2D position, final double radious, boolean oscilating, double oscilationLengthFraction) {
        super(position, new Vector2D(radious,radious));
        this.radious = radious;
        this.originalRadious = radious;
        this.oscilating = oscilating;
        this.oscilationLength = radious*oscilationLengthFraction;
        this.velocityDisappearance = radious/40;
    }

    public void setVelocityDisappearance(double perDis){
        this.velocityDisappearance = originalRadious*perDis;
    }

    public void setOscilationVelocity(double oscilationVelocity){
        this.oscilationVelocity = oscilationVelocity;
    }

    public void setOscilationLengthFraction(double oscilationLengthFraction){
        this.oscilationLength = radious*oscilationLengthFraction;//10% the radious
    }

    public void resetRadious(){
        this.radious = this.originalRadious;
    }

    private void setInnerRadious(double radious){
        characterDimensions.x = radious;
        characterDimensions.y = radious;
    }

    public void updateOscilation(double deltaT){
        if(oscilating){
            oscilationAngle+=oscilationVelocity*deltaT;
            oscilationAngle %= Math.PI*2;
            this.setInnerRadious(this.radious+oscilationLength*Math.sin(oscilationAngle)*Math.sin(oscilationAngle));
        }
    }

    public void updateRadious(double deltaT){
        this.radious-=this.velocityDisappearance*deltaT;
        if(this.radious>this.originalRadious)
            this.radious = this.originalRadious;
        if(this.radious<0)
            this.radious = 0;
    }

    public float getRadiousPercentage(){
        return (float)(this.radious/this.originalRadious);
    }

    public double getRadious(){
        return characterDimensions.y;
    }

    public boolean isOscilating(){
        return oscilating;
    }

}
