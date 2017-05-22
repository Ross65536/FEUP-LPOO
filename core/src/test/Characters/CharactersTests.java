package test.Characters;

import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Character;

import org.junit.Test;
import static org.junit.Assert.*;


public class CharactersTests
{
    public class Char extends Character
    {
        public Char (final Vector2D position, final Vector2D dimensions, final Vector2D speed)
        {
            super(position,dimensions,speed);
        }
        public void setXSpeed(double Xspeed)
        {
            this.characterSpeed.x=Xspeed;
        }
        public void setYSpeed(double Yspeed)
        {
            this.characterSpeed.y = Yspeed;
        }
    }

    static final double DELTA_DOUBLE = 0.001;

    @Test
    public void characterGetters()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1.0,2.0);
        Vector2D speed = new Vector2D(0.0, 0.0);

        Char character = new Char (pos, dims, speed);
        assertEquals(character.getAnimationTime(), 0.0, DELTA_DOUBLE);
        assertEquals(character.getXCenter(), pos.x + dims.x/2, DELTA_DOUBLE);
        assertEquals(character.getYCenter(), pos.y + dims.y/2, DELTA_DOUBLE);
        assertEquals(character.getXPos(), pos.x, DELTA_DOUBLE);
        assertEquals(character.getYPos(), pos.y, DELTA_DOUBLE);
        assertEquals(character.getXDim(), dims.x, DELTA_DOUBLE);
        assertEquals(character.getYDim(), dims.y, DELTA_DOUBLE);
        assertEquals(character.getMovementDirectionX(), 0.0, DELTA_DOUBLE);
        assertTrue(character.getPrevPosition() == null);

        character.setXPos(100.0);
        assertEquals(character.getXPos(), 100.0, DELTA_DOUBLE);

        character.setYPos(-100.0);
        assertEquals(character.getYPos(), -100.0, DELTA_DOUBLE);


        character = new Char(null, null, null);
        assertEquals(character.getAnimationTime(), 0.0, DELTA_DOUBLE);
        assertEquals(character.getXCenter(), 0, DELTA_DOUBLE);
        assertEquals(character.getYCenter(), 0, DELTA_DOUBLE);
        assertEquals(character.getXPos(), 0, DELTA_DOUBLE);
        assertEquals(character.getYPos(), 0, DELTA_DOUBLE);
        assertEquals(character.getXDim(), 0, DELTA_DOUBLE);
        assertEquals(character.getYDim(), 0, DELTA_DOUBLE);
    }

    @Test
    public void booleanTests()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1.0,2.0);
        Vector2D speed = new Vector2D(0.0, 0.0);

        Char character = new Char (pos, dims, speed);
        assertFalse(character.isMovingX());
        assertFalse(character.isMovingY());
        assertFalse(character.isFalling());
        assertTrue(character.isMovingRight());

        character.setXSpeed(1.0);
        assertTrue(character.isMovingRight());
        assertTrue(character.isMovingX());
        assertTrue(character.getMovementDirectionX() > 0.0);
        assertFalse(character.isMovingY());

        character.setXSpeed(-1.0);
        assertFalse(character.isMovingRight());
        assertTrue(character.isMovingX());
        assertTrue(character.getMovementDirectionX() < 0.0);

        character.setYSpeed(1.0);
        assertTrue(character.isMovingY());
        assertFalse(character.isFalling());

        character.setYSpeed(-1.0);
        assertTrue(character.isMovingY());
        assertTrue(character.isFalling());

    }

    static final float DELTA_T = 0.5f;
    static final double SPEED_X = 10.0;
    @Test
    public void updateTest()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1.0,2.0);
        Vector2D speed = new Vector2D(0.0, 0.0);

        //x movement
        Char character = new Char (pos, dims, speed);
        assertEquals(character.getAnimationTime(), 0.0, DELTA_DOUBLE);
        assertFalse(character.isMovingX());
        assertTrue(character.getPrevPosition() == null);

        character.update(DELTA_T);
        assertEquals(character.getAnimationTime(), DELTA_T, DELTA_DOUBLE);
        assertFalse(character.isMovingX());
        assertTrue(character.getPrevPosition() != null);
        Vector2D prev = character.getPrevPosition();
        assertTrue(prev.x==0.0 && prev.y ==0.0);

        character.setXSpeed(SPEED_X);
        character.update(DELTA_T);
        assertEquals(character.getAnimationTime(), 2*DELTA_T - 1.0, DELTA_DOUBLE);
        assertTrue(character.isMovingX());
        prev = character.getPrevPosition();
        assertTrue(prev.x==0.0 && prev.y ==0.0);
        assertEquals(character.getXPos(), DELTA_T * SPEED_X, DELTA_DOUBLE);

        Vector2D previous = new Vector2D(character.getXPos(), character.getYPos());
        character.update(DELTA_T);
        assertEquals(character.getAnimationTime(), (3*DELTA_T) - 1.0, DELTA_DOUBLE);
        prev = character.getPrevPosition();
        assertTrue(prev.x==previous.x && prev.y == previous.y);
        assertEquals(character.getXPos(), 2*DELTA_T * SPEED_X, DELTA_DOUBLE);

        character.update(DELTA_T);
        assertEquals(character.getAnimationTime(), (4*DELTA_T) - 2.0, DELTA_DOUBLE);

        character.update(DELTA_T);
        assertEquals(character.getAnimationTime(), (5*DELTA_T) - 2.0, DELTA_DOUBLE);

        //y movmeent
        character = new Char (pos, dims, speed);
        character.setYSpeed(SPEED_X);
        assertTrue(character.getXPos() == 0.0 && character.getYPos() == 0.0);

        character.update(DELTA_T);
        assertEquals(character.getYPos(), DELTA_T * SPEED_X, DELTA_DOUBLE );

        character.update(DELTA_T);
        assertEquals(character.getYPos(), 2 *DELTA_T * SPEED_X, DELTA_DOUBLE );

    }
}
