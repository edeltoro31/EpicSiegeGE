package com.epicsiege.game.Screens;

//import java.util.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Scenes.Hud;
import com.epicsiege.game.Sprites.Guy;
import com.epicsiege.game.Sprites.Spikes;
import com.epicsiege.game.Tools.B2WorldCreator;
import com.epicsiege.game.Tools.WorldContactListener;
import com.badlogic.gdx.audio.Music;

/**
 * Created by Joselito on 3/24/2018.
 */

public class PlayScreen implements Screen{
    //used to set Screen
    private MyGdxGame game;

    private TextureAtlas atlas;
    private TextureAtlas atlasSpike;

    //play screen variables
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private Guy player;
    private Music music;

    //To create our array of Spikes.
    private B2WorldCreator creator;

    //tiled map variables
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //Box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    public PlayScreen(MyGdxGame game){
        atlas = new TextureAtlas("Hero_and_Enemies.pack");


        //sprites for Spikes
        atlasSpike = new TextureAtlas("Spikes.pack");

        this.game = game;
        //camera that follows player though the map.
        gamecam = new OrthographicCamera();


        //maintains virtual aspect ratio despite the size of screen.
        gamePort = new FitViewport(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM, gamecam);


        //Hud keeps track of our score, timer, and level.
        hud = new Hud(game.batch);

        //Creates our world.
        maploader = new TmxMapLoader();
        map = maploader.load("epic_siege.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGdxGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
        world = new World(new Vector2(0, -10 ), true);
        b2dr = new Box2DDebugRenderer();


        //Gets our Avatar (Guy)
        player = new Guy(world,this);

        //creates Spikes in our map.
        creator = new B2WorldCreator(world, map, this);


        world.setContactListener(new WorldContactListener());
        music = MyGdxGame.manager.get("audio/music/hero_music.mp3", Music.class);
        music.setLooping(true);
        music.play();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    //NEW
    public TextureAtlas getAtlasSpike() {
        return atlasSpike;
    }

    @Override
    public void show() {

    }

    //keys, what keys make the character move
    //and how he moves.
    public void handleInput(float dt) {

        if (player.currentState != Guy.State.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
                player.b2body.applyLinearImpulse(new Vector2(0, 2f), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);

            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                player.b2body.applyForce(new Vector2(0f, 0.1f), player.b2body.getWorldCenter(), true);
        }
    }

    public void update(float dt) {
        handleInput(dt);

        world.step(1/60f, 6, 2);

        player.update(dt);

        //update our spikes.
        for (Spikes spikes : creator.getSpike())
            spikes.update(dt);

        hud.update(dt);

        if (player.currentState != Guy.State.DEAD) {
            gamecam.position.x = player.b2body.getPosition().x;
        }

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1 );
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);

        //draws the spikes on the map
        for (Spikes spikes : creator.getSpike())
            spikes.draw(game.batch);

        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        if (gameOver()) {
            game.setScreen(new GameOverScreen(game, Hud.score));
            dispose();
        }
    }

    public boolean gameOver() {
        if (player.currentState == Guy.State.DEAD && player.getStateTimer() > 3) {
            return true;
        }
        else
            return false;
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);

    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld () {
        return world;
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
