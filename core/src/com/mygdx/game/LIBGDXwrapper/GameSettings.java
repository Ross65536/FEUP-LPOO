package com.mygdx.game.LIBGDXwrapper;

import com.mygdx.game.gameLogic.GameWorld;

public class GameSettings implements Settings {
    private boolean isSensorControlled;
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
