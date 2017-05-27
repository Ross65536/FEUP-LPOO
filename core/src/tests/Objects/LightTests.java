package tests.Objects;

import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Entity;
import com.mygdx.game.gameLogic.Characters.HeroLifesWrapper;
import com.mygdx.game.gameLogic.Characters.Light;

import org.junit.Before;
import org.junit.Test;

import tests.Characters.HeroLifesWrapperTests;

import static org.junit.Assert.*;


public class LightTests {

    TestLight light;
    double radious = 5;

    private class TestLight extends Light {
        public TestLight(Vector2D position, double radious, boolean oscilating , double oscilationLengthFraction){
            super(position, radious, oscilating, oscilationLengthFraction);
        }

        public void testSetOscilating(boolean isO){
            oscilating = isO;
        }

    }

    @Before
    public void setup(){

        Vector2D position = new Vector2D(50,50);
        boolean oscilating = true;
        double fraqOsc = 0.1;
        light = new TestLight(position, radious, oscilating, fraqOsc);
    }


    @Test
    public void testRadiosDisappearance(){

        light.testSetOscilating(false);

        double timeAlight = 10;

        light.setVelocityDisappearance(1.0/timeAlight); //0.1 ->10s to disappear

        light.updateRadious(5);

        assertTrue(light.getRadiousPercentage() == 0.5);

        light.updateRadious(5);

        assertTrue(light.getRadiousPercentage() == 0.0);

        light.resetRadious();

        assertTrue(light.getRadiousPercentage() == 1.0);

        light.updateRadious(10);

        assertTrue(light.getRadious() == 0.0);

        light.resetRadious();

        assertTrue(light.getRadious() == radious);

        light.setInnerRadious(10.0f);

        assertTrue(light.getRadious() == 10.0f);

    }

}
