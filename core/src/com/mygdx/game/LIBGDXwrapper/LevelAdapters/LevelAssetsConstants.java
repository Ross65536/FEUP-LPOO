package com.mygdx.game.LIBGDXwrapper.LevelAdapters;

import com.badlogic.gdx.graphics.Texture;

import java.util.Map;
import java.util.TreeMap;

/**
 * Used to store asset paths
 */
public abstract class LevelAssetsConstants {
    public static final String heroImagePath = "person.png";
    public static final String gremlinImagePath = "personRed.png";
    public static final Map<String, Object> mapPathToType; //associates paths to object types for AssetManager

    static {
        mapPathToType = new TreeMap<String, Object>();
        mapPathToType.put(heroImagePath, Texture.class);
        mapPathToType.put(gremlinImagePath, Texture.class);
    }
}
