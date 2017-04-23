package com.mygdx.game.gameLogic;

public class GameSettings implements Settings {
    private boolean isSensorControlled;
    private GameWorld game;


    float volume;

    GameModeID mode;

    GameSettings(GameModeID mode, float volume, boolean isSensorControlled){
        this.mode = mode;
        this.volume = volume;
        this.isSensorControlled = isSensorControlled;
    }

    GameSettings(){}

    public void setVolume(float volume){
        this.volume = volume;
    }

    public void setMode(GameModeID mode){
        this.mode = mode;
    }

    public void setSensorControll(){
        isSensorControlled = true;
    }

    public boolean isSesorControlled(){
        return isSensorControlled;
    }

}
