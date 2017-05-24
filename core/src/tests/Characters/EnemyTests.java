package tests.Characters;


import com.mygdx.game.CommonConsts;
import com.mygdx.game.CommonConsts;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.EnemyFlying;
import com.mygdx.game.gameLogic.Characters.EnemyGround;


import org.junit.Test;
import static org.junit.Assert.*;

public class EnemyTests {
    class EnemyInstance extends Enemy
    {
        public EnemyInstance(Vector2D position, Vector2D dimensions, Vector2D speed, boolean bossEn)
        {
            super(position, dimensions, speed, bossEn);
        }

        @Override
        public boolean isFlyingType() {
            return false;
        }

        @Override
        public int getArrayIndex() {
            return 0;
        }


    }

    @Test
    public void baseEnemyTest()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1.0,2.0);
        Vector2D speed = new Vector2D(2.0, 0.0);

        EnemyInstance enemyInstance = new EnemyInstance(pos, dims, speed, false);
        assertFalse(enemyInstance.isBossType());
        assertTrue(enemyInstance.isMovingRight());

        enemyInstance.setMovementDirection(false);
        assertFalse(enemyInstance.isMovingRight());

        enemyInstance.setMovementDirection(true);
        assertTrue(enemyInstance.isMovingRight());

        enemyInstance = new EnemyInstance(pos, dims, speed, true);

        assertTrue(enemyInstance.isBossType());

    }

    @Test
    public void groundEnemyTests()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1.0,2.0);
        Vector2D speed = new Vector2D(2.0, 0.0);
        EnemyGround enemy = new EnemyGround(pos,dims,speed, false);
        assertFalse(enemy.isFlyingType());
        assertEquals(enemy.getArrayIndex(), CommonConsts.ENEMY_GROUND_ARRAY_INDEX);

    }

    @Test
    public void flyingEnemyTests()
    {
        Vector2D pos = new Vector2D(0,0);
        Vector2D dims = new Vector2D(1.0,2.0);
        Vector2D speed = new Vector2D(2.0, 0.0);
        EnemyFlying enemy = new EnemyFlying(pos,dims,speed, false);
        assertTrue(enemy.isFlyingType());
        assertEquals(enemy.getArrayIndex(), CommonConsts.ENEMY_FLYING_ARRAY_INDEX);

    }
}
