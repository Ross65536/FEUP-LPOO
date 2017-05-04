package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.CommonConstants;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameGUI.GameMenus;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;
import static com.mygdx.game.CommonConstants.NUMBER_OF_GAMEMODES;

public class PlayGUIWidgetsInput extends WidgetsInput{

    public void loadSettingsButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    public void loadBackToMenuButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(GameMenus.MainMenu);
            }
        });
    }

    public void loadSlideFunction(final Table table, final MenuManager menuManager, final OrthographicCamera camera){

        table.addListener(
            new ClickListener(){

                private float movementMade = 0;

                private float xSlided = 0;

                private float currentXCoor;

                private Action action;

                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button){

                    if(movementMade == 0)
                        return;

                    double maxAcceleration = 5000;
                    double sign = 1;

                    double movement = (movementMade % DeviceConstants.MENU_VIEWPORT);


                    if(Math.abs(movement) > (DeviceConstants.MENU_VIEWPORT/4)){

                        if(movement<0) {
                            movement = (DeviceConstants.MENU_VIEWPORT + movement);
                            maxAcceleration*=-1;
                            sign*=-1;
                        }
                        else {
                            movement = movement - DeviceConstants.MENU_VIEWPORT;
                        }

                        movementMade = (float)movement;
                    }else {

                        movement = (movement % (DeviceConstants.MENU_VIEWPORT / 4));

                        if(movement>0) {
                            sign *= -1;
                            maxAcceleration*=-1;
                        }
                    }

                    double startingVelocity = -sign*Math.sqrt(0-(2*maxAcceleration*movement))*1.01;

                    table.addAction(action = getTableAction(startingVelocity, maxAcceleration));

                }

                private Action getTableAction(final double initialVelocity,final double acceleration){
                    return new Action() {
                        private double velocity = initialVelocity;
                        @Override
                        public boolean act(float delta) {
                            double prevVel = velocity;
                            velocity = velocity + acceleration * delta;

                            if((prevVel<0 &&  velocity>=0) || (prevVel>0 &&  velocity<=0)){

                                camera.translate(-(float)movementMade, 0);
                                xSlided-=movementMade;
                                movementMade = 0;
                                return true;
                            }

                            double movementInterval = velocity * delta;
                            movementMade-=movementInterval;
                            xSlided-=movementInterval;
                            camera.translate(-(float)movementInterval, 0);
                            return false;
                        }
                    };
                }

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer){
                    x = getScreenX(x);
                    float diffMovement = currentXCoor - x;

                    //check if out of bounds
                    xSlided+=diffMovement;
                    if((xSlided<0) || (xSlided>=(CommonConstants.NUMBER_OF_GAMEMODES-1)*DeviceConstants.MENU_VIEWPORT)) {
                        xSlided-=diffMovement;
                        return;
                    }

                    currentXCoor = x;
                    camera.translate(diffMovement,0);
                    movementMade += diffMovement;
                }

                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                    //if(this.isPressed())
                    //    return false;
                    currentXCoor = getScreenX(x);
                    table.removeAction(action);
                    return true;
                }

                private float getScreenX(float x){
                    Vector3 vector3 = camera.project(new Vector3(x,0,0),0,0,DeviceConstants.MENU_VIEWPORT,(float)(DeviceConstants.MENU_VIEWPORT*DeviceConstants.SCREEN_RATIO));
                    return vector3.x;
                }
            }
        );
    }

}

