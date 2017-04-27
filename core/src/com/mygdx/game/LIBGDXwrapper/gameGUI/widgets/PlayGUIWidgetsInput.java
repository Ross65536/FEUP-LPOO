package com.mygdx.game.LIBGDXwrapper.gameGUI.widgets;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.LIBGDXwrapper.DeviceConstants;
import com.mygdx.game.LIBGDXwrapper.MyGame;
import com.mygdx.game.LIBGDXwrapper.gameGUI.MenuManager;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.table;

public class PlayGUIWidgetsInput{

    static public void loadSettingsButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });
    }

    static public void loadBackToMenuButton(Button button, final MenuManager menuManager){
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                menuManager.setMenu(MenuManager.GameMenus.MainMenu);
            }
        });
    }

    static float xPos = -1;
    static float yPos = -1;

    static public void loadSlideFunction(final Table table, final MenuManager menuManager, final OrthographicCamera camera){
        table.addListener(
            new ClickListener(){

                private float movementMade = 0;

                private float currentXCoor;

                @Override
                public void touchUp (InputEvent event, float x, float y, int pointer, int button){

                    final double maxMov = DeviceConstants.MENU_VIEWPORT/4;
                    double maxTime = 2;
                    final double maxAccelaration = 4000;


                    double movement = (movementMade % DeviceConstants.MENU_VIEWPORT);

                    System.out.println(movement);

                    if(Math.abs(movement) > (DeviceConstants.MENU_VIEWPORT/4)){

                        movement = -DeviceConstants.MENU_VIEWPORT + movement;

                    }else {
                        int numAux = (int) (movement / (DeviceConstants.MENU_VIEWPORT / 8));

                        if(numAux == 1){
                            numAux*=-1;
                        }else
                            numAux=1;

                        movement = (movement % (DeviceConstants.MENU_VIEWPORT / 8)) * numAux;
                    }

                    if(movement > maxMov){
                        movement = 2*maxMov - movement;
                    }else
                        movement*=1;

                    final double startingVelocity = -Math.sqrt(0-(2*maxAccelaration*movement));

                    final double finalMovement = movement;

                    table.addAction(new Action() {
                        private double velocity = startingVelocity;
                        private double accelaration = maxAccelaration;
                        private double movementLeft = finalMovement;

                        @Override
                        public boolean act(float delta) {
                            double prevVel = velocity;
                            velocity = velocity + accelaration * delta;
                            if(prevVel<0 &&  velocity>0 || prevVel>0 &&  velocity<0 )
                                return true;
                            double movementInterval = velocity * delta;
                            movementLeft -= movementInterval;
                            /*
                            if(movementLeft >= 0){
                                float remainder = (float)Math.abs(movementLeft+movementInterval);
                                camera.translate(remainder,0);
                                movementMade+=remainder;
                                return true;
                            }*/
                            movementMade-=movementInterval;
                            camera.translate(-(float)movementInterval, 0);
                            return false;
                        }
                    });

                }


                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer){
                    x = getScreenX(x);
                    float diffMovement = currentXCoor - x;
                    currentXCoor = x;
                    camera.translate(diffMovement,0);
                    movementMade += diffMovement;
                }

                @Override
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button){
                    currentXCoor = getScreenX(x);
                    table.clearActions();
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

