package com.mygdx.game.LIBGDXwrapper.gameAdapter;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.Vector2D;

public interface IGameWorldAdapter {
    void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera);
    Vector2D getCameraSetup ();
    void updateWorld(float delaT);
    void updateScreen(float deltaT);
    void setCamera(OrthographicCamera camera);
    IGameWorldHeroInputs getLogicWorldInputs();
    void resize(int width, int height);
    void dispose();
}
