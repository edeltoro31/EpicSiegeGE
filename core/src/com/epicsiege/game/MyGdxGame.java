package com.epicsiege.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.epicsiege.game.Screens.PlayScreen;

public class MyGdxGame extends Game {

	//Dimensions of our game.
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short DEFAULT_BIT = 1;
	public static final short GUY_BIT = 2;
	public static final short TOKEN_BIT = 4;
	public static final short DESTROYED_BIT = 16;


	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	

}
