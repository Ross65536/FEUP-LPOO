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
    }

    @Test
    public void heroJump()
    {

    }
}
