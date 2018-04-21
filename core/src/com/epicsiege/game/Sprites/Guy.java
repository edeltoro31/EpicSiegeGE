package com.epicsiege.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Screens.PlayScreen;

/**
 * Created by Joselito on 4/17/2018.
 */

public class Guy extends Sprite{

    //for the Avatar (to define what state he'll be in).
    public enum State {FALLING, JUMPING, STANDING, RUNNING, FIGHTING};
    public State currentState;
    public State previousState;


    public World world;
    public Body b2body;

    //for the Avatar (to define what sprite to use).
    private TextureRegion guyStand;
    private Animation<TextureRegion> guyRun;
    private Animation<TextureRegion> guyJump;
    private  Animation<TextureRegion> guyFight;
    private float stateTimer;
    private boolean runningRight;



    public Guy (World world, PlayScreen screen) {

        //sprite for the Avatar(Guy) default state.
        super(screen.getAtlas().findRegion("hero_standing"));

        this.world = world;

        //Avatars current state when not moving.
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        //This array holds the Avatar(Guy's) different positions.
        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Sets the running sprites for our Avatar(Guy)
        //the sprites sizes are all 49x49
        // the x and y coordinates tell where the sprite is located in the pack png.
        frames.add(new TextureRegion(getTexture(), 5 * 50, 0, 49, 49));
        guyRun = new Animation(0.1f, frames);
        frames.clear();


        //Sets the jumping sprites for our Avatar (Guy)
        for (int i = 6; i < 9; i++) {

            //The final state for the jump is located towards the front of the pack, hence the if statement
            //setting the last frame to an earlier location.
            if (i == 8)
                frames.add(new TextureRegion(getTexture(), 4 * 50, 0, 49, 49));
            else
                frames.add(new TextureRegion(getTexture(), i * 50, 0, 49, 49));
        }
        guyJump = new Animation(0.1f, frames);

        //Sets the fighting sprites for our Avatar (Guy)
        for (int i = 9; i < 13; i++)
            frames.add(new TextureRegion(getTexture(), i * 50, 0, 50, 50));
        guyFight = new Animation (0.1f, frames);


        defineGuy();

        //Sets the size of our Avatar(Guy) in relation to our Map or World.
        guyStand = new TextureRegion(getTexture(), 0, 0, 49, 49);
        setBounds(0, 0, 49 / (MyGdxGame.PPM + 30), 49 / (MyGdxGame.PPM + 30));
        setRegion(guyStand);
    }

    public void update (float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 4);

        setRegion(getFrame(dt));
    }

    //Defines what frame the Avatar (Guy) should take in game.
    public TextureRegion getFrame (float dt) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = guyJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = guyRun.getKeyFrame(stateTimer, true);
                break;
            case FIGHTING:
                region = guyFight.getKeyFrame(stateTimer);
                break;
            case FALLING:
            case STANDING:
            default:
                region = guyStand;
                break;
        }

        //Is he running left or right.
        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        }
        else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;

    }

    //Gets the state of our Avatar (Guy) based on what he is doing on the x and y coordinate Map.
    //If he's moving along the x axis he's running, if he's moving along the y axis he's jumping.
    public State getState() {
        if (b2body.getLinearVelocity().y > 0)
            return State.JUMPING;

        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;

        else if (b2body.getLinearVelocity().x != 0)
            return  State.RUNNING;

        else if (b2body.getLinearVelocity().y > 0 && b2body.getLinearVelocity().y < 1)
            return  State.FIGHTING;

        else
            return State.STANDING;
    }

    public void defineGuy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MyGdxGame.PPM, 32 / MyGdxGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        //Our collision detectors
        FixtureDef fdef = new FixtureDef();
        FixtureDef fdef1 = new FixtureDef();
        FixtureDef fdef2 = new FixtureDef();
        FixtureDef fdef3 = new FixtureDef();
        FixtureDef fdef4 = new FixtureDef();

        //Defines the physical components of our Avatar(Guy), our Guy is a sphere with a radius of 6.
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);

        //What our collision detectors can interact with.
        fdef.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef.filter.maskBits =  MyGdxGame.TOKEN_BIT | MyGdxGame.DEFAULT_BIT | MyGdxGame.SPIKES_BIT;

        fdef1.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef1.filter.maskBits =  MyGdxGame.TOKEN_BIT | MyGdxGame.SPIKES_BIT;
        fdef2.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef2.filter.maskBits =  MyGdxGame.TOKEN_BIT | MyGdxGame.SPIKES_BIT;
        fdef3.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef3.filter.maskBits = MyGdxGame.TOKEN_BIT | MyGdxGame.SPIKES_BIT;
        fdef4.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef4.filter.maskBits = MyGdxGame.TOKEN_BIT | MyGdxGame.SPIKES_BIT;


        fdef.shape = shape;


        b2body.createFixture(fdef);

        //collision detector
        //sensor in our Guy's body
        EdgeShape bodyf = new EdgeShape(); //front
        EdgeShape bodyb = new EdgeShape(); //back
        EdgeShape bodyt = new EdgeShape(); //top
        EdgeShape bodybt = new EdgeShape(); //bottom
        //There is a sensor in front, behind, on top, and below our Avatar(Guy)
        bodyf.set(new Vector2(12 / MyGdxGame.PPM, 12 / MyGdxGame.PPM), new Vector2(12 / MyGdxGame.PPM, -12 / MyGdxGame.PPM));
        bodyb.set(new Vector2(-12 / MyGdxGame.PPM, 12 / MyGdxGame.PPM), new Vector2(-12 / MyGdxGame.PPM, -12 / MyGdxGame.PPM));
        bodyt.set(new Vector2(12 / MyGdxGame.PPM, 12 / MyGdxGame.PPM), new Vector2(-12 / MyGdxGame.PPM, 12 / MyGdxGame.PPM));
        bodybt.set(new Vector2(-12 / MyGdxGame.PPM, -12 / MyGdxGame.PPM), new Vector2(12 / MyGdxGame.PPM, -12 / MyGdxGame.PPM));
        fdef1.shape = bodyf;
        fdef2.shape = bodyb;
        fdef3.shape = bodyt;
        fdef4.shape = bodybt;

        fdef1.isSensor = true;
        fdef2.isSensor = true;
        fdef3.isSensor = true;
        fdef4.isSensor = true;

        b2body.createFixture(fdef1).setUserData("body");
        b2body.createFixture(fdef2).setUserData("body");
        b2body.createFixture(fdef3).setUserData("body");
        b2body.createFixture(fdef4).setUserData("body");
    }

}
