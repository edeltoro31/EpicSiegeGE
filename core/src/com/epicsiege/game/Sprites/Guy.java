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

    //for the avatar.
    public enum State {FALLING, JUMPING, STANDING, RUNNING, FIGHTING};
    public State currentState;
    public State previousState;


    public World world;
    public Body b2body;

    //for the avatar.
    private TextureRegion guyStand;
    private Animation<TextureRegion> guyRun;
    private Animation<TextureRegion> guyJump;
    private  Animation<TextureRegion> guyFight;
    private float stateTimer;
    private boolean runningRight;



    public Guy (World world, PlayScreen screen) {

        super(screen.getAtlas().findRegion("hero_standing"));

        this.world = world;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();

        //Running
        frames.add(new TextureRegion(getTexture(), 5 * 50, 0, 49, 49));
        guyRun = new Animation(0.1f, frames);
        frames.clear();


        //jumping
        for (int i = 6; i < 9; i++) {

            if (i == 8)
                frames.add(new TextureRegion(getTexture(), 4 * 50, 0, 49, 49));
            else
                frames.add(new TextureRegion(getTexture(), i * 50, 0, 49, 49));
        }
        guyJump = new Animation(0.1f, frames);

        //fighting
        for (int i = 9; i < 13; i++)
            frames.add(new TextureRegion(getTexture(), i * 50, 0, 50, 50));
        guyFight = new Animation (0.1f, frames);
        //frames.clear();

        defineGuy();


        guyStand = new TextureRegion(getTexture(), 0, 0, 49, 49);
        setBounds(0, 0, 49 / (MyGdxGame.PPM + 20), 49 / (MyGdxGame.PPM + 20));
        setRegion(guyStand);
    }

    public void update (float dt) {
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 4);

        setRegion(getFrame(dt));
    }

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

        FixtureDef fdef = new FixtureDef();
        FixtureDef fdef2 = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MyGdxGame.PPM);
        fdef.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef.filter.maskBits = MyGdxGame.DEFAULT_BIT | MyGdxGame.TOKEN_BIT;
        fdef2.filter.categoryBits = MyGdxGame.GUY_BIT;
        fdef2.filter.maskBits = MyGdxGame.DEFAULT_BIT | MyGdxGame.TOKEN_BIT;

        fdef.shape = shape;
        fdef2.shape = shape;
        b2body.createFixture(fdef);

        //collision detector
        //sensor in our Guy's body
        EdgeShape bodyf = new EdgeShape();
        EdgeShape bodyb = new EdgeShape();
        //sensor is at the center.
        bodyf.set(new Vector2(6 / MyGdxGame.PPM, 6/ MyGdxGame.PPM), new Vector2(6 / MyGdxGame.PPM, 0/ MyGdxGame.PPM));
        bodyb.set(new Vector2(-6 / MyGdxGame.PPM, 6/ MyGdxGame.PPM), new Vector2(-6 / MyGdxGame.PPM, 0/ MyGdxGame.PPM));
        fdef.shape = bodyf;
        fdef2.shape = bodyb;
        fdef.isSensor = true;
        fdef2.isSensor = true;

        b2body.createFixture(fdef).setUserData("body");
        b2body.createFixture(fdef2).setUserData("body");
    }

}
