 package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.mygdx.game.Const;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Fighter;
import com.mygdx.game.Sprites.Hooker;
import com.mygdx.game.Sprites.Jumper;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.Tools.Controller;
import com.mygdx.game.androidGame;

public class PlayScreen implements Screen {
    private androidGame game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    // tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // player creation
    public Player[] player = new Player[3];
    private Vector2 spawnPos;
    public int currentPlayer = 0;

    // controller
    Controller controller;

    private BitmapFont FPSfont = new BitmapFont();

    public PlayScreen(androidGame game) {
        this.game = game;
        spawnPos = new Vector2(200, 200);
    }

    @Override
    public void show() {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(Const.MAP_PATH);

        renderer = new OrthogonalTiledMapRenderer(map);

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(Const.V_WIDTH, Const.V_HEIGHT, gamecam);
        gamecam.position.set(
                gamePort.getWorldWidth() / 2 + Const.TILE_WIDTH,
                gamePort.getWorldHeight() / 2,
                0
        );

        hud = new Hud(game.batch);

        player[0] = new Fighter((TiledMapTileLayer) map.getLayers().get(1), 0);
        player[1] = new Jumper((TiledMapTileLayer) map.getLayers().get(1), 1);
        player[2] = new Hooker((TiledMapTileLayer) map.getLayers().get(1), 2);
        player[currentPlayer].setPosition(spawnPos.x,spawnPos.y);

        controller = new Controller();
//        Gdx.input.setInputProcessor(this);
    }

    public void handleInput() {
//        if (controller.isRightPressed())
//            player[currentPlayer].moveRight();
//        else if (controller.isLeftPressed())
//            player[currentPlayer].moveLeft();
//        else {
//            player[currentPlayer].notMoveRight();
//            player[currentPlayer].notMoveLeft();
//        }
//
//        if (controller.isSwitchedLeft()) {
//            switchPlayer(-1);
//            controller.switchLeft = false;
//        }
//        else if (controller.isSwitchedRight()) {
//            switchPlayer(1);
//            controller.switchLeft = false;
//        }
    }

    public void update(float dt) {
//        handleInput();
        player[currentPlayer].update(dt);

        gamecam.position.x = player[currentPlayer].getX() + player[currentPlayer].getWidth() / 2;
        gamecam.position.x = MathUtils.clamp(
                gamecam.position.x,
                gamePort.getWorldWidth() / 2 + Const.TILE_WIDTH,
                map.getProperties().get("width", Integer.class)
                        * Const.TILE_WIDTH - gamePort.getWorldWidth() / 2 - Const.TILE_WIDTH
        );

//        Gdx.app.log("log", "" + gamecam.position.x);

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        // set color and clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render game map
        renderer.render();

        update(delta);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        player[currentPlayer].draw(game.batch);
//        FPSfont.draw(game.batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        game.batch.end();

        // FPS on screen
        game.FPSbatch.begin();
        FPSfont.draw(game.FPSbatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        game.FPSbatch.end();

        // Hud draw
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        // controller draw
        controller.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        controller.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        hud.dispose();
    }

////     InputProcessor methods
//    @Override
//    public boolean keyDown(int keycode) {
//        switch (keycode) {
//            // move player input handling
//            case Input.Keys.A:
//                player[currentPlayer].moveLeft();
//                break;
//
//            case Input.Keys.D:
//                player[currentPlayer].moveRight();
//                break;
//
//            case Input.Keys.W:
//                player[currentPlayer].jump();
//                break;
//
//            // cheats
//            case Input.Keys.SHIFT_RIGHT:
//                if (player[currentPlayer].getX() + 600 < 7200)
//                    player[currentPlayer].setX(player[currentPlayer].getX() + 600);
//                break;
//            case Input.Keys.SHIFT_LEFT:
//                if (player[currentPlayer].getX() - 600 > 0)
//                    player[currentPlayer].setX(player[currentPlayer].getX() - 600);
//                break;
//
//            // switch player input handling
//            case Input.Keys.Q:
//                switchPlayer(-1);
//                break;
//
//            case Input.Keys.E:
//                switchPlayer(1);
//                break;
//
//            default:
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        switch (keycode) {
//            case Input.Keys.A:
//                player[currentPlayer].notMoveLeft();
//                break;
//
//            case Input.Keys.D:
//                player[currentPlayer].notMoveRight();
//                break;
//
//            case Input.Keys.W:
//                player[currentPlayer].notJump();
//                break;
//
//            default:
//                break;
//        }
//        return true;
//    }
//
//    @Override
//    public boolean keyTyped(char character) {
//        return false;
//    }
//
//    @Override
//    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        if ((screenX < Gdx.graphics.getWidth() / 2) && (screenY > Gdx.graphics.getHeight() / 2))
//            player[currentPlayer].moveLeft();
//        else if ((screenX > Gdx.graphics.getWidth() / 2)&&(screenY > Gdx.graphics.getHeight() / 2))
//            player[currentPlayer].moveRight();
//        else if ((screenY < Gdx.graphics.getHeight() / 2)) {
////            player[currentPlayer].jump();
//
//        }
//
//        System.out.println(screenX + "  " + screenY);
//        return true;
//    }
//
//    @Override
//    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
//        if ((screenX < Gdx.graphics.getWidth() / 2) && (screenY > Gdx.graphics.getHeight() / 2)) {
//            player[currentPlayer].notMoveLeft();
//        }
//        else if ((screenX > Gdx.graphics.getWidth() / 2) && (screenY > Gdx.graphics.getHeight() / 2)) {
//            player[currentPlayer].notMoveRight();
//        }
//        else if ((screenY < Gdx.graphics.getHeight() / 2))
//            player[currentPlayer].notJump();
//
//        return true;
//    }
//
//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        return false;
//    }
//
//    @Override
//    public boolean mouseMoved(int screenX, int screenY) {
//        return false;
//    }
//
//    @Override
//    public boolean scrolled(int amount) {
//        return false;
//    }

    public void switchPlayer(int verse) {
        Vector2 pos, vel;
        boolean runningRight;
        Player.State currentState, previousState;
        float stateTimer;

        runningRight = player[currentPlayer].isRunningRight();
        pos = player[currentPlayer].getPosition();
        vel = player[currentPlayer].getVelocity();
        player[currentPlayer].resetMoves();
        currentState = player[currentPlayer].getCurrentState();
        previousState = player[currentPlayer].getPreviousState();
        stateTimer = player[currentPlayer].getStateTimer();

        if (verse == 1)
            if (++currentPlayer > 2) currentPlayer = 0;
        if (verse == -1)
            if (--currentPlayer < 0) currentPlayer = 2;

        player[currentPlayer].setRunningRight(runningRight);
        player[currentPlayer].setPosition(pos);
        player[currentPlayer].setVelocity(vel);
        player[currentPlayer].setCurrentState(currentState);
        player[currentPlayer].setPreviousState(previousState);
        player[currentPlayer].setStateTimer(stateTimer);

        Gdx.app.log("Ciao", "" + currentPlayer);
    }
}