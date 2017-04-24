package com.mygdx.game.gameLogic;

import com.mygdx.game.gameLogic.Characters.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//added
public class GameWorld
{
    private Vector2D worldDimensions;
    private Hero hero;
    private ArrayList<Enemy> enemies;

    public GameWorld(double dimX, double dimY, Hero hero)
    {
        worldDimensions = new Vector2D(dimX, dimY);
        this.hero = hero;
        enemies = new ArrayList<Enemy>();

        //TODO remove
        createDummyEnemies();
    }

    /**
     * Updates all characters in the world, checks collisions
     *
     * @param deltaT
     */
    public void update (float deltaT)
    {
        hero.updatePos(deltaT);
    }

    public final CharacterInfo getHeroInfo() {
        return hero;
    }

    public HeroInputs getHeroInput() {
        return hero;
    }

    /**
     * return an unmodifiable collection
     */
    public List<EnemyInfo> getEnemiesInfo()
    {
        List<EnemyInfo> ret = (List) enemies; //should not have a copy operation here
        return Collections.unmodifiableList(ret); //or here
    }

    private void createDummyEnemies () //testing function
    {
        final double heroX = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double heroYDim = hero.getYDim();

        Enemy enemy1 = new Enemy(heroX + 2, 0, heroXDim, 0);
        enemies.add(enemy1);
        Enemy enemy2 = new Enemy(heroX - 5, 0, heroXDim, 0);
        enemies.add(enemy2);
    }

}
