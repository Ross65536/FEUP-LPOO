package com.mygdx.game.gameLogic;


import com.mygdx.game.Constants;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.Hero;

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

    @Override
    public void updateSpecific(float deltaT) {
        tryLoopHero();
    }

    ////
    protected void checkHeroJump()
    {
        if (hero.isJumping() && hero.getYPos() < 0.0) //hero jumping hit ground
        {
            hero.stopJump();
            hero.setYPos(0.0);
        }
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

    public void moveHeroHorizontal(final double heroXMovement)
    {
        hero.setXMovement(heroXMovement);
    }

    @Override
    public void heroJump(final double gravityStrength) {
        hero.jump(gravityStrength);
    }

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
}
