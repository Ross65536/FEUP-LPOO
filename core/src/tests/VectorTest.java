package tests;


import com.mygdx.game.Vector2D;

import org.junit.Test;
import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void vectorTests()
    {
        Vector2D vector = new Vector2D(1.0, 2.0);
        assertTrue(vector.x == 1.0 && vector.y == 2.0);

        vector.x = 10.0;
        vector.y = -4.0;
        assertTrue(vector.x == 10.0 && vector.y == -4.0);

        Vector2D vector2 = (Vector2D) vector.clone();
        assertTrue(vector2.x == vector.x && vector2.y == vector.y);

        vector2 = new Vector2D(vector);
        assertTrue(vector2.x == vector.x && vector2.y == vector.y);

        vector2.setXY(0.1,0.1);
        assertTrue(vector2.x == 0.1 && vector2.y == 0.1);
    }
}
