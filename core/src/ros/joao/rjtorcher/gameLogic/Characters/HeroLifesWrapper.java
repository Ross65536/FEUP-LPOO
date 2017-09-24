package ros.joao.rjtorcher.gameLogic.Characters;


import ros.joao.rjtorcher.Vector2D;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLifesFeature;

import java.util.Random;

/**
 * Wrapper used on hero to use the lifes and immunity features.
 */
public class HeroLifesWrapper extends Hero implements HeroLifesFeature {
    private int numberOfLifes;

    protected double nockbackSpeed = 0;

    private double prevSpeed = 0;

    final protected double nockbackRecoverySpeed = 800;

    protected boolean isBeingNockedbacked = false;

    private boolean immunity = false;

    private double fullImminityTime = 4;//s

    final protected double startingNockbackSpeed = 350; //accelaraion

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

    protected void setImmunity(){
        immunity = true;
        immunityTime = fullImminityTime;
    }

    public void setImmunityTime(double time){
        fullImminityTime = time;
    }

    protected void nockback(boolean direction){
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

    public boolean isBeingNockedbacked(){
        return isBeingNockedbacked;
    }
}
