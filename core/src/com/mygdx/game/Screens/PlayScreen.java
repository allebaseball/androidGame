package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.androidGame;

public class PlayScreen implements Screen{
    private androidGame game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    private Vector2 spawnPos;

    //Tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables

    private Player p1;

    private BitmapFont FPSfont;

    public PlayScreen(androidGame game) {
        this.game = game;
        spawnPos = new Vector2(50,50);
    }

    @Override
    public void show() {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/TestTiledMap.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);

        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(androidGame.V_WIDTH , androidGame.V_HEIGHT , gamecam);
        gamecam.position.set(gamePort.getWorldWidth() / 2 + 32, gamePort.getWorldHeight() / 2, 0);

        hud = new Hud(game.batch);

        FPSfont = new BitmapFont();

        p1 = new Player(spawnPos);
    }

    public void handleInput(float dt) {
        if (Gdx.input.justTouched()) {
            p1.setJump();
        }
    }

    public void update(float dt) {
        handleInput(dt);
        p1.update(dt);

        gamecam.position.set(
                p1.getX() + p1.getWidth() / 2,
                p1.getY() + p1.getHeight() / 2,
                0
        );

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);
        //Set color and clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Render game map
        renderer.render();

        //Render Box2DDebugLines

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();

        p1.draw(game.batch);
        game.batch.end();

        //FPS on screen
        game.FPSbatch.begin();
        FPSfont.draw(
                game.FPSbatch,
                "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        game.FPSbatch.end();

        //Hud draw
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

}
