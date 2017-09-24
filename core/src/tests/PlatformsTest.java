package tests;

import ros.joao.rjtorcher.CommonConsts;
import ros.joao.rjtorcher.LIBGDXwrapper.gameAdapter.LevelBuilds.PlatLevelBuild;
import ros.joao.rjtorcher.Vector2D;
import ros.joao.rjtorcher.gameLogic.Characters.Hero;
import ros.joao.rjtorcher.gameLogic.Characters.Platform;
import ros.joao.rjtorcher.gameLogic.LogicWorlds.WorldFeatures.Platforms;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static ros.joao.rjtorcher.gameLogic.Characters.Platform.fractionOfScreenHeightForPlatform;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.*;

public class PlatformsTest {


    TestPlarforms platforms;
    Hero heroMock;
    Vector2D worldDimensions;
    Vector2D cameraSizes;

    double platformHeight;
    double platformWidth;
    Vector2D paddingXY;
    int frqPad;

    private class TestPlarforms extends Platforms{
        public TestPlarforms(Hero hero,Vector2D worldDimensions,Vector2D cameraSizes){
            this.hero = hero;
            this.worldDimensions = worldDimensions;
            this.cameraHeight = cameraSizes.y;
            this.cameraWidth = cameraSizes.x;
        }
        public void testInitConstants(){
            super.initConstants();
        }
        public void testSetPadingANDFrequecy(Vector2D padding, int freq){
            super.setPadingANDFrequecy(padding,freq);
        }
        public void testCreatePlarforms(){
            super.createPlarforms();
        }
    }

    @Before
    public void setup(){
        heroMock = createMock(Hero.class);

        cameraSizes = new Vector2D(100, 0.5*100);
        worldDimensions = new Vector2D(1000,cameraSizes.y*10);

        platformHeight = fractionOfScreenHeightForPlatform*cameraSizes.y;

        platformWidth = platformHeight*CommonConsts.getCharacterConstants(Platform.class).aspectRatio;

        platforms =  new TestPlarforms(heroMock,worldDimensions,cameraSizes);

        expect(heroMock.getYDim()).andReturn(cameraSizes.y/ PlatLevelBuild.HERO_HEIGHT_BY_SCREEN_HEIGHT).times(2);

        replay(heroMock);

        platforms.testInitConstants();

        paddingXY = new Vector2D((int)heroMock.getYDim()*1.5/platformHeight,0);

        frqPad = 5;

        platforms.testSetPadingANDFrequecy(paddingXY,frqPad);

        verify(heroMock);

        reset(heroMock);
    }

    @Test
    public void checkPlarformsCreation(){

        expect(heroMock.getYDim()).andReturn(cameraSizes.y/ PlatLevelBuild.HERO_HEIGHT_BY_SCREEN_HEIGHT).times(1);

        replay(heroMock);

        platforms.testCreatePlarforms();

        verify(heroMock);

        reset(heroMock);
    }

    @Test
    public void checkPaddingPlatforms(){

        checkPlarformsCreation();

        double coorX,prevX=0;
        boolean isfirst;

        for (Map.Entry<Double,TreeMap<Double,Platform>> yplat: platforms.getAllPlatforms().entrySet()) {
            isfirst = true;
            for (Map.Entry<Double, Platform> xplat : yplat.getValue().entrySet()) {

                coorX = xplat.getKey();
                if(!isfirst){
                    assertTrue(((coorX-prevX)-platformWidth)>=(1-paddingXY.x)*platformWidth);
                }

                prevX = coorX;
                if(isfirst)
                    isfirst = false;
            }
        }

    }


    @Test
    public void checkPlatformsInRange(){
        checkPlarformsCreation();

        expect(heroMock.getXPos()).andReturn(cameraSizes.x*2).times(2);

        expect(heroMock.getYPos()).andReturn(cameraSizes.y*2).times(3);

        replay(heroMock);

        platforms.updatePlatformsInRange();

        verify(heroMock);

        double maxX = -1, minX=cameraSizes.x*2, maxY= -1, minY=cameraSizes.y*2;
        for (Platform plat: platforms.getPlatforms()){ //getPlatforms returns platforms in range
            if(plat.getXPos() < minX){
                minX = plat.getXPos();
            }
            if(plat.getXPos() > maxX){
                maxX = plat.getXPos();
            }
            if(plat.getYPos() < minY){
                minY = plat.getYPos();
            }
            if(plat.getYPos() > maxY){
                maxY = plat.getYPos();
            }
        }
        assertTrue((maxX-minX)<=cameraSizes.x*2);
        assertTrue((maxY-minY)<=cameraSizes.y*2);
    }

    @Test
    public void checkPlatformIntersection(){

        Platform platform = new Platform(new Vector2D(0,0),new Vector2D(2,1));

        expect(heroMock.getXPos()).andReturn(0.0).anyTimes();
        expect(heroMock.getXDim()).andReturn(1.0).anyTimes();
        expect(heroMock.getYPos()).andReturn(Platform.PLATFORM_Y_LEEWAY).anyTimes();
        expect(heroMock.getYDim()).andReturn(1.0).anyTimes();
        expect(heroMock.getPrevPosition()).andReturn(new Vector2D(0,0)).anyTimes();
        expect(heroMock.isFalling()).andReturn(true).anyTimes();



        replay(heroMock);
        assertTrue(platform.checkCollision(heroMock));

        verify(heroMock);
        reset(heroMock);



        expect(heroMock.getXPos()).andReturn(5.0).anyTimes();
        expect(heroMock.getXDim()).andReturn(1.0).anyTimes();
        expect(heroMock.getYPos()).andReturn(0.0).anyTimes();
        expect(heroMock.getYDim()).andReturn(1.0).anyTimes();
        expect(heroMock.getPrevPosition()).andReturn(new Vector2D(5,0)).anyTimes();
        expect(heroMock.isFalling()).andReturn(true).anyTimes();

        replay(heroMock);

        assertFalse(platform.checkCollision(heroMock));

        verify(heroMock);
        reset(heroMock);


        //hero went trough


        expect(heroMock.getXPos()).andReturn(0.0).anyTimes();
        expect(heroMock.getXDim()).andReturn(1.0).anyTimes();
        expect(heroMock.getYPos()).andReturn(Platform.PLATFORM_Y_LEEWAY-1).anyTimes();
        expect(heroMock.getYDim()).andReturn(1.0).anyTimes();
        expect(heroMock.getPrevPosition()).andReturn(new Vector2D(0,2)).anyTimes();
        expect(heroMock.isFalling()).andReturn(true).anyTimes();

        expect(heroMock.isMovingY()).andReturn(false).once();

        replay(heroMock);

        assertTrue(platform.checkCollision(heroMock));

        platforms.checkPlatformCollisions();

        verify(heroMock);

    }



}
