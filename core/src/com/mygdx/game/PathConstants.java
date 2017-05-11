package com.mygdx.game;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import java.util.Map;
import java.util.TreeMap;

public final class PathConstants {
    //asset names
    public static final String HERO_IMAGE_PATH = "person.png";
    public static final String ENEMY_GROUND_IMAGE_PATH = "personRed.png";
    public static final String PLATFORM_IMAGE_PATH = "platform.png";
    public static final String LIGHT_IMAGE_PATH = "light.png";


    //associations between names and assetmanager types
    public static  Map<String, Object> mapPathToType; //associates paths to object types for AssetManager
    static {

        PathConstants.mapPathToType = new TreeMap<String, Object>();
        PathConstants.mapPathToType.put(PathConstants.HERO_IMAGE_PATH, Texture.class);
        PathConstants.mapPathToType.put(PathConstants.ENEMY_GROUND_IMAGE_PATH, Texture.class);
        PathConstants.mapPathToType.put(PathConstants.PLATFORM_IMAGE_PATH, Texture.class);
        PathConstants.mapPathToType.put(PathConstants.LIGHT_IMAGE_PATH, Texture.class);
    }
}
