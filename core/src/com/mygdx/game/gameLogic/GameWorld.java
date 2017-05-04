package com.mygdx.game.gameLogic;

import com.mygdx.game.CommonConstants;
import com.mygdx.game.gameLogic.Characters.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

//added
public class GameWorld
{
    private static final double ENEMY_MAX_RANGE = 3.0; //has performance implications

    private Vector2D worldDimensions;
    private Hero hero;
    private ArrayList<Enemy> enemies;
    private boolean gamePlayable;

    public GameWorld(double dimX, double dimY, Hero hero)
    {
        worldDimensions = new Vector2D(dimX, dimY);
        this.hero = hero;
        enemies = new ArrayList<Enemy>();
        this.gamePlayable = true;

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
        if (! isGamePlayable())
            return;

        this.updateEnemies(deltaT);
        this.updateHero(deltaT);

        if(this.checkHeroCollisions() > 0)
        {
            gamePlayable = false;

            //TODO remove
            System.out.println("Game Lost");
        }

    }

    private void updateHero(float deltaT)
    {
        hero.updatePos(deltaT);
        if (hero.isJumping())
            this.checkHeroJump();

        //loop around level
        final double heroXPos = hero.getXPos();
        if (heroXPos < 0.0)
            hero.setXPos(worldDimensions.x + heroXPos);
        else if (heroXPos > worldDimensions.x)
            hero.setXPos(heroXPos - worldDimensions.x);

    }

    /**
     * @return number of collisions
     */
    private long checkHeroCollisions() {
        long ret =0;
        for (Enemy en : enemies)
            if (hero.checkCollision(en))
                ret ++;

        return ret;
    }

    private void updateEnemies(float deltaT) {
        final double heroXPos = hero.getXPos();
        final double WORLD_RANGE = ENEMY_MAX_RANGE * worldDimensions.y;
        Iterator<Enemy> itr = enemies.iterator();

        while(itr.hasNext())
        {
            Enemy en = itr.next();
            en.updatePos(deltaT);

            final double enXPos = en.getXPos();
            final double deltaXPos = Math.abs(enXPos - heroXPos);
            if (deltaXPos > WORLD_RANGE) //remove enemy if out of range
                itr.remove();
        }
    }

    private void checkHeroJump()
    {
        if (hero.getYPos() < 0.0) //hero jumping hit ground
            hero.stopJump(0.0);
    }

    public final HeroInfo getHeroInfo() {
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
        List<EnemyInfo> ret = (List) enemies;
        return Collections.unmodifiableList(ret); 
    }

    private void createDummyEnemies () //testing function
    {
        final double heroX = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double heroYDim = hero.getYDim();

        final double scale = CommonConstants.getDimYRatio(Enemy.class);
        final double aspectRatio = CommonConstants.getAspectRatio(Enemy.class);

        final double enYDim = scale * heroYDim;
        final double enXDim = enYDim * aspectRatio;

        Vector2D dims = new Vector2D(enXDim, enYDim);

        Enemy enemy1 = new Enemy(new Vector2D(heroX + 2, 0), dims, 0, 0);
        enemies.add(enemy1);
        Enemy enemy2 = new Enemy(new Vector2D(heroX - 5, 0), dims, 0, 0);
        enemies.add(enemy2);

        Enemy enemy3 = new Enemy(new Vector2D(heroX - 8, 0), dims, heroYDim, 0);
        enemies.add(enemy3);
    }

    public boolean isGamePlayable()
    {
        return this.gamePlayable;
    }

}
