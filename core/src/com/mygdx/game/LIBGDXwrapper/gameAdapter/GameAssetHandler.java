package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.gameLogic.Characters.EnemyGround;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Used to manage assets such as sounds, textures.
 * Singleton Pattern
 */
public class GameAssetHandler {
    //asset names
    public static final String imagePathHero = "person.png";
    public static final String imagePathEnemyGround = "personRed.png";
    //associations between names and types
    private static final Map<String, Object> mapPathToType; //associates paths to object types for AssetManager
    static {
        mapPathToType = new TreeMap<String, Object>();
        mapPathToType.put(imagePathHero, Texture.class);
        mapPathToType.put(imagePathEnemyGround, Texture.class);
    }

    static private GameAssetHandler gameAssetHandler = null; //singelton instance
    private AssetManager assetManager;

    public GameAssetHandler()
    {
        assetManager = new AssetManager(new InternalFileHandleResolver());
    }

    public static GameAssetHandler getGameAssetHandler()
    {
        if (gameAssetHandler == null)
            gameAssetHandler = new GameAssetHandler();

        return gameAssetHandler;
    }

    public void unloadUnneededLevelAssets(final Collection<String> assetPaths)
    {
        final Array<String> currentAssets = assetManager.getAssetNames(); //Array is from LIBGDX
        for (String currPath : currentAssets) //unloads uneeded assets
        {
            if (! assetPaths.contains(currPath))
            {
                assetManager.unload(currPath);
            }
        }
    }

    /**
     * Doesnt load duplicates if they exist
     * @param assetPaths
     */
    public void loadLevelAssets (final Collection<String> assetPaths)
    {
        for (final String path : assetPaths) //loads missing assets
        {
            assetManager.load(path, (java.lang.Class<Object>) GameAssetHandler.mapPathToType.get(path));
        }

    }

    public Texture getHeroTexture(HeroInfo heroInfo)
    {
        Texture tex = assetManager.get(imagePathHero);
        return tex;
    }

    /**
     * Associates class with textures.
     * Receives a pointer and decides what texture to return.
     * Also handles getting the right image for the animation time, etc.
     *
     * @param enemyInfo
     * @return Texture to be drawn
     */
    public Texture getCharacterTexture (final EnemyInfo enemyInfo)
    {
        if (enemyInfo instanceof EnemyGround)
            return assetManager.get(imagePathEnemyGround);


        return null;
    }

    public void finishLoading()
    {
        assetManager.finishLoading();
    }
}