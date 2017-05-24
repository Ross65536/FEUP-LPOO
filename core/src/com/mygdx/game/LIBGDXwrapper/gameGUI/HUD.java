package com.mygdx.game.LIBGDXwrapper.gameGUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameAdapter.GameAssetHandler;
import com.mygdx.game.LIBGDXwrapper.gameGUI.widgets.WidgetsGeneric;
import com.mygdx.game.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;

public class HUD extends Stage{

    private MyGame game;

    private Skin skin;

    private Table table;

    private HashMap<String, Object> elements;

    private float viewportWidth;

    private float viewportHeight;

    private ArrayList<Image> hearts = null;
    private int numberOfHearts;

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

        this.getViewport().setCamera(this.getCamera());
        table.setRound(false);
        table.setDebug(true);
        loadWidgets();
    }

    private void loadWidgets(){

        Button.ButtonStyle buttonStyle = GameAssetHandler.getGameAssetHandler().getUISkinAssetHandler().getUIAsset("pauseButton",Button.ButtonStyle.class);


        elements.put("pauseButton", WidgetsGeneric.getButton(skin,buttonStyle));

        table.add(((Button)elements.get("pauseButton")))
                .grow()
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

    public void addLifeHearts(int numberOfHearts){
        table.getCell(((Button)elements.get("pauseButton"))).colspan(numberOfHearts);
        if(hearts == null){
            skin.add("heartEmpty",new Texture(Gdx.files.internal("heart_empty.png")));
            skin.add("heartGlow",new Texture(Gdx.files.internal("heart_glow.png")));
            hearts = new ArrayList<Image>();
            this.numberOfHearts = numberOfHearts;
        }
        table.row().left().size(viewportHeight/10f).padBottom(viewportHeight/20);
        for(int i = 0; i < numberOfHearts; i++){
            if(i==(numberOfHearts-1))
                addLifeHeart().padRight(viewportWidth-viewportHeight/20f-numberOfHearts*viewportHeight/10f);
            else
                if(i==0)
                    addLifeHeart().padLeft(viewportHeight/20f);
                else
                    addLifeHeart();
        }
    }

    private Cell addLifeHeart(){
        Image heartImage = new Image(skin, "heartGlow");
        hearts.add(heartImage);
        return table.add(heartImage);
    }

    public void removeHeart(){
        hearts.get(--this.numberOfHearts).setDrawable(skin,"heartEmpty");
    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        if(((Button)elements.get("pauseButton")).isPressed())
            return true;
        return false;
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        super.touchUp(screenX, screenY, pointer, button);

        if(((Button)elements.get("pauseButton")).isOver())
            return true;
        return false;
    }

    @Override
    public void dispose(){
        this.dispose();
        skin.dispose();
    }

    public MyGame getGame(){
        return game;
    }

}
