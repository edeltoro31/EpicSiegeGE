package com.epicsiege.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Screens.PlayScreen;


/**
 * Created by Joselito on 4/20/2018.
 */


public class Spikes  extends Sprite{

    protected World world;
    protected PlayScreen screen;

    public Body b2body;

    private float stateTime;
    private com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> spinAnimation;
    private Array<TextureRegion> frames;



    public Spikes (World world, PlayScreen screen, float x, float y, int a) {
        this.world = world;
        this.screen = screen;
        setPosition(x,y);

        SpikesDef(world, screen, x, y);

        defineSpikes(a);


    }

    public void SpikesDef (World world, PlayScreen screen, float x, float y) {
        //super(world, screen, x, y);
        frames = new Array<TextureRegion>();
        for (int i = 0; i < 7; i++)
        {
            frames.add(new TextureRegion(screen.getAtlasSpike().findRegion("Spikes"), i * 46, 0, 43, 152));//152
        }
        spinAnimation = new com.badlogic.gdx.graphics.g2d.Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        //setBounds(getX(), getY(), 86 / MyGdxGame.PPM, 86 / MyGdxGame.PPM);
        setBounds(getX(), getY(), 43 / MyGdxGame.PPM, 152 / MyGdxGame.PPM);
    }


    //protected abstract void defineSpikes();

    protected void defineSpikes(int x) {

        BodyDef bdef = new BodyDef();


        switch (x) {
            case 1:
                 bdef.position.set(310 / MyGdxGame.PPM, 10 / MyGdxGame.PPM);
                 break;
            case 2:
                bdef.position.set(385 / MyGdxGame.PPM, 10 / MyGdxGame.PPM);
                break;
            case 3:
                bdef.position.set(1125 / MyGdxGame.PPM, 60 / MyGdxGame.PPM);
                break;
            default:
                bdef.position.set(1550 / MyGdxGame.PPM, 60 / MyGdxGame.PPM);
                break;
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);


        //Our collision detectors
        FixtureDef fdef = new FixtureDef();
        //Defines the physical components of our Avatar(Guy), our Guy is a sphere with a radius of 6.
        CircleShape shape = new CircleShape();
        shape.setRadius(2 / MyGdxGame.PPM);

        fdef.filter.categoryBits = MyGdxGame.SPIKES_BIT;
        fdef.filter.maskBits = MyGdxGame.DEFAULT_BIT;
        fdef.shape = shape;



        b2body.createFixture(fdef);

    }

    public void update (float dt) {
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(spinAnimation.getKeyFrame(stateTime, true));
    }
}
