package com.mygdx.game.LIBGDXwrapper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;


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

        gameScreen = new GameScreen(this, gameSettings);//gameSettings não devia de ser parametro
        menuScreen = new MenuScreen(this);

        setScreen(menuScreen);

	}


	private void startGameTest()
	{
        TestLevel testLevel = new TestLevel();
        gameScreen.LoadLevel(testLevel);

	}

    public void SwicthToMenuScreen(MenuInstr instruction){
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
        Gdx.input.setInputProcessor(new InputAdapter(){});
        menuScreen.nullifyMenu();
        switch (instruction){
            case RESUME:

                break;
            case RESTART:
                startGameTest();
                break;
            case START:
                startGameTest();
                break;
        }
        setScreen(gameScreen);
    }


    public GameSettings getGameSettings(){
        return this.gameSettings;
    }
}
