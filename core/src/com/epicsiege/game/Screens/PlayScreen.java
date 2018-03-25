package com.epicsiege.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epicsiege.game.MyGdxGame;

/**
 * Created by eleaz on 3/24/2018.
 */

public class PlayScreen implements Screen {

    private MyGdxGame game;
    Texture texture;

    private OrthographicCamera gamecam;
    private Viewport gamePort;

    public PlayScreen(MyGdxGame game){
        this.game = game;
        texture = new Texture("EpicSiegeAVI.jpg");
        gamecam = new OrthographicCamera();
        gamePort = new FitViewport(800, 400, gamecam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(150, 165, 45, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        game.batch.end();
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

    }
}