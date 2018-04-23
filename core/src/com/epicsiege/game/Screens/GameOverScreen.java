package com.epicsiege.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
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

    int score, highscore;

    public GameOverScreen (Game game, int score) {
        this.score = score;
        this.game = game;

        Preferences prefs = Gdx.app.getPreferences("epicsiege");
        this.highscore = prefs.getInteger("highscore", 0);

        if (score > highscore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }

        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);

        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle font = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        com.badlogic.gdx.scenes.scene2d.ui.Label gameOverLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Game Over", font);
        com.badlogic.gdx.scenes.scene2d.ui.Label scoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Your Score: " + score, font);
        com.badlogic.gdx.scenes.scene2d.ui.Label highscoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Highest Score: " + highscore, font);
        com.badlogic.gdx.scenes.scene2d.ui.Label playAgainLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Play Again? (Press any key)", font);
        com.badlogic.gdx.scenes.scene2d.ui.Label congratulationsLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("CONGRATULATIONS", font);

        if (score >= highscore) {
            table.add(gameOverLabel).expandX();
            table.row();
            table.add(congratulationsLabel).expandX().padTop(10f);
            table.row();
            table.add(highscoreLabel).expandX().padTop(10f);
            table.row();
            table.add(playAgainLabel).expandX().padTop(10f);
        }
        else {
            table.add(gameOverLabel).expandX();
            table.row();
            table.add(scoreLabel).expandX().padTop(10f);
            table.row();
            table.add(highscoreLabel).expandX().padTop(10f);
            table.row();
            table.add(playAgainLabel).expandX().padTop(10f);
        }

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            Guy.hit(false, -2f, 4f);
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
