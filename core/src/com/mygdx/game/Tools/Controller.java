package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Const;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.androidGame;
import javafx.scene.image.ImageView;

public class Controller {
    Viewport viewport;
    Stage stage;
    OrthographicCamera cam;

    Image joyBack,joyKnob;

    private int touchPosX = 0,touchPosY = 0, min_distance = 0;
    private float distance = 0;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(Const.V_WIDTH, Const.V_HEIGHT, cam);
        stage = new Stage(viewport, androidGame.batch);
        Gdx.input.setInputProcessor(stage);

        joyBack = new Image(new Texture(Const.JOYSTICK_BACK_PATH));
        joyKnob = new Image(new Texture(Const.JOYSTICK_KNOB_PATH));

        setImage(joyBack, 200 , 200, 0, 0, .3f);
        setImage(joyKnob, 75, 75,63, 63, .3f);
        joyKnob.setSize(75,75);
        joyKnob.setColor(joyKnob.getColor().r, joyKnob.getColor().r, joyKnob.getColor().r,.3f);


        joyBack.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchPosX =(int) ( x - joyKnob.getWidth()/2 );
                touchPosY =(int) ( y - joyKnob.getHeight()/2 );
                joyKnob.setPosition(touchPosX,touchPosY);
                
                stage.addActor(joyKnob);
                //drawStick();
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                touchPosX =(int) ( x - joyBack.getWidth()/2 );
                touchPosY =(int) ( y - joyBack.getHeight()/2);
                distance = (float) Math.sqrt(Math.pow(touchPosX, 2) + Math.pow(touchPosY, 2));

                //Gdx.app.log("distance =", "" + distance);
                Gdx.app.log("distance", "" + distance);
                if(distance <= joyBack.getWidth()/2)
                    joyKnob.setPosition(x - joyKnob.getWidth()/2,y - joyKnob.getHeight()/2);
                else if(distance > joyBack.getWidth()/2){
                    float actualX = (float) (Math.cos(Math.toRadians(cal_angle(touchPosX,touchPosY)))*100);
                    float actualY = (float) (Math.sin(Math.toRadians(cal_angle(touchPosY,touchPosY))));

                    actualX+= joyBack.getWidth()/2;
                    actualY+= joyBack.getHeight()/2;
                    joyKnob.setPosition(actualX,actualY);

                }else
                joyKnob.remove();


            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                joyKnob.remove();
            }
        });
        stage.addActor(joyBack);
    }

    public void draw() {
        stage.draw();
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private void setImage(Image Img,float width, float height, float posX, float posY, float alpha) {
        Img.setSize(height, width);
        Img.setColor(Img.getColor().r, Img.getColor().r, Img.getColor().r, alpha);
        Img.setPosition(posX, posY);

    }

    private double cal_angle(float x, float y) {
        if(x >= 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x));
        else if(x < 0 && y >= 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x < 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 180;
        else if(x >= 0 && y < 0)
            return Math.toDegrees(Math.atan(y / x)) + 360;
        return 0;
    }

    public void setMinimumDistance(int minDistance){
        min_distance = minDistance;
    }


}



//public class Controller {
//    Viewport viewport;
//    Stage stage;
//    boolean leftPressed, rightPressed;
//    public boolean switchLeft, switchRight;
//    OrthographicCamera cam;
//
//    Table table;
//
//    public Controller(PlayScreen playS) {
//        final PlayScreen playSc = playS;
//        cam = new OrthographicCamera();
//        viewport = new FitViewport(Const.V_WIDTH, Const.V_HEIGHT, cam);
//        stage = new Stage(viewport, androidGame.batch);
//
//        stage.addListener(new InputListener() {
//            @Override
//            public boolean keyDown(InputEvent event, int keycode) {
//                switch (keycode) {
//                    // move player input handling
//                    case Input.Keys.A:
//                        playSc.player[playSc.currentPlayer].moveLeft();
////                        leftPressed = true;
//                        break;
//
//                    case Input.Keys.D:
//                        playSc.player[playSc.currentPlayer].moveRight();
////                        rightPressed = true;
//                        break;
//
//                    case Input.Keys.W:
//                        playSc.player[playSc.currentPlayer].jump();
//                        break;
//
////                    // cheats
////                    case Input.Keys.SHIFT_RIGHT:
////                        if (player[currentPlayer].getX() + 600 < 7200)
////                            player[currentPlayer].setX(player[currentPlayer].getX() + 600);
////                        break;
////                    case Input.Keys.SHIFT_LEFT:
////                        if (player[currentPlayer].getX() - 600 > 0)
////                            player[currentPlayer].setX(player[currentPlayer].getX() - 600);
////                        break;
//
//                    // switch player input handling
//                    case Input.Keys.Q:
//                        playSc.switchPlayer(-1);
//                        break;
//
//                    case Input.Keys.E:
//                        playSc.switchPlayer(1);
//                        break;
//
//                    default:
//                        break;
//                }
//                return true;
//            }
//
//            @Override
//            public boolean keyUp(InputEvent event, int keycode) {
//                switch (keycode) {
//                    case Input.Keys.A:
//                        playSc.player[playSc.currentPlayer].notMoveLeft();
////                        leftPressed = false;
//                        break;
//
//                    case Input.Keys.D:
//                        playSc.player[playSc.currentPlayer].notMoveRight();
////                        rightPressed = false;
//                        break;
//
//                    case Input.Keys.W:
////                        playSc.player[playSc.currentPlayer].notJump();
//                        break;
//
//                    default:
//                        break;
//                }
//                return true;
//            }
//
//            @Override
//            public boolean keyTyped(InputEvent event, char character) {
//                return false;
//            }
//        });
//
//        Gdx.input.setInputProcessor(stage);
//
//        table = new Table();
//        table.left().bottom();
//
//        Image rightImg = new Image(new Texture(Const.RIGHTARROW_PATH));
//        rightImg.setSize(50, 50);
//        rightImg.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                playSc.player[playSc.currentPlayer].moveRight();
////                rightPressed = true;
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                playSc.player[playSc.currentPlayer].notMoveRight();
////                rightPressed = false;
//            }
//        });
//
//        Image leftImg = new Image(new Texture(Const.LEFTARROW_PATH));
//        leftImg.setSize(50, 50);
//        leftImg.addListener(new InputListener() {
//            @Override
//            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
//                playSc.player[playSc.currentPlayer].moveLeft();
////                leftPressed = true;
//                return true;
//            }
//
//            @Override
//            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
//                playSc.player[playSc.currentPlayer].notMoveLeft();
////                leftPressed = false;
//            }
//        });
//
//        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
//        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());
//
//        table.setColor(1,1,1,0.5f);
//
//        stage.addActor(table);
//
//    }
//    public void draw() {
//        stage.draw();
//    }
//
//    public boolean isLeftPressed() {
//        return leftPressed;
//    }
//
//    public boolean isRightPressed() {
//        return rightPressed;
//    }
//
//    public boolean isSwitchedLeft() { return switchLeft; }
//
//    public boolean isSwitchedRight() { return switchRight; }
//
//    public void resize(int width, int height){
//        viewport.update(width,height);
//    }
//}
