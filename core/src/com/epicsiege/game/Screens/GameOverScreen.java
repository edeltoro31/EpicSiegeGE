package com.epicsiege.game.Screens;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

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
import com.epicsiege.game.Scenes.Hud;
import com.epicsiege.game.Sprites.Guy;
import java.awt.Label;

import javax.swing.JOptionPane;

/**
 * Created by Joselito on 4/21/2018.
 */


public class GameOverScreen implements Screen{

    private Viewport viewport;
    private Stage stage;
    private Game game;

    int score;
    int highscore;
    public  static String name = " ";

    private String winner = "";


    public GameOverScreen (Game game, int score) {

        this.score = score;

        this.game = game;

        Preferences prefs = Gdx.app.getPreferences("epicsiege");
        this.highscore = prefs.getInteger("highscore", 0);
        //this.winner = prefs.getString("winner", null);

        if (score > highscore) {

            Gdx.input.setOnscreenKeyboardVisible(true);
            MyTextInputListener listener = new MyTextInputListener();
            Gdx.input.getTextInput(listener, "Your Name Worthy Challenger", name, "Winner");

            CheckScore();
            prefs.putString("name", name);
           // winner = Hud.name;
            //winner = name;
            prefs.putInteger("highscore", score);


            prefs.flush();
        }
        else {
            if (!name.equals(" "))
                CheckScore();

            name = this.getHighscorValue();
        }


        viewport = new FitViewport(MyGdxGame.V_WIDTH, MyGdxGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, ((MyGdxGame) game).batch);

        com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle font = new com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle(new BitmapFont(), Color.WHITE);

        Table table = new Table();
        table.center();
        table.setFillParent(true);

        com.badlogic.gdx.scenes.scene2d.ui.Label gameOverLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Game Over", font);
        com.badlogic.gdx.scenes.scene2d.ui.Label scoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Your Score: " + score, font);
        com.badlogic.gdx.scenes.scene2d.ui.Label highscoreLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Highest Score: " + name + " : " + highscore, font);
        com.badlogic.gdx.scenes.scene2d.ui.Label playAgainLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("Play Again? (Press any key)", font);
        com.badlogic.gdx.scenes.scene2d.ui.Label congratulationsLabel = new com.badlogic.gdx.scenes.scene2d.ui.Label("CONGRATULATIONS", font);

        if (score > highscore) {
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

    public void CheckScore () {

            //String name = JOptionPane.showInputDialog("You set a new highscore. What is your name?");
            //highscore = name + ":" + score;

            File scoreFile = new File("highscore.dat");
            if (!scoreFile.exists()){
                try {
                    scoreFile.createNewFile();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter writeFile = null;
            PrintWriter writer = null;
            try {
                writeFile = new FileWriter("highscore.dat");
                writer = new PrintWriter(writeFile);

                writer.write(this.name);
                writer.close();
            }
            catch (Exception e) {
                //errors
            }
            finally {
                try {
                    if(writer != null)
                        writer.close();
                }
                catch (Exception e) {

                }

            }


    }

    public String getHighscorValue()
    {
        FileReader readFile = null;
        BufferedReader reader = null;
        //File readFile  = null;
        //Scanner x = null; //new Scanner(readFile);
        try {
            readFile = new FileReader("highscore.dat");
            //x = new Scanner(readFile);
            reader = new BufferedReader(readFile);
            return reader.readLine();
            //return x.next();
        }
        catch (Exception e) {
            return "Nobody";
        }
        finally {
            try {
                if(reader != null)
                    reader.close();
            }
            catch  (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Preferences prefs = Gdx.app.getPreferences("epicsiege");
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            // Gdx.input.setOnscreenKeyboardVisible(true);


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



/*
public class GameOverScreen implements Screen{

    private Viewport viewport;
    private Stage stage;
    private Game game;

    int score;
    String highscore;
    String name;

    private String winner = "";


    public GameOverScreen (Game game, int score, String name) {

        this.score = score;
        this.name = name;
        this.game = game;
        /*
        Preferences prefs = Gdx.app.getPreferences("epicsiege");
        this.highscore = prefs.getInteger("highscore", 0);
        this.winner = prefs.getString("winner", null);
        */

       // if (score > Integer.parseInt((highscore.split(":")[1]))) {
            /*
            Gdx.input.setOnscreenKeyboardVisible(true);
            MyTextInputListener listener = new MyTextInputListener();
            Gdx.input.getTextInput(listener, "Your Name Worthy Challenger", name, "Winner");
            /*
            prefs.putString("winner", Hud.name);
            winner = Hud.name;
            prefs.putInteger("highscore", score);
            prefs.flush();

            CheckScore();
       // }
        if (winner.equals("")) {
            highscore = this.getHighscorValue();
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

        if (score >= Integer.parseInt(highscore)) {
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

    public void CheckScore () {



        if (score > Integer.parseInt((highscore.split(":")[1]))){
         String name = JOptionPane.showInputDialog("You set a new highscore. What is your name?");
         highscore = name + ":" + score;

            File scoreFile = new File("highscore.dat");
            if (!scoreFile.exists()){
                try {
                    scoreFile.createNewFile();

                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            FileWriter writeFile = null;
            BufferedWriter writer = null;
            try {
                writeFile = new FileWriter(scoreFile);
                writer = new BufferedWriter(writeFile);

                writer.write(this.highscore);
            }
            catch (Exception e) {
                //errors
            }
            finally {
                try {
                    if(writer != null)
                        writer.close();
                }
                catch (Exception e) {

                }

            }
        }

    }

    public String getHighscorValue()
    {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);
            return reader.readLine();
        }
        catch (Exception e) {
            return "Nobody:0";
        }
        finally {
            try {
                if(reader != null)
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Preferences prefs = Gdx.app.getPreferences("epicsiege");
        if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
           // Gdx.input.setOnscreenKeyboardVisible(true);


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
*/