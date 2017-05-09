package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilder;


public class MyGame extends Game {
    private GameScreen gameScreen;


    private MenuScreen menuScreen;

    private GameSettings gameSettings;

    public static enum GameInstr{RESUME, RESTART, START};
    public static enum MenuInstr{PAUSE, EXIT};


    @Override
	public void create ()
    {
        gameSettings = new GameSettings();
        gameSettings.setApplicationPlatform(Gdx.app.getType());

        gameScreen = new GameScreen(this, gameSettings);//gameSettings não devia de ser parametro
        menuScreen = new MenuScreen(this);

        setScreen(menuScreen);
	}

	private void startGameTest()
	{
        gameScreen.LoadLevel(LevelBuilder.createTestLevel());
	}

    private void startPlatGameTest()
    {
        gameScreen.LoadLevel(LevelBuilder.createPlatformTestLevel());
    }

    public void SwicthToMenuScreen(MenuInstr instruction)
    {
        switch (instruction){
            case PAUSE:
                menuScreen.pauseGame();
                break;
            case EXIT:
                gameScreen.nullifyLevel();
                menuScreen.backToMenu();
                break;
        }
        setScreen(menuScreen);
    }


    public void SwicthToGameScreen(GameInstr instruction){
        gameScreen.registerInputHandler();
        switch (instruction){
            case RESUME:

                break;
            case RESTART:
                startGameTest();
                break;
            case START:
                startPlatGameTest();
                //startGameTest();
                break;
        }

        GameAssetHandler.getGameAssetHandler().finishLoading(); //finish loading textures here

        setScreen(gameScreen);
    }


    public GameSettings getGameSettings(){
        return this.gameSettings;
    }
}
