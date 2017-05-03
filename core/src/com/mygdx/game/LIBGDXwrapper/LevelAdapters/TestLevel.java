package com.mygdx.game.LIBGDXwrapper.LevelAdapters;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.CommonConstants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.GameWorld;
import com.mygdx.game.gameLogic.Characters.Hero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class TestLevel extends GameLevel {
    public static final double HERO_HEIGHT_BY_SCREEN_HEIGHT = 3;
    private static Texture[] texEnemyTiers = new Texture[10];
    public static final Collection<String> levelAssetPaths;
    static { //add assets here
        levelAssetPaths = new ArrayList<String>();
        levelAssetPaths.add(LevelAssetsConstants.heroImagePath );  //hero
        levelAssetPaths.add(LevelAssetsConstants.gremlinImagePath); //tier 0
    }

    public TestLevel()
    {
        super();
        createTestLevel();
    }


    private void createTestLevel() {

        worldXDim = 100;
        worldYDim = DeviceConstants.INVERTED_SCREEN_RATIO * worldXDim / 10;

        final double heroHeight = worldYDim/HERO_HEIGHT_BY_SCREEN_HEIGHT;

        Hero hero = new Hero(worldXDim / 2f, 0, heroHeight * CommonConstants.heroAspectRatio, heroHeight);
        gameLogicWorld = new GameWorld(worldXDim, worldYDim, hero);
    }


    @Override
    public void getLevelAssets(AssetManager gameAssetManager) {
        super.getLevelAssets(gameAssetManager);
        texEnemyTiers[0] = gameAssetManager.get(LevelAssetsConstants.gremlinImagePath);

    }

    private void drawEnemies()
    {
        List<EnemyInfo> enemies = gameLogicWorld.getEnemiesInfo(); //should be unmodifiable
        for (EnemyInfo enemy : enemies)
        {
            final int enemyTier = enemy.getTier();
            Texture enemyTex = texEnemyTiers[enemyTier];

            drawBatch.draw(enemyTex, (float) enemy.getXPos(), (float) enemy.getYPos(), (float) enemy.getXDim(), (float) enemy.getYDim());
        }
    }

    @Override
    public void update(float deltaT, OrthographicCamera gameCamera) {
        gameLogicWorld.update(deltaT); //updates all world characters

        CharacterInfo hero = gameLogicWorld.getHeroInfo();
        updateCameraPos(hero, gameCamera);

        drawBatch.setProjectionMatrix(gameCamera.combined);
        drawBatch.begin();
            drawEnemies();
            super.drawHero();
        drawBatch.end();
    }


}
