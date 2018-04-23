package com.epicsiege.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.epicsiege.game.Scenes.Hud;

/**
 * Created by Joselito on 4/23/2018.
 */

public class MyTextInputListener implements Input.TextInputListener {
    @Override
    public void input(String text) {
        GameOverScreen.name = text;
    }

    @Override
    public void canceled() {

    }
}
