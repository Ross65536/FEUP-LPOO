package com.mygdx.game.gameLogic;

                       //added
public class GameWorld extends  GameSettings
{
    private Vector2D worldDimensions;
    private Hero hero;

    //added
   // GameMode gameMode;

    public GameWorld(double dimX, double dimY, Hero hero)
    {
        worldDimensions = new Vector2D(dimX, dimY);
        this.hero = hero;
        //added
   //     this.gameMode = null;

    }

    //added
    void startGame(){
        switch (this.mode){
            case SURVIVAL:
 //               gameMode = new SurvivalGame(hero, worldDimensions, batch);
        }
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
