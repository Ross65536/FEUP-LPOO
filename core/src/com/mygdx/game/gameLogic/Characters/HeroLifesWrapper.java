package com.mygdx.game.gameLogic.Characters;


import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;

import java.util.Random;

public class HeroLifesWrapper extends Hero implements HeroLifesFeature {
    private int numberOfLifes;

    private double nockbackSpeed = 0;

    private double prevSpeed = 0;

    final private double nockbackRecoverySpeed = 800;

    private boolean isBeingNockedbacked = false;

    private boolean immunity = false;

    final private double fullImminityTime = 4;//s

    final private double startingNockbackSpeed = 350;

    private double immunityTime = 0;

    private Random random;

    public HeroLifesWrapper(final Vector2D position, final Vector2D dimensions, double heroMaxSpeedXMult, int numberOfLifes){
        super(position, dimensions, heroMaxSpeedXMult);
        random = new Random();
        this.numberOfLifes = numberOfLifes;
    }

    public int takeLife(){
        if(immunity || (numberOfLifes==0)){
            return numberOfLifes;
        }
        numberOfLifes--;
        nockback(random.nextBoolean());
        setImmunity();
        return numberOfLifes;
    }

    public int getNumberOfLifes(){
        return numberOfLifes;
    }

    public boolean isImmune(){
        return immunity;
    }

    @Override
    public void update(float deltaT)
    {
        super.update(deltaT);
        if(isBeingNockedbacked){
            characterPosition.x = characterPosition.x + nockbackSpeed*deltaT;

            decreaseNockbackSpeed(deltaT);
        }
        if(immunity){
            decreaseImmuneTime(deltaT);
        }
    }

    private void decreaseImmuneTime(float deltaT){
        immunityTime-=deltaT;
        if(immunityTime<=0){
            immunityTime = 0;
            immunity = false;
        }
    }

    private void decreaseNockbackSpeed(float deltaT){
        if(isBeingNockedbacked){
            prevSpeed = nockbackSpeed;
            if(nockbackSpeed<0){
                nockbackSpeed+=nockbackRecoverySpeed*deltaT;
            }else
            {
                nockbackSpeed-=nockbackRecoverySpeed*deltaT;
            }

            if((prevSpeed<0 && nockbackSpeed>=0) || (prevSpeed>0 && nockbackSpeed<=0)){
                nockbackSpeed = 0;
                isBeingNockedbacked = false;
            }
        }
    }

    private void setImmunity(){
        immunity = true;
        immunityTime = fullImminityTime;
    }

    private  void nockback(boolean direction){
        this.jump(3);
        if(direction) {
            this.nockbackSpeed = -startingNockbackSpeed;
            this.prevSpeed = 0;
        }
        else {
            this.prevSpeed = 0;
            this.nockbackSpeed = startingNockbackSpeed;
        }
        isBeingNockedbacked = true;
    }
}
