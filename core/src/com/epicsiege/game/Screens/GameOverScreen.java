package com.epicsiege.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Sprites.Guy;
import java.awt.Label;

/**
 * Created by Joselito on 4/21/2018.
 */

public class GameOverScreen implements Screen{

    private Viewport viewport;
    private Stage stage;
    private Game game;

    public GameOverScreen (Game game) {
        this.game = game;
        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);

        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle font = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        com.badlogic.gdx.scenes.scene2d.ui.Label gameOverLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Game Over", font);
        com.badlogic.gdx.scenes.scene2d.ui.Label playAgainLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Play Again? (Press any key)", font);

        table.add(gameOverLabel).expandX();
        table.row();
        table.add(playAgainLabel).expandX().padTop(10f);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Guy.hit(false);
            game.setScreen(new PlayScreen((MyGdxGame) game));
            dispose();
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
