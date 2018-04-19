package com.epicsiege.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.epicsiege.game.MyGdxGame;

/**
 * Created by Joselito on 4/17/2018.
 */

public class Tokens extends InteractiveTileObjects {
    public Tokens(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(MyGdxGame.TOKEN_BIT);
    }

    @Override
    public void onBodyHit () {
        Gdx.app.log("Token", "Collision");
        setCategoryFilter(MyGdxGame.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
