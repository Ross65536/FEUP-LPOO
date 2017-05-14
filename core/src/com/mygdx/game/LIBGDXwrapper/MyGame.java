package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds.LevelBuilder;

import static com.mygdx.game.LIBGDXwrapper.gameAdapter.LevelBuilds.LevelBuilder.createPlatformTestLevel;


public class MyGame extends Game {
    private GameScreen gameScreen;


    private MenuScreen menuScreen;

    private GameSettings gameSettings;

    public static enum GameInstr{RESUME, RESTART, START_GAME_MODE1, START_GAME_MODE2};
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
        gameScreen.LoadLevel(createPlatformTestLevel());
    }

    public void SwicthToMenuScreen(MenuInstr instruction)
    {
        switch (instruction){
            case PAUSE:
                menuScreen.pauseGame();
                break;
            case EXIT:
                gameScreen.nullifyLevel(); //very important
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
            case START_GAME_MODE1:
                startGameTest();
                break;
            case START_GAME_MODE2:
                startPlatGameTest();
                break;

        }

        GameAssetHandler.getGameAssetHandler().finishLoading(); //finish loading textures here

        setScreen(gameScreen);
    }


    public GameSettings getGameSettings(){
        return this.gameSettings;
    }
}
