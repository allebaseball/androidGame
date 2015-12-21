package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.Const;
import com.mygdx.game.androidGame;

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean leftPressed, rightPressed;
    OrthographicCamera cam;

    Table table;

    public Controller() {
        cam = new OrthographicCamera();
        viewport = new FitViewport(Const.V_WIDTH, Const.V_HEIGHT, cam);
        stage = new Stage(viewport, androidGame.batch);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    // move player input handling
                    case Input.Keys.A:
                        leftPressed = true;
                        break;

                    case Input.Keys.D:
                        rightPressed = true;
                        break;

                    case Input.Keys.W:
//                        player[currentPlayer].jump();
                        break;

//                    // cheats
//                    case Input.Keys.SHIFT_RIGHT:
//                        if (player[currentPlayer].getX() + 600 < 7200)
//                            player[currentPlayer].setX(player[currentPlayer].getX() + 600);
//                        break;
//                    case Input.Keys.SHIFT_LEFT:
//                        if (player[currentPlayer].getX() - 600 > 0)
//                            player[currentPlayer].setX(player[currentPlayer].getX() - 600);
//                        break;

//                    // switch player input handling
//                    case Input.Keys.Q:
//                        switchPlayer(-1);
//                        break;
//
//                    case Input.Keys.E:
//                        switchPlayer(1);
//                        break;

                    default:
                        break;
                }
                return true;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.A:
                        leftPressed = false;
                        break;

                    case Input.Keys.D:
                        rightPressed = false;
                        break;

                    case Input.Keys.W:
//                        player[currentPlayer].notJump();
                        break;

                    default:
                        break;
                }
                return true;
            }

            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return false;
            }
        });

        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.left().bottom();

        Image rightImg = new Image(new Texture(Const.RIGHTARROW_PATH));
        rightImg.setSize(50, 50);
        rightImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        Image leftImg = new Image(new Texture(Const.LEFTARROW_PATH));
        leftImg.setSize(50, 50);
        leftImg.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        table.add(leftImg).size(leftImg.getWidth(), leftImg.getHeight());
        table.add(rightImg).size(rightImg.getWidth(), rightImg.getHeight());

        table.setColor(1,1,1,0.5f);

        stage.addActor(table);

    }
    public void draw() {
        stage.draw();
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height){
        viewport.update(width,height);
    }
}
