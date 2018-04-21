package com.epicsiege.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Scenes.Hud;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Joselito on 4/17/2018.
 */

public class Tokens extends InteractiveTileObjects {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 1;
    //Creates our Tokens on our Map.
    public Tokens(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        tileSet = map.getTileSets().getTileSet("energy_balls");

        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.TOKEN_BIT);
    }

    //When the sensor collides with our Tokens, the Tokens are set to "null" and disappear from our Map.
    @Override
    public void onBodyHit () {
        Gdx.app.log("Token", "Collision");

        if(getCell().getTile().getId() == BLANK_COIN)
            MyGdxGame.manager.get("audio/sounds/bump.wav", Sound.class).play();
        else
            MyGdxGame.manager.get("audio/sounds/coin.wav", Sound.class).play();


        setCategoryFilter(MyGdxGame.DESTROYED_BIT);
         //tile where Token is located is set to "null".
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(100);
    }
}
