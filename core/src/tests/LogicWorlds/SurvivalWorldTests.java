package tests.LogicWorlds;


import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Enemy;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.GameDirector.Statistic.StatisticsInput;
import com.mygdx.game.gameLogic.LogicWorlds.SurvivalWorld;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.HeroLight;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.easymock.EasyMock.*;

import java.util.Random;

public class SurvivalWorldTests {
    private static double DELTA_DOUBLE = 0.00001;

    Hero heroMock ;
    HeroLight lightMock ;
    DummyEnemies dummyMock ;
    Vector2D worldDims;
    StatisticsInput statisticsMock ;
    SurvivalWorld survivalWorld;
    Enemy enemyMock;
    Random randomMock;

    private static final double WORLD_X = 100;
    private static final double WORLD_Y = 10;

    class TestSurvival extends SurvivalWorld
    {

        public TestSurvival(Vector2D worldDims, Hero heroMock, DummyEnemies dummyMock, HeroLight lightMock, Random randomMock) {
            super();
            this.worldDimensions=worldDims; this.hero=heroMock; this.dummyEnemies=dummyMock; this.light = lightMock; this.gamePlayable=true; this.random = randomMock;
        }
    }


    @Before
    public void setup()
    {
        heroMock = createMock(Hero.class);
        lightMock = createMock(HeroLight.class);
        dummyMock = createMock(DummyEnemies.class);
        worldDims = new Vector2D(WORLD_X, WORLD_Y);
        statisticsMock = createMock(StatisticsInput.class);
        enemyMock = createMock(Enemy.class);
        randomMock = createMock(Random.class);
        survivalWorld = new TestSurvival(worldDims, heroMock, dummyMock, lightMock, randomMock);
    }



    @Test
    public void baseClassTests()
    {

        float lightPercentage = 0.3f;
        float delta_t = 0.78f;

        expect(lightMock.getRadiousPercentage()).andReturn(lightPercentage).times(2);

        dummyMock.updateEnemieStatistics(delta_t);
        expectLastCall().once();

        expect(dummyMock.getStatsticsInput()).andReturn(statisticsMock);

        statisticsMock.setLightLevel(lightPercentage);
        expectLastCall().once();

        replay(lightMock);
        replay(dummyMock);
        replay(statisticsMock);


        assertTrue(survivalWorld.isGamePlayable());

        assertEquals(survivalWorld.getDangerLevel(), 1 - lightPercentage, DELTA_DOUBLE);

        survivalWorld.updateEnemieStatistics(delta_t);
        verify(dummyMock);
        verify(statisticsMock);
        verify(lightMock);

    }

    @Test
    public void baseClassHeroInputsTests()
    {
        //jump
        final double jumpStrength = 2.34;

        expect(dummyMock.getStatsticsInput()).andReturn(statisticsMock).times(2);

        statisticsMock.registerJump();
        expectLastCall().once();
        heroMock.jump(jumpStrength);
        expectLastCall().once();

        //movement
        final double movement = 3.4;
        statisticsMock.registerMovement();
        expectLastCall().once();
        heroMock.setXMovement(movement);
        expectLastCall().once();

        replay(statisticsMock);
        replay(heroMock);
        replay(dummyMock);

        survivalWorld.heroJump(jumpStrength);
        survivalWorld.moveHeroHorizontal(movement);

        verify(statisticsMock);
        verify(heroMock);
        verify(dummyMock);

    }

    @Test
    public void placeEnemyTest()
    {
        //ground enemy placed on the left
        double heroXPos = 23;
        expect(heroMock.getXPos()).andReturn(heroXPos);
        expect(enemyMock.isFlyingType()).andReturn(false);
        enemyMock.setYPos(0.0);
        expectLastCall();
        expect(randomMock.nextBoolean()).andReturn(true);
        enemyMock.setXPos(anyDouble());
        expectLastCall();
        enemyMock.setMovementDirection(true);
        expectLastCall();

        replay(randomMock);
        replay(heroMock);
        replay(enemyMock);

        survivalWorld.placeEnemy(enemyMock);


        //flying enemy placed on the right
        setup();
        expect(heroMock.getXPos()).andReturn(heroXPos);
        expect(enemyMock.isFlyingType()).andReturn(true);
        enemyMock.setYPos(anyDouble());
        expectLastCall();
        expect(randomMock.nextBoolean()).andReturn(false);
        expect(randomMock.nextDouble()).andReturn(0.3);
        enemyMock.setXPos(anyDouble());
        expectLastCall();
        enemyMock.setMovementDirection(false);
        expectLastCall();

        replay(randomMock);
        replay(heroMock);
        replay(enemyMock);

        survivalWorld.placeEnemy(enemyMock);

        verify(randomMock);
        verify(enemyMock);
        verify(heroMock);

    }

    private static float DELTA_T = 1.2f;
    final static double LIGHT_LEVEL = 0.3f;
    @Test
    public void testOneUpdate()
    {
        dummyMock.updateEnemieStatistics(DELTA_T);
        expectLastCall().times(2);
        expect(lightMock.getRadiousPercentage()).andReturn((float) LIGHT_LEVEL).times(2);
        statisticsMock.setLightLevel((float) LIGHT_LEVEL);
        expectLastCall().times(2);

        expect(dummyMock.getStatsticsInput()).andReturn(statisticsMock).times(2);

        heroMock.update(DELTA_T);
        expectLastCall().times(2);

        //hero should get grounded
        expect(heroMock.isJumping()).andReturn(true).once();
        expect(heroMock.getYPos()).andReturn(-10.0).anyTimes();
        heroMock.stopJump();
        EasyMock.expectLastCall().once();
        heroMock.setYPos(0.0);
        EasyMock.expectLastCall().once();
        //on 2nd pollOrientation hero shouldnt get grounded
        expect(heroMock.isJumping()).andReturn(false).once();


        expect(heroMock.isMoving()).andReturn(true).once();
        lightMock.updateLight(-DELTA_T);
        expectLastCall().once();
        expect(heroMock.isMoving()).andReturn(false).once();
        lightMock.updateLight(DELTA_T);
        expectLastCall().once();

        //1st time pollOrientation should loop on left
        expect(heroMock.getXPos()).andReturn(- WORLD_X / 2).once();
        //2nd time on right
        expect(heroMock.getXPos()).andReturn(WORLD_X * 100).once();
        heroMock.setXPos(anyDouble());
        expectLastCall().times(2);



        dummyMock.tryGenerateEnemy();
        expectLastCall().times(2);
        expect(dummyMock.checkEnemyCollisions()).andReturn(0l).times(2);

        replay(heroMock);
        replay(lightMock);
        replay(dummyMock);
        replay(statisticsMock);

        assertEquals(survivalWorld.getScore(), "0:0:0");

        survivalWorld.update(DELTA_T);
        assertEquals(survivalWorld.getScore(), "0:0:1");

        survivalWorld.update(DELTA_T);
        assertEquals(survivalWorld.getScore(), "0:0:2");

        verify(dummyMock);
        verify(statisticsMock);
        verify(lightMock);
        verify(heroMock);

    }

    @Test
    public void testGameLost()
    {
        dummyMock.updateEnemieStatistics(DELTA_T);
        expectLastCall().once(); //if game is lost, second call to pollOrientation shouldn't got this far in
        expect(lightMock.getRadiousPercentage()).andReturn((float) LIGHT_LEVEL);
        statisticsMock.setLightLevel((float) LIGHT_LEVEL);
        expectLastCall().once();

        expect(dummyMock.getStatsticsInput()).andReturn(statisticsMock);

        heroMock.update(DELTA_T);
        expectLastCall().once();

        expect(heroMock.isJumping()).andReturn(false);
        expect(heroMock.isMoving()).andReturn(false);
        lightMock.updateLight(DELTA_T);
        expectLastCall().once();

        //hero should be looped on the world here, hero.setXPos shouldnt be called
        expect(heroMock.getXPos()).andReturn(WORLD_X / 2);
        dummyMock.tryGenerateEnemy();
        expectLastCall().once();
        expect(dummyMock.checkEnemyCollisions()).andReturn(1l); //there is a collisiosn, and game should be lost

        replay(heroMock);
        replay(lightMock);
        replay(dummyMock);
        replay(statisticsMock);

        survivalWorld.update(DELTA_T);

        survivalWorld.update(DELTA_T); //should return immediatly

        assertFalse(survivalWorld.isGamePlayable());

        survivalWorld.moveHeroHorizontal(1.2); //shouldn't call any methods inside, and therefore shouldt fail the tests

        verify(dummyMock);
        verify(statisticsMock);
        verify(lightMock);
        verify(heroMock);
    }
}
