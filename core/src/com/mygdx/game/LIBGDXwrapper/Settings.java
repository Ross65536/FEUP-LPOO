package com.mygdx.game.LIBGDXwrapper;

public interface Settings {
    public static enum GameModeID {SURVIVAL, MUSHROOM, SHOTGUNSEARCH};
    public void setVolume(float volume);
    public void setMode(GameModeID mode);
}
