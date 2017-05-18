package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.Vector2D;

import java.util.HashMap;

public class HUD extends Stage{

    private MyGame game;

    private Skin skin;

    private Table table;

    private HashMap<String, Object> elements;

    private float viewportWidth;

    private float viewportHeight;

    public HUD(Vector2D cameraDim, MyGame game){
        skin = new Skin();
        table = new Table(skin);
        table.setFillParent(true);

        table.setTouchable(Touchable.childrenOnly);

        this.addActor(table);

        elements = new HashMap<String, Object>();
        this.game = game;
        viewportWidth = (float)cameraDim.x;
        viewportHeight = (float)cameraDim.y;

        this.setViewport(
                new StretchViewport(
                        (int)viewportWidth
                        ,(int)viewportHeight
                )
        );

        this.getViewport().update(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),true);
        ((OrthographicCamera)this.getCamera()).setToOrtho(false, (float) cameraDim.x, (float) cameraDim.y); //camera has maximum world height
        //System.out
        table.setDebug(true);
        loadWidgets();
    }

    private void loadWidgets(){
        elements.put("pauseButton", WidgetsGeneric.getButton(skin,"upPauseButton.png", "downPauseButton.png"));

        table.add(((Button)elements.get("pauseButton")))
                .grow()
                .center()
                .right()
                .top()
                .size(viewportHeight/10.0f)
                .padRight(viewportHeight/20.0f).padTop(viewportHeight/20.0f);
        table.row();

        loadInputlisteners();
    }

    private void loadInputlisteners(){
        ((Button)elements.get("pauseButton")).addListener(new ChangeListener() {


            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.SwicthToMenuScreen(MyGame.MenuInstr.PAUSE);

            }
        });
    }


    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        System.out.println("\ntouchDown");
        if(((Button)elements.get("pauseButton")).isPressed())
            return true;
        System.out.println("touchDown");
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);

        System.out.println("\ntouchUp");
        if(((Button)elements.get("pauseButton")).isOver())
            return true;
        System.out.println("touchUp");
        return false;
    }

    @Override
    public void dispose(){
        this.dispose();
        skin.dispose();
    }

}
