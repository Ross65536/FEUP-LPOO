package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.PathConstants;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.Characters.Platform;

import java.util.Collection;

/**
 * Used to manage assets such as sounds, textures.
 * Singleton Pattern
 */
public class GameAssetHandler { //dispose textures when swicth to menu

    static private GameAssetHandler gameAssetHandler = null; //singelton instance
    private AssetManager assetManager;

    private Animation<TextureRegion> heroWalkAnimations;

    public GameAssetHandler()
    {
        assetManager = new AssetManager(new InternalFileHandleResolver());
        heroWalkAnimations = null;
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

        heroWalkAnimations = null;
    }

    public void setupHeroAnimations()
    {
        if (heroWalkAnimations == null)
        {
            finishLoading();
            Texture heroWalking = assetManager.get(PathConstants.HERO_WALKING_IMAGE_PATH);

            TextureRegion[][] tmp = TextureRegion.split(heroWalking,
                    heroWalking.getWidth() / PathConstants.HERO_WALKING_FRAME_COLS,
                    heroWalking.getHeight() / PathConstants.HERO_WALKING_FRAME_ROWS);


            TextureRegion[] walkFrames = new TextureRegion[PathConstants.HERO_WALKING_FRAME_COLS * PathConstants.HERO_WALKING_FRAME_ROWS];
            int index = 0;
            for (int i = 0; i < PathConstants.HERO_WALKING_FRAME_ROWS; i++) {
                for (int j = 0; j < PathConstants.HERO_WALKING_FRAME_COLS; j++) {
                    walkFrames[index++] = tmp[i][j];
                }
            }

            heroWalkAnimations = new Animation<TextureRegion>(PathConstants.HERO_FRAME_TIME, walkFrames);
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
            assetManager.load(path, (java.lang.Class<Object>) PathConstants.mapPathToType.get(path));
        }

    }

    public TextureRegion getHeroTexture(HeroInfo heroInfo)
    {
        TextureRegion tex = null;
        if (heroInfo.isMovingY()) //jumping texture
            tex = heroWalkAnimations.getKeyFrame(0.5f, true);
        else if(heroInfo.isMovingX()) //moving on ground texture
            tex =  heroWalkAnimations.getKeyFrame((float) heroInfo.getAnimationTime(), true);
        else //stationary texture
            tex =  heroWalkAnimations.getKeyFrame(0.0f, true);

        final boolean isMovingRight = heroInfo.isMovingRight();
        if (! isMovingRight) //flip on Y axis if hero moving leftwards
        {
            tex = new TextureRegion(tex);
            tex.flip(true, false);
        }

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
        return assetManager.get(enemyInfo.getAssociatedImagePath());
    }

    public Texture getPlatformTexture(final Platform platform)
    {
        return assetManager.get(platform.getAssociatedImagePath());
    }

    public Texture getLightTexture()
    {
        return assetManager.get(PathConstants.LIGHT_IMAGE_PATH);
    }

    public void finishLoading()
    {
        assetManager.finishLoading();
    }
}
