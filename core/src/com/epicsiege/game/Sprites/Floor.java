package com.epicsiege.game.Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Scenes.Hud;

/**
 * Created by Joselito on 4/18/2018.
 */

public class Floor extends InteractiveTileObjects {



    //Creates our floor;
    public Floor (World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
    }

    @Override
    public void onBodyHit () {

        Spikes.reverseVelocity(true,false);

        //Gdx.app.log("Token", "Collision");
    }
}
