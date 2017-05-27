package tests.Objects;


import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLight;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class HeroLightTest {

    HeroLight heroLight;
    Hero heroMock;
    double heroYDim = 10;
    double heroXDim = 3;
    double heroXPos = 5;
    double heroYPos = 0;

    @Before
    public void setup(){

        heroMock = createMock(Hero.class);

        expect(heroMock.getXDim()).andReturn(heroXDim).times(1);
        expect(heroMock.getYDim()).andReturn(heroYDim).times(2);
        expect(heroMock.getXPos()).andReturn(heroXPos).times(1);
        expect(heroMock.getYPos()).andReturn(heroYPos).times(1);

        replay(heroMock);

        heroLight = new HeroLight(heroMock);

        verify(heroMock);
        reset(heroMock);
    }

    @Test
    public void testUpdateLight(){

        expect(heroMock.getXDim()).andReturn(heroXDim).times(1);
        expect(heroMock.getYDim()).andReturn(heroYDim).times(1);
        expect(heroMock.getXPos()).andReturn(heroXPos).times(1);
        expect(heroMock.getYPos()).andReturn(heroYPos).times(1);

        replay(heroMock);

        heroLight.updateLight(0);

        verify(heroMock);
        reset(heroMock);

        assertTrue(heroLight.getLightInfo().getXPos()==(heroXPos+(heroXDim/2.0)-(heroLight.getLightInfo().getRadious()/2.0)));
        assertTrue(heroLight.getLightInfo().getYPos()==(heroYPos+(heroYDim/2.0)-(heroLight.getLightInfo().getRadious()/2.0)));

        heroXPos++;
        heroYPos++;

        expect(heroMock.getXDim()).andReturn(heroXDim).times(1);
        expect(heroMock.getYDim()).andReturn(heroYDim).times(1);
        expect(heroMock.getXPos()).andReturn(heroXPos).times(1);
        expect(heroMock.getYPos()).andReturn(heroYPos).times(1);

        replay(heroMock);

        heroLight.updateLight(0);

        verify(heroMock);


        assertTrue(heroLight.getLightInfo().getXPos()==(heroXPos+(heroXDim/2.0)-(heroLight.getLightInfo().getRadious()/2.0)));
        assertTrue(heroLight.getLightInfo().getYPos()==(heroYPos+(heroYDim/2.0)-(heroLight.getLightInfo().getRadious()/2.0)));

    }


}
