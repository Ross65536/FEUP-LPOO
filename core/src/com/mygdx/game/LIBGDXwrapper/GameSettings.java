package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Application;

public class GameSettings implements Settings {
    private boolean isSensorControlled;
    float volume;
    GameModeID mode;

    private Application.ApplicationType applicationPlatform;

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

    public boolean isSensorControlled(){
        return isSensorControlled;
    }

    public void setApplicationPlatform(Application.ApplicationType applicationPlatform) {
        this.applicationPlatform = applicationPlatform;
    }

    public boolean noMotionSensors() {
        return applicationPlatform == Application.ApplicationType.Desktop || applicationPlatform == Application.ApplicationType.Applet || applicationPlatform == Application.ApplicationType.WebGL;
    }
}
