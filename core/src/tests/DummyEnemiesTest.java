package tests;


import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.StageDirector;
import com.mygdx.game.gameLogic.GameDirector.Statistic.StatisticsInput;
import com.mygdx.game.gameLogic.LogicWorlds.GameWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;

import static org.easymock.EasyMock.*;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import org.junit.Before;
import org.junit.Test;

public class DummyEnemiesTest {


    TestDummyEnemies dummyEnemies;
    StageDirector statisticsDirector;
    Hero heroMock;
    GameWorld gameWorld;

    private class TestDummyEnemies extends DummyEnemies{
        public TestDummyEnemies(Hero hero, Vector2D worldDims, StageDirector stageDirector, GameWorld gameWorld){
            super(hero, worldDims, stageDirector, gameWorld);
        }

        public void testUpdateEnemies(float deltaT, StatisticsInput statisticsInput){
            super.updateEnemies(deltaT,statisticsInput);
        }
    }

    @Before
    public void setup(){

        heroMock = createMock(Hero.class);

        Vector2D worldDims = new Vector2D(1000,100);
        statisticsDirector = createMock(StageDirector.class);
        gameWorld = createMock(GameWorld.class);
        dummyEnemies = new TestDummyEnemies(heroMock, worldDims, statisticsDirector, gameWorld);

    }


    @Test
    public void testUpdate(){

        expect(heroMock.getXPos()).andReturn(1.0);
        expect(heroMock.getXDim()).andReturn(1.0);
        expect(heroMock.getYDim()).andReturn(3.0);

        replay(heroMock);

        dummyEnemies.createDummyEnemies();

        verify(heroMock);

        reset(heroMock);

        expect(heroMock.getXPos()).andReturn(10000.0).times(2);

        replay(heroMock);

        float deltaT = 1f;

        StatisticsInput statisticsInput = createMock(StatisticsInput.class);
        expect(statisticsDirector.getStatsticsInput()).andReturn(statisticsInput);

        statisticsInput.update(deltaT);
        expectLastCall().once();

        statisticsInput.updateNumberOfGroundEnemies(-2);
        expectLastCall().once();

        statisticsInput.updateNumberOfFlyingEnemies(0);
        expectLastCall().once();

        replay(statisticsDirector);
        replay(statisticsInput);

        dummyEnemies.updateEnemieStatistics(deltaT);

        verify(statisticsDirector);
        verify(statisticsInput);
        verify(heroMock);
    }

}
