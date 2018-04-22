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
import java.sql.*;

/**
 * Created by eleaz on 4/22/2018.
 */

public class DatabaseHelper extends MyGdxGame{

    public DatabaseHelper() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection c = DriverManager.getConnection("jdbc:mysql://104.198.172.94:3306/EpicSiege","root","1234qwer");
        Statement s = c.createStatement();
        ResultSet res = s.executeQuery("select * from Highscores");

        while (res.next()){

        }c.close();
    }
}
