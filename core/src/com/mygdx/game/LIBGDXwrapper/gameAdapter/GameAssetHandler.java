package com.mygdx.game.LIBGDXwrapper.gameAdapter;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.CommonConsts;
import com.mygdx.game.LIBGDXwrapper.PathConstants;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.Characters.EnemyInfo;
import com.mygdx.game.gameLogic.Characters.HeroInfo;
import com.mygdx.game.gameLogic.Characters.Platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Used to manage assets such as sounds, textures.
 * Singleton Pattern
 */
public class GameAssetHandler { //dispose textures when swicth to menu

    static private GameAssetHandler gameAssetHandler = null; //singelton instance
    private AssetManager assetManager;
    private UISkinAssetHandler UIskin;

    private Animation<TextureRegion> heroWalkAnimations;
    private List<Animation<TextureRegion>> enemyAnimations;

    /**
     * constructor
     */
    private GameAssetHandler()
    {
        assetManager = new AssetManager(new InternalFileHandleResolver());
        heroWalkAnimations = null;
        enemyAnimations = new ArrayList<>(CommonConsts.ENEMY_ARRAY_SIZE);

        UIskin = new UISkinAssetHandler(assetManager);
    }

    public UISkinAssetHandler getUISkinAssetHandler(){return UIskin;}

    /**
     * singleton access point
     * @return the class instance
     */
    public static GameAssetHandler getGameAssetHandler()
    {
        if (gameAssetHandler == null) {
            gameAssetHandler = new GameAssetHandler();

        }
        return gameAssetHandler;
    }


    /**
     * Unloads uneeded textures, sounds form memory
     * @param assetPaths
     */
    public void unloadUnneededLevelAssets(final Collection<String> assetPaths)
    {
        final Array<String> currentAssets = assetManager.getAssetNames(); //Array is from LIBGDX
        for (String currPath : currentAssets) //unloads uneeded assets
        {
            if (!assetPaths.contains(currPath) && !currPath.contains(UISkinAssetHandler.UIAssetsPath) && !currPath.contains(".ttf"))
            {
                System.out.println(currPath);
                assetManager.unload(currPath);
            }
        }

        nullifyAssetReferences();
    }

    private void nullifyAssetReferences() {
        heroWalkAnimations = null;
        enemyAnimations.clear();
    }

    /**
     * Sets up the hero's various assets
     */
    public void setupHeroAssets()
    {
        if (heroWalkAnimations == null)
        {
            Texture heroWalking = assetManager.get(PathConstants.HERO_WALKING_IMAGE_PATH);
            final int numCols = PathConstants.HERO_WALKING_FRAME_COLS;
            final int numRows = PathConstants.HERO_WALKING_FRAME_ROWS;

            heroWalkAnimations = getAnimationFromTiles(heroWalking, numCols, numRows, PathConstants.HERO_FRAME_TIME);
        }
    }

    private Animation<TextureRegion> getAnimationFromTiles(Texture tiles, final int numCols, final int numRows, final float frameTime) {
        TextureRegion[][] tmp = TextureRegion.split(tiles,
                tiles.getWidth() / numCols,
                tiles.getHeight() / numRows);


        TextureRegion[] frames = new TextureRegion[numCols * numRows];
        int index = 0;
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        return new Animation<TextureRegion>(frameTime, frames);
    }

    /**
     * sets up the assets for the enemy which correponds to the index
     * @param enemyArrayIndex indicates the enemy class
     */
    public void setupEnemyAnimationsFlat(final int enemyArrayIndex)
    {
        final String enTexPath = PathConstants.enemyIndexToTexture.get(enemyArrayIndex);
        Texture enemyTex = assetManager.get(enTexPath);

        final int numCols = PathConstants.enemyNumAnimationFrames.get(enemyArrayIndex);
        final float animationTime = PathConstants.enemyFrameTimes.get(enemyArrayIndex);

        Animation<TextureRegion> WalkAnimations = getAnimationFromTiles(enemyTex, numCols, 1, animationTime);

        enemyAnimations.add(enemyArrayIndex, WalkAnimations);
    }


    /**
     * Loads assets in the list
     * Doesnt load duplicates if they exist
     * @param assetPaths the asset names
     */
    public void loadLevelAssets (final Collection<String> assetPaths)
    {
        for (final String path : assetPaths) //loads missing assets
        {
            assetManager.load(path, (java.lang.Class<Object>) PathConstants.mapPathToType.get(path));
        }

    }

    /**
     * Returns the appropriate hero texture
     * @param heroInfo object containing information about the hero
     * @return the texture
     */
    public TextureRegion getHeroTexture(HeroInfo heroInfo)
    {
        TextureRegion tex = null;
        if (heroInfo.isMovingY()) //jumping texture
        {
            Texture texture = assetManager.get(PathConstants.HERO_JUMPING_IMAGE_PATH);
            tex =  new TextureRegion(texture);
        }
        else if(heroInfo.isMovingX()) //moving on ground texture
            tex =  heroWalkAnimations.getKeyFrame((float) heroInfo.getAnimationTime(), true);
        else //stationary texture
            tex =  heroWalkAnimations.getKeyFrame(0.0f, true);

        tex= tryFlippingTexture(heroInfo, tex);

        return tex;
    }

    private TextureRegion tryFlippingTexture(final CharacterInfo charInfo, TextureRegion tex) {
        TextureRegion newTex = tex;
        final boolean isMovingRight = charInfo.isMovingRight();
        if (! isMovingRight) //flip on Y axis if hero moving leftwards
        {
            newTex = new TextureRegion(tex);
            newTex.flip(true, false);
        }

        return newTex;
    }

    /**
     * Associates class with textures.
     * Receives a pointer and decides what texture to return.
     * Also handles getting the right image for the animation time, etc.
     *
     * @param enemyInfo
     * @return Texture to be drawn
     */
    public TextureRegion getEnemyTexture(final EnemyInfo enemyInfo)
    {
        final int arrayIndex = enemyInfo.getArrayIndex();
        Animation<TextureRegion> walkAnimation = enemyAnimations.get(arrayIndex);

        TextureRegion enTex =  walkAnimation.getKeyFrame((float) enemyInfo.getAnimationTime(), true);

        enTex = tryFlippingTexture(enemyInfo, enTex);

        return enTex;
    }

    public Texture getPlatformTexture(final Platform platform)
    {
        return assetManager.get(PathConstants.PLATFORM_IMAGE_PATH);
    }

    public Texture getLightTexture()
    {
        return assetManager.get(PathConstants.LIGHT_IMAGE_PATH);
    }

    /**
     * Finishes loading assets, since assets are loaded asynchronously.
     */
    public void finishLoading()
    {
        assetManager.finishLoading();
    }

    public void reloadNightBackground(){
        if(assetManager.containsAsset(PathConstants.NIGHTBACKGROUND_IMAGE))
            assetManager.unload(PathConstants.NIGHTBACKGROUND_IMAGE);
        assetManager.load(PathConstants.NIGHTBACKGROUND_IMAGE, Texture.class);
        assetManager.finishLoading();
    }

    public Texture getNightBackgroundTexture() {
        try{
            assetManager.get(PathConstants.NIGHTBACKGROUND_IMAGE);
        }catch(Exception e){
            assetManager.finishLoading();
        }
        return assetManager.get(PathConstants.NIGHTBACKGROUND_IMAGE);
    }

    /**
     * returns the texture for the background
     * @return
     */
    public Texture getBackgroundTexture() {
        return assetManager.get(PathConstants.BACKGROUND_IMAGE);
    }

    public Texture getBorderTexture() {
        return assetManager.get(PathConstants.BACKGROUND_BORDER);
    }

    public Texture getLightRechargerTexture()
    {
        return assetManager.get(PathConstants.RECHARGER_IMAGE_PATH);
    }

    public void dispose(){
        UIskin.dispose();
        //assetManager.clear();
    }
}
