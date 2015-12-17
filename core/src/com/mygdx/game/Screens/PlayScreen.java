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
import com.mygdx.game.Sprites.Player;
import com.mygdx.game.androidGame;

public class PlayScreen implements Screen{
    private androidGame game;

    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;

    // tiled map variables
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private Player p1;
    private Vector2 spawnPos;

    private BitmapFont FPSfont;

    public PlayScreen(androidGame game) {
        this.game = game;
        spawnPos = new Vector2(100, 500);
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

        FPSfont = new BitmapFont();

        p1 = new Player((TiledMapTileLayer) map.getLayers().get(1));
        p1.setPosition(spawnPos.x,spawnPos.y);

        Gdx.input.setInputProcessor(p1);

    }

    public void update(float dt) {
        p1.update(dt);

        gamecam.position.x = p1.getX() + p1.getWidth();
        gamecam.position.x = MathUtils.clamp(
                gamecam.position.x,
                gamePort.getWorldWidth() / 2 + Const.TILE_WIDTH,
                map.getProperties().get("width", Integer.class)
                        * Const.TILE_WIDTH - gamePort.getWorldWidth() / 2 - Const.TILE_WIDTH
        );

        Gdx.app.log("log", "" + gamecam.position.x);

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

        p1.draw(game.batch);
        game.batch.end();

        // FPS on screen
        game.FPSbatch.begin();
        FPSfont.draw(game.FPSbatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 20, 20);
        game.FPSbatch.end();

        // Hud draw
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