package com.epicsiege.game.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.epicsiege.game.Sprites.InteractiveTileObjects;


/**
 * Created by Joselito on 4/18/2018.
 */

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "body" || fixB.getUserData() == "body") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObjects) {
                ((InteractiveTileObjects) object.getUserData()).onBodyHit();
            }

        }

    }

    @Override
    public void endContact (Contact contact) {

    }

    @Override
    public void preSolve (Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve (Contact contact, ContactImpulse impulse) {

    }
}
