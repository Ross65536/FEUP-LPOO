package com.mygdx.game.LIBGDXwrapper.gameAdapter;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.gameLogic.Characters.CharacterInfo;
import com.mygdx.game.gameLogic.LogicWorlds.IGameWorldHeroInputs;
import com.mygdx.game.gameLogic.Vector2D;

public interface IGameWorldAdapter {
    void updateCameraPos(CharacterInfo hero, OrthographicCamera gameCamera);
    Vector2D getCameraSetup ();
    void update(float deltaT, OrthographicCamera gameCamera);
    IGameWorldHeroInputs getLogicWorldInputs();
}
