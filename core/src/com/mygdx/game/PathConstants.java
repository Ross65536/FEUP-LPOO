package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public final class PathConstants {


    //asset names
    private static final String SPRITE_PATH = "Sprites/";
    public static final String ENEMY_GROUND_IMAGE_PATH = SPRITE_PATH + "gobby_moveR_strip6.png";
    public static final String ENEMY_FLYING_IMAGE_PATH = SPRITE_PATH + "flyingSpritesPixelated.png";
    public static final String PLATFORM_IMAGE_PATH = SPRITE_PATH + "platform2.png";
    public static final String LIGHT_IMAGE_PATH = "light.png";

    //hero
    public static final String HERO_WALKING_IMAGE_PATH = SPRITE_PATH + "heroWalking.png";
    public static final String HERO_JUMPING_IMAGE_PATH = SPRITE_PATH + "heroJumping.png";
    public static final double HERO_WORLD_EDGE_LEEWAY = 0.15; // time xDim of hero to know how much alpha space to cut
    public static final int HERO_WALKING_FRAME_COLS = 4;
    public static final int HERO_WALKING_FRAME_ROWS = 1;
    public static final float HERO_FRAME_TIME = 1.0f / HERO_WALKING_FRAME_COLS / 2;

    //background
    public static final String BACKGROUND_IMAGE = SPRITE_PATH + "Background3.png";
    public static final double BACKGROUND_ASPECT_RATIO = 1.0;
    public static final double BACKGROUND_PORTION_OF_CAMERA_Y = 0.5;



    //assets for each gaeme mode
    public static final String[] discLevelAssetNames = {HERO_WALKING_IMAGE_PATH, ENEMY_GROUND_IMAGE_PATH, LIGHT_IMAGE_PATH, ENEMY_FLYING_IMAGE_PATH, HERO_JUMPING_IMAGE_PATH, BACKGROUND_IMAGE};
    public static final String[] platformTestLevelAssetNames = {HERO_WALKING_IMAGE_PATH, HERO_JUMPING_IMAGE_PATH, ENEMY_GROUND_IMAGE_PATH, PLATFORM_IMAGE_PATH, LIGHT_IMAGE_PATH, BACKGROUND_IMAGE, ENEMY_FLYING_IMAGE_PATH};

    //global arrays that contain information about each enemy, each index is essentially the type of enemy (ground, flying, etc)
    public static final int ENEMY_GROUND_ARRAY_INDEX;
    public static final int ENEMY_FLYING_ARRAY_INDEX;
    public static final int ENEMY_ARRAY_SIZE;
    static
    {
        int i=0;
        ENEMY_GROUND_ARRAY_INDEX = i; i++;
        ENEMY_FLYING_ARRAY_INDEX = i; i++;

        ENEMY_ARRAY_SIZE = i;
    }

    //associations between names and assetmanager types
    public static final  Map<String, Object> mapPathToType; //associates paths to object types for AssetManager
    static {

        mapPathToType = new TreeMap<>();
        mapPathToType.put(HERO_WALKING_IMAGE_PATH, Texture.class);
        mapPathToType.put(HERO_JUMPING_IMAGE_PATH, Texture.class);
        mapPathToType.put(ENEMY_GROUND_IMAGE_PATH, Texture.class);
        mapPathToType.put(ENEMY_FLYING_IMAGE_PATH, Texture.class);
        mapPathToType.put(BACKGROUND_IMAGE, Texture.class);
        mapPathToType.put(PLATFORM_IMAGE_PATH, Texture.class);
        mapPathToType.put(LIGHT_IMAGE_PATH, Texture.class);
    }

    public static final List<Integer> enemyNumAnimationFrames; //assuming flat images, number of frames for each enemy animation
    static
    {
        enemyNumAnimationFrames = new ArrayList<>(2);
        enemyNumAnimationFrames.add(ENEMY_GROUND_ARRAY_INDEX, 6);
        enemyNumAnimationFrames.add(ENEMY_FLYING_ARRAY_INDEX, 4);
    }

    public static final List<Float> enemyFrameTimes; // Associates each enemy index to a animation frame time
    static
    {
        enemyFrameTimes = new ArrayList<>();
        enemyFrameTimes.add(ENEMY_GROUND_ARRAY_INDEX, 1.0f/6 / 1.5f);
        enemyFrameTimes.add(ENEMY_FLYING_ARRAY_INDEX, 1.0f/4 / 1.5f);
    }

    public static final List<String> enemyIndexToTexture; //to each index associates an image path
    static
    {
        enemyIndexToTexture = new ArrayList<>();
        enemyIndexToTexture.add(ENEMY_GROUND_ARRAY_INDEX, ENEMY_GROUND_IMAGE_PATH);
        enemyIndexToTexture.add(ENEMY_FLYING_ARRAY_INDEX, ENEMY_FLYING_IMAGE_PATH);
    }

}
