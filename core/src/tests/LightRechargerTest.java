package tests;

import com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild;
import com.mygdx.game.Vector2D;
import com.mygdx.game.gameLogic.Characters.Hero;
import com.mygdx.game.gameLogic.Characters.Light;
import com.mygdx.game.gameLogic.Characters.Platform;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.LightRecharger;
import com.mygdx.game.gameLogic.LogicWorlds.WorldFeatures.Platforms;

import org.junit.Before;
import org.junit.Test;

import java.util.TreeMap;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;


public class LightRechargerTest {

    private int NUM_ITEM_GENERATION_TESTS = 65;
    TestLightRecharger lightRecharger;
    Hero heroMock;
    Light heroLightMock;
    Platforms platforms;
    Vector2D cameraSizes;
    Vector2D worldDimensions;


    private class TestLightRecharger extends LightRecharger{
        public TestLightRecharger(Light heroLight, Hero hero, TreeMap<Double, TreeMap<Double,Platform>> allPlatforms,double cameraDimY){
            this.heroInfo = hero;
            this.allPlatforms = allPlatforms;
            this.cameraHeight = cameraDimY;
            this.heroLight = heroLight;
        }
        public void testGenerateItem(){
            super.generateItem();
        }

        public Platform getPlatformWhereItemIs(){
            return platWhereRechargerIs;
        }

        public double testDist(Platform platform){
            return super.dist(platform);
        }

        public double calculateMinDistance(){
            return super.calculateMinDistance();
        }

        public void setHeroPosition(Vector2D position, Vector2D dims){
            this.heroInfo = new Hero(position, dims, 1);
        }
    }


    @Before
    public void setup(){

        heroLightMock = createMock(Light.class);

        heroMock = createMock(Hero.class);

        cameraSizes = new Vector2D(100, 0.5*100);

        worldDimensions = new Vector2D(1000,cameraSizes.y*10);

        expect(heroMock.getYDim()).andReturn(cameraSizes.y/ PlatLevelBuild.HERO_HEIGHT_BY_SCREEN_HEIGHT).times(2);

        expect(heroMock.getXPos()).andReturn(cameraSizes.x*2).times(2);

        expect(heroMock.getYPos()).andReturn(cameraSizes.y*2).times(3);

        replay(heroMock);

        platforms = new Platforms(heroMock, worldDimensions, cameraSizes);

        lightRecharger = new TestLightRecharger(heroLightMock, heroMock, platforms.getAllPlatforms(), cameraSizes.y);

        reset(heroMock);
    }


    @Test
    public void testItemGeneration(){

        expect(heroMock.getYDim()).andReturn(cameraSizes.y/ PlatLevelBuild.HERO_HEIGHT_BY_SCREEN_HEIGHT).anyTimes();

        expect(heroMock.getXPos()).andReturn(cameraSizes.x*2).anyTimes();

        expect(heroMock.getYPos()).andReturn(cameraSizes.y*2).anyTimes();

        replay(heroMock);

        for(int i = 0; i < NUM_ITEM_GENERATION_TESTS; i++){

            lightRecharger.testGenerateItem();

            Platform plat = lightRecharger.getPlatformWhereItemIs();
            assertTrue(lightRecharger.testDist(plat)>=lightRecharger.calculateMinDistance());

            lightRecharger.increaseLevel();
        }


        assertTrue(lightRecharger.totalRechargersCaught()==0);
        verify(heroMock);

    }


    @Test
    public void checkItemFound(){

        heroLightMock.resetRadious();
        expectLastCall().once();

        replay(heroLightMock);

        lightRecharger.setHeroPosition(new Vector2D(cameraSizes.x*2,cameraSizes.y*2), new Vector2D(1,1));

        lightRecharger.testGenerateItem();

        lightRecharger.updateRechargerItem(0f);

        assertFalse(lightRecharger.wasRechargerCaught());

        lightRecharger.setHeroPosition(
                new Vector2D(lightRecharger.getItemInfo().getXPos()
                ,lightRecharger.getItemInfo().getYPos())
                , new Vector2D(1,1)
        );

        lightRecharger.updateRechargerItem(0f);

        assertTrue(lightRecharger.wasRechargerCaught());

        assertFalse(lightRecharger.wasRechargerCaught());

        verify(heroLightMock);
    }

}
