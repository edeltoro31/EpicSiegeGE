package com.epicsiege.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.epicsiege.game.Screens.PlayScreen;
import com.epicsiege.game.Sprites.Floor;
import com.epicsiege.game.Sprites.Spikes;
import com.epicsiege.game.Sprites.Tokens;



public class B2WorldCreator {

    //Our array of spikes throughout the Map
    private static com.badlogic.gdx.utils.Array<Spikes> spike;

    public B2WorldCreator(World world, TiledMap map, PlayScreen screen) {
       // World world = screen.getWorld();
       // TiledMap map =screen.getMap();
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


        //creates objects for our Tokens, so our Guy can interact with Tokens.
        //the 2 stands for the objects location on the Tile Editor.
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //creates Tokens in our Map.
            new Tokens(world, map , rect);
        }

        //creates objects for our Floor, so our Guy doesn't fall through to ground.
        //the 3 stands for the objects location on the Tile Editor.
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            //creates the floor though out our Map.
            new Floor(world, map, rect);
        }

        spike = new Array<Spikes>();
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            spike.add(new Spikes(world, screen, rect.getX(), rect.getY()));

        }


    }

    public Array<Spikes> getSpike() {
        return spike;
    }
    }
