package com.mygdx.game.gameLogic;

//added
public class GameWorld
{
    private Vector2D worldDimensions;
    private Hero hero;


    public GameWorld(double dimX, double dimY, Hero hero)
    {
        worldDimensions = new Vector2D(dimX, dimY);
        this.hero = hero;


    }



    public Hero getHero()
    {
        return hero;
    }

    /**
     * Updates all characters in the world, checks collisions
     *
     * @param deltaT
     */
    public void update (float deltaT)
    {
        //added
 //       gameMode.update(deltaT);
    }
}
