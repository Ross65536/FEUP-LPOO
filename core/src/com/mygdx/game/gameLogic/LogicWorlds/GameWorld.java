package com.mygdx.game.gameLogic.LogicWorlds;

import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.Vector2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

//added
public abstract class GameWorld implements IGameWorld, IGameWorldHeroInputs {
    static protected Random random = new Random();
    //constants
    protected static final double ENEMY_DELETION_RANGE_MULT = 3.0; //has performance implications
    //data
    protected Vector2D worldDimensions;
    protected Hero hero;
    protected ArrayList<Enemy> enemies;
    protected boolean gamePlayable;
    protected StageDirector stageDirector;



    public GameWorld(final Vector2D worldDims, Hero hero, StageDirector stageDirector)
    {
        worldDimensions = new Vector2D(worldDims);
        this.hero = hero;
        this.stageDirector = stageDirector;

        enemies = new ArrayList<Enemy>();
        this.gamePlayable = true;
    }

    //// Queries --------------
    /**
     * Return hero information (position, dimension, etc).
     * @return
     */
    public final HeroInfo getHeroInfo() {
        return hero;
    }

    /**
     * return an unmodifiable collection of enemies containing information about them to be able to draw them
     */
    public List<EnemyInfo> getEnemiesInfo()
    {
        List<EnemyInfo> ret = (List) enemies;
        return Collections.unmodifiableList(ret);
    }

    public boolean isGamePlayable()
    {
        return this.gamePlayable;
    }

    public IGameWorldHeroInputs getWorldInputs()
    {
        return this;
    }

    //// abstract ----------------
    /**
     * Updates all characters in the world, checks collisions
     *
     * @param deltaT
     */
//    abstract protected void updateSpecific (float deltaT);
    abstract public void update (float deltaT);
    abstract protected void checkHeroJump();
//    abstract public void moveHeroHorizontal(final double heroXMovement);
//    abstract public void heroJump(final double gravityStrength);
    abstract protected void placeEnemy(Enemy enemy);

    //// updates ---------------

    protected void tryGenerateEnemy()
    {
        final Enemy enemy = stageDirector.tryGenerateEnemy();
        if (enemy != null)
        {
            placeEnemy(enemy); //specific placement
            enemies.add(enemy);
        }
    }

    protected void updateHero(float deltaT)
    {
        hero.update(deltaT);
        checkHeroJump();
    }

    protected int updateEnemies(float deltaT) {
        final double enemyDeletionRange = ENEMY_DELETION_RANGE_MULT * worldDimensions.y;
        int numDeletions=0;

        Iterator<Enemy> itr = enemies.iterator();
        while(itr.hasNext())
        {
            Enemy enemy = itr.next();
            enemy.update(deltaT);

            final double deltaXPos = Math.abs(enemy.getXPos() - hero.getXPos());
            if (deltaXPos > enemyDeletionRange) //remove enemy if out of range
            {
                itr.remove();
                numDeletions++;
            }
        }
        return numDeletions;
    }

    /**
     * @return number of collisions
     */
    protected long checkHeroCollisions() {
        long numCollisions =0;
        for (Enemy en : enemies)
            if (hero.checkCollision(en))
                numCollisions ++;

        return numCollisions;
    }


}
