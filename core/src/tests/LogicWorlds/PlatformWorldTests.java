package tests.LogicWorlds;

import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.LIBGDXwrapper.PathConstants;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild;
import ros.joao.rjtorcher.Vector2D;
import ros.joao.rjtorcher.gameLogic.Characters.Enemy;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;
import ros.joao.rjtorcher.gameLogic.GameDirector.StageDirector;
import ros.joao.rjtorcher.gameLogic.GameDirector.Statistic.StatisticsInput;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.PlatWorld;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.DummyEnemies;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.HeroLight;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.LightRecharger;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.Platforms;

import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.easymock.EasyMock.anyDouble;
import static org.easymock.EasyMock.createControl;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

public class PlatformWorldTests {


    Hero hero;
    Vector2D worldDimensions;
    Vector2D cameraSizes;
    HeroLight mockLight;
    DummyEnemies mockDummyEnemies;
    LightRecharger mockLightRecharger;
    TestPlarformsWorld platformWorld;
    StatisticsInput statisticsMock;
    Enemy enemyMock;
    Random randomMock;
    Platforms platfromsMock;

    private static final double WORLD_X = 100;
    private static final double WORLD_Y = 10;

    class TestPlarformsWorld extends PlatWorld{
        public TestPlarformsWorld(Hero hero,HeroLight light, LightRecharger lightRecharger, DummyEnemies mockDummyEnemies,Random rndgen,Platforms platformsMock, Vector2D worldDims){
            super.hero = hero;
            super.worldDimensions = worldDims;
            super.light = light;
            super.lightRecharger = lightRecharger;
            super.dummyEnemies = mockDummyEnemies;
            super.gamePlayable = true;
            super.platforms = platformsMock;
            super.random = rndgen;
        }

        public void testCheckHeroAtWorldEdges(){
            super.checkHeroAtWorldEdges();
        }

        public void testCheckScore(){
            super.checkScore();
        }

        public void testCheckLost(){
            super.checkLost();
        }

        public void testCheckHeroJump(){ super.checkHeroJump();}
    }

    @Before
    public void setup()
    {
        mockLight = createMock(HeroLight.class);
        mockLightRecharger = createMock(LightRecharger.class);
        mockDummyEnemies = createMock(DummyEnemies.class);
        statisticsMock = createMock(StatisticsInput.class);
        enemyMock = createMock(Enemy.class);
        randomMock = createMock(Random.class);
        platfromsMock = createMock(Platforms.class);

        cameraSizes = new Vector2D(100, 0.5*100);
        worldDimensions = new Vector2D(1000,cameraSizes.y*10);

        double heroYDim = (cameraSizes.y/PlatLevelBuild.HERO_HEIGHT_BY_SCREEN_HEIGHT);
        double heroXDim = heroYDim * CommonConsts.getCharacterConstants(Hero.class).aspectRatio;

        hero = new Hero(new Vector2D(0,0), new Vector2D(heroXDim, heroYDim), 1);

        platformWorld = new TestPlarformsWorld(hero, mockLight, mockLightRecharger, mockDummyEnemies,randomMock,platfromsMock, worldDimensions);
    }


    @Test
    public void checkHeroWorldEdges(){
        hero.setXPos(-1);
        platformWorld.testCheckHeroAtWorldEdges();
        assertTrue(hero.getXPos()==0.0);

        final double worldXMax = worldDimensions.x - hero.getXDim() * (1.0 - PathConstants.HERO_WORLD_EDGE_LEEWAY);
        hero.setXPos(worldXMax+1);
        platformWorld.testCheckHeroAtWorldEdges();

        assertTrue(hero.getXPos()==worldXMax);
    }

    @Test
    public void checkScoreIncrease(){

        for(int i = 1; i <= 20; i++){

            expect(mockLightRecharger.wasRechargerCaught()).andReturn(true);

            expect(mockLightRecharger.totalRechargersCaught()).andReturn(i);

            replay(mockLightRecharger);

            platformWorld.testCheckScore();

            assertTrue((i + "").equals(platformWorld.getScore())) ;

            verify(mockLightRecharger);

            reset(mockLightRecharger);
        }

    }

    @Test
    public void checkLostLight(){
        expect(mockLight.getRadiousPercentage()).andReturn(0.2f);
        expect(mockDummyEnemies.checkEnemyCollisions()).andReturn((long)0).times(2);
        replay(mockDummyEnemies);

        replay(mockLight);

        platformWorld.testCheckLost();

        assertTrue(platformWorld.isGamePlayable());

        verify(mockLight);
        reset(mockLight);

        expect(mockLight.getRadiousPercentage()).andReturn(0.09f);

        replay(mockLight);

        platformWorld.testCheckLost();

        assertFalse(platformWorld.isGamePlayable());

        verify(mockLight);

        verify(mockDummyEnemies);

    }


    @Test
    public void baseClassHeroInputsTests()
    {
        //jump
        final double jumpStrength = 2.34;

        expect(mockDummyEnemies.getStatsticsInput()).andReturn(statisticsMock).times(2);

        statisticsMock.registerJump();
        expectLastCall().once();
        hero.jump(jumpStrength);

        //movement
        final double movement = 3.4;
        statisticsMock.registerMovement();
        expectLastCall().once();
        hero.setXMovement(movement);

        replay(statisticsMock);
        replay(mockDummyEnemies);

        platformWorld.heroJump(jumpStrength);
        platformWorld.moveHeroHorizontal(movement);

        verify(statisticsMock);
        verify(mockDummyEnemies);

    }


    @Test
    public void placeEnemyTest()
    {
        //ground enemy placed on the left
        double heroXPos = 23;
        hero.setXPos(heroXPos);
        expect(enemyMock.isFlyingType()).andReturn(false);
        enemyMock.setYPos(0.0);
        expectLastCall();
        expect(randomMock.nextBoolean()).andReturn(true);
        enemyMock.setXPos(anyDouble());
        expectLastCall();
        enemyMock.setMovementDirection(true);
        expectLastCall();

        replay(randomMock);
        replay(enemyMock);

        platformWorld.placeEnemy(enemyMock);


        //flying enemy placed on the right
        setup();
        hero.setXPos(heroXPos);
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
        replay(enemyMock);

        platformWorld.placeEnemy(enemyMock);

        verify(randomMock);
        verify(enemyMock);

    }




    @Test
    public void checkPlatformWorldBasicFunctions(){

        cameraSizes = new Vector2D(100, 0.5*100);
        worldDimensions = new Vector2D(1000,cameraSizes.y*10);

        StageDirector stageDirector = createMock(StageDirector.class);
        PlatWorld platWorld = new PlatWorld(cameraSizes, worldDimensions, hero, stageDirector);

        assertTrue(platWorld.getRadiousPercentage()==1);

        assertTrue(platWorld.getLandingYValue() == 0);

        platWorld.getItemLight().updateRadious(100);

        assertTrue(platWorld.getItemLight().getRealRadious() == 0);

        expect(platfromsMock.getLandingYValue()).andReturn(0.0);

        replay(platfromsMock);

        platformWorld.testCheckHeroJump();

        verify(platfromsMock);

        mockDummyEnemies.tryGenerateEnemy();
        expectLastCall().once();

        replay(mockDummyEnemies);

        platformWorld.tryGenerateEnemy();

        verify(mockDummyEnemies);
    }


}
























