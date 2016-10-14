package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Const;
import com.mygdx.game.androidGame;

public class MainMenu implements Screen{
    private androidGame game;

    private Viewport viewport;
    private OrthographicCamera cam;
    private Stage stage;

    private Image playBtn,settingsBtn,quitBtn;
   // private static int buttonWidth = ,buttonHeight;

    public MainMenu(androidGame game) {
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(Const.V_WIDTH,Const.V_HEIGHT);
        stage = new Stage(viewport,game.batch);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void show(){

        Table table = new Table();
        table.center();
        table.setPosition(Const.V_WIDTH/2,Const.V_HEIGHT/2);

        playBtn = new Image(new Texture(Const.MENU_START_PATH));
        settingsBtn = new Image(new Texture(Const.MENU_SETTINGS_PATH));
        quitBtn = new Image (new Texture(Const.MENU_QUIT_PATH));

        playBtn.setSize(200,80);
        settingsBtn.setSize(200,80);
        quitBtn.setSize(200,80);

        playBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlayScreen(game));
                dispose();
                return false;
            }
        });

        settingsBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }
        });

        quitBtn.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.exit(1);
                dispose();
                return false;
            }
        });

        table.add(playBtn).size(playBtn.getWidth(),playBtn.getHeight());
        table.row().padTop(10);
        table.add(settingsBtn).size(settingsBtn.getWidth(),settingsBtn.getHeight());
        table.row().padTop(10);
        table.add(quitBtn).size(quitBtn.getWidth(),quitBtn.getHeight());

        stage.addActor(table);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
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
        stage.dispose();
    }

}
