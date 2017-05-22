package test.Characters;

import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Hero;

import org.junit.Test;
import static org.junit.Assert.*;



public class HeroTests {


    static final double DELTA_DOUBLE = 0.001;
    static final float DELTA_T = 0.5f;
    static final double SPEED_X = 10.0;
    @Test
    public void heroMovement()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(2,1);

        Hero hero = new Hero(pos, dims, SPEED_X);

        assertTrue(hero.isMovingRight());
        assertFalse(hero.isMoving());

        hero.setXMovement(0.0);
        assertTrue(hero.isMovingRight());
        assertFalse(hero.isMoving());
        assertEquals(hero.getAnimationTime(), 0.0, DELTA_DOUBLE);

        hero.update(DELTA_T);

        assertEquals(hero.getAnimationTime(), 0.0, DELTA_DOUBLE);

        hero.update(DELTA_T);
        final double mov_amount = 0.8;
        hero.setXMovement(mov_amount);
        assertTrue(hero.isMovingRight());
        assertTrue(hero.isMoving());

        hero.update(DELTA_T);

        assertEquals(hero.getAnimationTime(), DELTA_T, DELTA_DOUBLE);
        assertEquals(hero.getXPos(), DELTA_T * SPEED_X * mov_amount, DELTA_DOUBLE);

        hero.update(DELTA_T);

        assertEquals(hero.getAnimationTime(), DELTA_T * 2 - 1.0, DELTA_DOUBLE);
        assertEquals(hero.getXPos(), 2 * DELTA_T * SPEED_X * mov_amount, DELTA_DOUBLE);

        final double mov_amount_neg = -0.6;
        hero.setXMovement(mov_amount_neg);
        assertFalse(hero.isMovingRight());
        assertTrue(hero.isMoving());

        hero.update(DELTA_T);

        assertEquals(hero.getAnimationTime(), DELTA_T * 3 - 1.0, DELTA_DOUBLE);
        assertEquals(hero.getXPos(), 2 * DELTA_T * SPEED_X * mov_amount + DELTA_T * SPEED_X * mov_amount_neg, DELTA_DOUBLE);

        hero.update(DELTA_T);

        assertEquals(hero.getAnimationTime(), DELTA_T * 4 - 2.0, DELTA_DOUBLE);
        assertEquals(hero.getXPos(), 2 * DELTA_T * SPEED_X * mov_amount + 2* DELTA_T * SPEED_X * mov_amount_neg, DELTA_DOUBLE);

        hero.setXMovement(0.0);
        hero.update(DELTA_T);
        assertFalse(hero.isMovingRight());
        assertFalse(hero.isMoving());
        assertEquals(hero.getAnimationTime(), 0.0, DELTA_DOUBLE);
    }

    @Test(timeout = 100)
    public void heroJump()
    {
        final double heroYDim = 2;

        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1,heroYDim);

        Hero hero = new Hero(pos, dims, SPEED_X);

        assertFalse(hero.isJumping());

        final double gravity_strength = 0.5;
        hero.jump(gravity_strength);
        assertTrue(hero.isJumping());
        assertTrue(hero.isMoving());
        double heroYPosPrev = hero.getYPos();

        while (true) //check that jump eventually goes down
        {
            hero.update(DELTA_T);
            double heroYNew = hero.getYPos();
            if(heroYNew < heroYPosPrev)
                break;
            else
                heroYPosPrev = heroYNew;
        }

        while (true) //check that jum reaches ground eventually
        {
            hero.update(DELTA_T);
            double heroYNew = hero.getYPos();
            if(heroYNew <= 0.0)
                break;
        }
        assertTrue(hero.isMoving());
        assertTrue(hero.isFalling());
        assertTrue(hero.isJumping());

        double heroYPos = hero.getYPos();
        double heroXPos = hero.getXPos();

        hero.stopJump();
        hero.update(DELTA_T);

        assertFalse(hero.isMoving());
        assertFalse(hero.isFalling());
        assertFalse(hero.isJumping());

        assertTrue(heroXPos == hero.getXPos() && heroYPos == hero.getYPos());


        //fall method
        hero.setYPos(0.0);

        heroYPosPrev = hero.getYPos();
        hero.fall(gravity_strength);
        hero.update(DELTA_T);
        hero.update(DELTA_T);
        double heroYNew = hero.getYPos();
        assertTrue(heroYNew < heroYPosPrev);

        assertTrue(hero.isMoving());
        assertTrue(hero.isFalling());
        assertTrue(hero.isJumping());
    }

    private final double HERO_COLLISION_OFFSET = 0.9;
    @Test
    public void heroCollisions()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1,2);

        Hero hero = new Hero(pos, dims, 0.0);

        Hero collisionObject = new Hero(pos, dims, 0.0);

        final double heroYDim = hero.getYDim();
        final double heroXDim = hero.getYDim();

        assertTrue(hero.checkCollision(collisionObject));
        collisionObject.setXPos(10.0);
        assertFalse(hero.checkCollision(collisionObject));
        collisionObject.setXPos(0.0);
        collisionObject.setYPos(3.0);
        assertFalse(hero.checkCollision(collisionObject));

        //hero doesnt collide exactly at the border, but a bit in
        collisionObject.setYPos(0.0);
        collisionObject.setXPos(HERO_COLLISION_OFFSET * heroXDim );
        assertFalse(hero.checkCollision(collisionObject));
        collisionObject.setYPos(HERO_COLLISION_OFFSET * heroYDim);
        collisionObject.setXPos(0.0);
        assertFalse(hero.checkCollision(collisionObject));

    }
}

