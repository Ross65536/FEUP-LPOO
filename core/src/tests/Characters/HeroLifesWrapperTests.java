package tests.Characters;

import ros.joao.rjtorcher.Vector2D;
import ros.joao.rjtorcher.gameLogic.Characters.HeroLifesWrapper;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HeroLifesWrapperTests {

    private TestHeroLifesWrapper heroLifesWrapper;

    private int numberOfLifes = 500;

    private class TestHeroLifesWrapper extends HeroLifesWrapper {
        public TestHeroLifesWrapper(final Vector2D position, final Vector2D dimensions){
            super(position, dimensions, 1, numberOfLifes);
        }

        public double testNockbackSpeedRecovery(){
            return nockbackRecoverySpeed;
        }

        public double testStartingNockbackSpeed(){
            return startingNockbackSpeed;
        }

        public double testNockbackSpeed(){
            return nockbackSpeed;
        }

        public void nockback(boolean dir){
            super.nockback(dir);
        }
    }

    @Before
    public void setup(){

        Vector2D positionHero = new Vector2D(50,0);
        Vector2D sizeHero = new Vector2D(1,2);
        heroLifesWrapper = new TestHeroLifesWrapper(positionHero,sizeHero);
    }

    @Test
    public void testImmunity(){

        float deltaT = 1;

        heroLifesWrapper.setImmunityTime(4);

        assertFalse(heroLifesWrapper.isImmune());

        heroLifesWrapper.takeLife();

        assertTrue(heroLifesWrapper.isImmune());

        heroLifesWrapper.update(deltaT); //t-=1

        assertTrue(heroLifesWrapper.isImmune());

        heroLifesWrapper.update(deltaT); //t-=1

        assertTrue(heroLifesWrapper.isImmune());

        heroLifesWrapper.update(deltaT); //t-=1

        assertTrue(heroLifesWrapper.isImmune());

        heroLifesWrapper.update(deltaT); //t-=1

        assertFalse(heroLifesWrapper.isImmune());

    }


    @Test
    public void testNockback(){

        float numberOfUpdates = 2.0f;

        float deltaT = (float)(heroLifesWrapper.testStartingNockbackSpeed()
                       /heroLifesWrapper.testNockbackSpeedRecovery())/numberOfUpdates;

        heroLifesWrapper.setImmunityTime(deltaT/2.0f);

        for(int i = 0; i < numberOfLifes; i ++) {

            heroLifesWrapper.takeLife();

            heroLifesWrapper.update(deltaT);

            assertTrue(heroLifesWrapper.isBeingNockedbacked());

            heroLifesWrapper.update(deltaT);

            assertFalse(heroLifesWrapper.isBeingNockedbacked());
        }

        assertTrue(heroLifesWrapper.getNumberOfLifes()==0);

        assertTrue(heroLifesWrapper.takeLife()==0);
    }


    @Test
    public void testNockbackDir(){

        heroLifesWrapper.nockback(true);

        assertTrue(heroLifesWrapper.testNockbackSpeed()<0);

        heroLifesWrapper.nockback(false);

        assertTrue(heroLifesWrapper.testNockbackSpeed()>0);
    }

}
