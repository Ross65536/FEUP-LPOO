package com.mygdx.game.gameLogic.LogicWorlds;


import com.mygdx.game.Constants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.StatisticsInput;
import com.mygdx.game.gameLogic.Vector2D;

public class DiscWorld extends GameWorld {
    protected static final double ENEMY_GENERATION_YMULT = 1.0;


    public DiscWorld(final Vector2D worldDims, Hero hero, com.mygdx.game.gameLogic.GameDirector.StageDirector stageDirector)
    {
        super(worldDims, hero, stageDirector);

        //TODO remove
        if (Constants.INPUT_DEBUG)
            createDummyEnemies();
    }

    //// specific functions -------------
    protected void tryLoopHero() {
        //loop around level
        final double heroXPos = hero.getXPos();
        if (heroXPos < 0.0)
            hero.setXPos(worldDimensions.x + heroXPos);
        else if (heroXPos > worldDimensions.x)
            hero.setXPos(heroXPos - worldDimensions.x);
    }

    protected void createDummyEnemies () //testing function
    {
        final double heroX = hero.getXPos();
        final double heroXDim = hero.getXDim();
        final double heroYDim = hero.getYDim();

        final Constants.CharacterConstants characterConstants = Constants.getEnemyConstants(EnemyGround.class);

        final double enYDim = characterConstants.dimYMult * heroYDim;
        final double enXDim = enYDim * characterConstants.aspectRatio;

        Vector2D dims = new Vector2D(enXDim, enYDim);

        Enemy enemy1 = new EnemyGround(new Vector2D(heroX + 2, 0), dims, new Vector2D(0,0));
        enemies.add(enemy1);
        Enemy enemy2 = new EnemyGround(new Vector2D(heroX - 5, 0), dims, new Vector2D(0,0));
        enemies.add(enemy2);

//        Enemy enemy3 = new EnemyGround(new Vector2D(heroX - 8, 0), dims, new Vector2D(heroYDim * characterConstants.speedMult,0));
//        enemies.add(enemy3);
    }

    //// abstract implementations ---------------
    @Override
    public void update (float deltaT)
    {
        if (! isGamePlayable())
            return;

        final int numDestroyedEnemies = updateEnemies(deltaT);

        StatisticsInput statisticsInput = stageDirector.getStatsticsInput();
        statisticsInput.updateNumberOfGroundEnemies(- numDestroyedEnemies);
        statisticsInput.update(deltaT);

        updateHero(deltaT);
        tryLoopHero();

        if(this.checkHeroCollisions() > 0)
        {
            gamePlayable = false;

            //TODO remove
            System.out.println("Game Lost");
        }

        if (! Constants.INPUT_DEBUG)
            tryGenerateEnemy();
    }

    @Override
    protected void checkHeroJump()
    {
        if (hero.isJumping() && hero.getYPos() < 0.0) //hero jumping hit ground
        {
            hero.stopJump();
            hero.setYPos(0.0);
        }
    }

    @Override
    protected void placeEnemy(Enemy enemy) {
        enemy.setYPos(0.0);

        final double enXDelta = worldDimensions.y * ENEMY_GENERATION_YMULT;
        final double heroXPos = hero.getXPos();

        final boolean bool = random.nextBoolean(); //random

        if (bool) //left side spawn
        {
            enemy.setXPos(heroXPos - enXDelta);
            enemy.setMovementDirection(true);
        }
        else //right
        {
            enemy.setXPos(heroXPos + enXDelta);
            enemy.setMovementDirection(false);
        }
    }

    //// hero inputs -------
    public void moveHeroHorizontal(final double heroXMovement)
    {
        hero.setXMovement(heroXMovement);
    }

    @Override
    public void heroJump(final double gravityStrength) {
        hero.jump(gravityStrength);
    }
}
