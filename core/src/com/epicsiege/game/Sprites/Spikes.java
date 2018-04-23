package com.epicsiege.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Screens.PlayScreen;



public class Spikes  extends Sprite{

    protected World world;
    protected PlayScreen screen;

    public Body b2body;
    public static Vector2 velocity;


    private float stateTime;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> spinAnimation;
    private Array<TextureRegion> frames;
    private boolean x;
    private boolean y;


    public Spikes (World world, PlayScreen screen, float x, float y) {


        SpikesDef(world, screen, x, y);

        defineSpikes(x, y);
        velocity = new Vector2(0.7f, 0);


    }

    public void SpikesDef (World world, PlayScreen screen, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        setPosition( x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 7; i++)
        {
            frames.add(new TextureRegion(screen.getAtlasSpike().findRegion("Spikes"), (i * 46) - 1, 0, 43, 125));//152
        }
        spinAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        //setBounds(getX(), getY(), 86 / MyGdxGame.PPM, 86 / MyGdxGame.PPM);
        setBounds(getX(), getY(), 43 / MyGdxGame.PPM, 125 / MyGdxGame.PPM);
    }


    //protected abstract void defineSpikes();

    protected void defineSpikes(float x, float y) {

        BodyDef bdef = new BodyDef();

        bdef.position.set(x / MyGdxGame.PPM, y / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Our collision detectors
        FixtureDef fdef = new FixtureDef();
        FixtureDef fdef1 = new FixtureDef();
        FixtureDef fdef2 = new FixtureDef();
        //Defines the physical components of our Avatar(Guy), our Guy is a sphere with a radius of 6.
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / MyGdxGame.PPM);


        fdef.filter.categoryBits = MyGdxGame.SPIKES_BIT;
        fdef.filter.maskBits = MyGdxGame.DEFAULT_BIT | MyGdxGame.GUY_BIT | MyGdxGame.SPIKES_BIT;
        fdef1.filter.categoryBits = MyGdxGame.SPIKES_BIT;
        fdef1.filter.maskBits = MyGdxGame.DEFAULT_BIT | MyGdxGame.GUY_BIT | MyGdxGame.SPIKES_BIT;
        fdef2.filter.categoryBits = MyGdxGame.SPIKES_BIT;
        fdef2.filter.maskBits = MyGdxGame.DEFAULT_BIT | MyGdxGame.GUY_BIT | MyGdxGame.SPIKES_BIT;
        EdgeShape bodyf = new EdgeShape();
        EdgeShape bodyb = new EdgeShape();
        bodyf.set(new Vector2(18 / MyGdxGame.PPM, 55 / MyGdxGame.PPM), new Vector2(18 / MyGdxGame.PPM, -17 / MyGdxGame.PPM));
        bodyb.set(new Vector2(-18 / MyGdxGame.PPM, 55 / MyGdxGame.PPM), new Vector2(-18 / MyGdxGame.PPM, -17 / MyGdxGame.PPM));
        fdef.shape = shape;
        fdef1.shape = bodyf;
        fdef2.shape = bodyb;
        fdef1.isSensor = true;
        fdef2.isSensor = true;
        b2body.createFixture(fdef1).setUserData("body");
        b2body.createFixture(fdef2).setUserData("body");
        b2body.createFixture(fdef);


        PolygonShape topHalf = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-18, 55).scl(1 / MyGdxGame.PPM);
        vertice[1] = new Vector2(-18, -17).scl(1 / MyGdxGame.PPM);
        vertice[2] = new Vector2(18, 55).scl(1 / MyGdxGame.PPM);
        vertice[3] = new Vector2(18, -17).scl(1 / MyGdxGame.PPM);
        topHalf.set(vertice);

        fdef.shape = topHalf;
        fdef.restitution = 1f;
        fdef.filter.categoryBits = MyGdxGame.SPIKES_BIT;
        fdef.filter.maskBits = MyGdxGame.DEFAULT_BIT | MyGdxGame.GUY_BIT | MyGdxGame.SPIKES_BIT;
        b2body.createFixture(fdef).setUserData(this);


    }

    //protected abstract void defineSpike();


    //protected abstract void defineSpike();
    public static void reverseVelocity(boolean x, boolean y){
        if (x)
            velocity.x = -velocity.x;

        if (y)
            velocity.y = -velocity.y;
    }



    // public void draw(Batch batch) {
    //     super.draw(batch);
    //}

    public void update (float dt) {
        stateTime += dt;

        b2body.setLinearVelocity(velocity);
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(spinAnimation.getKeyFrame(stateTime, true));

    }
}
