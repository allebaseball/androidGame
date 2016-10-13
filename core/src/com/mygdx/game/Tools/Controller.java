package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Const;
import com.mygdx.game.androidGame;


public class Controller {
    Viewport viewport;
    Stage stage;
    OrthographicCamera cam;

    Image joyBack,joyKnob,slide,AKey,BKey;
    private boolean dragTouch_state = false;
    private boolean touch_state = false;
    private int touchPosX = 0, touchPosY = 0, min_distance = 0;
    private Vector2 startPos;
    private float distance = 0, angle = 0, maxDistance = 0,dragDistance = 0;
    private int OFFSET = 0;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(Const.V_WIDTH, Const.V_HEIGHT, cam);
        stage = new Stage(viewport, androidGame.batch);
        Gdx.input.setInputProcessor(stage);
        Gdx.app.log("OFFSET", "" + OFFSET);

        joyBack = new Image(new Texture(Const.JOYSTICK_BACK_PATH));
        joyKnob = new Image(new Texture(Const.JOYSTICK_KNOB_PATH));
        slide = new Image(new Texture(Const.JOYSTICK_SLIDE_PATH));
        AKey = new Image(new Texture(Const.JOYSTICK_AKEY_PATH));
        BKey = new Image(new Texture(Const.JOYSTICK_BKEY_PATH));

        setImage(joyBack, 140 , 140, OFFSET, OFFSET, .3f);
        //setImage(joyKnob, 75, 75, 63, 63, .3f);
        setImage(slide, 45, 140, OFFSET, 145 + OFFSET, .3f);

        maxDistance = (float) (joyBack.getWidth()*0.75/2);

        joyKnob.setSize(60,60);
        joyKnob.setColor(joyKnob.getColor().r, joyKnob.getColor().r, joyKnob.getColor().r,.3f);

        joyBack.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                touchPosX = (int) (x - joyBack.getWidth() / 2);
                touchPosY = (int) (y -  joyBack.getHeight() / 2);
                Gdx.app.log("coordinate_1","" +  x + ", " + y);

                distance = (float) Math.sqrt(Math.pow(touchPosX, 2) + Math.pow(touchPosY, 2));

                if(distance <= maxDistance) {
                    touch_state = true;

                    angle = (float) Math.toDegrees(cal_angle(touchPosX, touchPosY));

                    joyKnob.setPosition(x + OFFSET - joyKnob.getWidth() / 2, y + OFFSET- joyKnob.getHeight() / 2);
                    Gdx.app.log("coordinate_2","" +  x + ", " + y);
                    stage.addActor(joyKnob);
                    return true;
                }
                return false;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                touchPosX = (int) (x - joyBack.getWidth()/2);
                touchPosY = (int) (y - joyBack.getHeight()/2);

                distance = (float) Math.sqrt(Math.pow(touchPosX, 2) + Math.pow(touchPosY, 2));
                angle = (float) Math.toDegrees(cal_angle(touchPosX, touchPosY));
                //Gdx.app.log("angleDrag","" + angle);

                if(distance <= maxDistance)
                    joyKnob.setPosition(x + OFFSET - joyKnob.getWidth()/2,y + OFFSET - joyKnob.getHeight()/2);

                else if(distance > maxDistance){
                    float actualX = (float) (Math.cos(cal_angle(touchPosX,touchPosY)) *maxDistance + OFFSET);
                    float actualY = (float) (Math.sin(cal_angle(touchPosX,touchPosY)) *maxDistance + OFFSET);

                    actualX += (joyBack.getWidth()/2);
                    actualY += (joyBack.getHeight()/2);

                    joyKnob.setPosition(actualX - joyKnob.getWidth()/2,actualY - joyKnob.getHeight()/2);

                }else
                joyKnob.remove();
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                touch_state = false;
                joyKnob.remove();
            }
        });

        slide.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                dragTouch_state = true;
                startPos = new Vector2(x, y);
                return true;
            }

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                dragDistance = x - startPos.x;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                dragDistance = 0;
                dragTouch_state = false;
            }
        });
        stage.addActor(slide);
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
        return Math.atan2(y,x);
    }

    public int get4Direction() {
        if(distance > min_distance && touch_state) {
            if(angle >= 45 && angle < 135 ) {
                return Const.STICK_UP;
            } else if(angle >= -45 && angle < 45 ) {
                return Const.STICK_RIGHT;
            } else if(angle >= -135 && angle < -45 ) {
                return Const.STICK_DOWN;
            } else if((angle >= 135 && angle <= 180 )||(angle >=-180 && angle < -135)) {
                return Const.STICK_LEFT;
            }
        } else if(distance <= min_distance && touch_state) {
            return Const.STICK_NONE;
        }
        return 0;
    }
    public void resetDrag(){
        dragTouch_state = false;
    }

    public void setUIPosition(int offset){
        OFFSET = offset;
        joyBack.setPosition(OFFSET,OFFSET);
        slide.setPosition(OFFSET, joyBack.getHeight() + 5 + OFFSET);

    }

    public int getDragDistance(){
        if(Math.abs(dragDistance) > slide.getWidth()/3 && dragTouch_state == true)
            if(dragDistance > 0)
                return 1;
            else if(dragDistance < 0)
                return -1;
        else return 0;
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
