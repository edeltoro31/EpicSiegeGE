package com.epicsiege.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.epicsiege.game.MyGdxGame;
import com.epicsiege.game.Sprites.Guy;
import com.epicsiege.game.Sprites.InteractiveTileObjects;
import com.epicsiege.game.Sprites.Spikes;


/**
 * Created by Joselito on 4/18/2018.
 */

public class WorldContactListener implements ContactListener{

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;
    /*
        if(fixA.getUserData() == "body" || fixB.getUserData() == "body") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if (object.getUserData() instanceof InteractiveTileObjects) {
                ((InteractiveTileObjects) object.getUserData()).onBodyHit();
            }

        }
    */
        if(fixA.getUserData() == "body" || fixB.getUserData() == "body") {
            Fixture body = fixA.getUserData() == "body" ? fixA : fixB;
            Fixture object = body == fixA ? fixB : fixA;

            if (object.getUserData() != null && InteractiveTileObjects.class.isAssignableFrom(object.getUserData().getClass())) {
                ((InteractiveTileObjects) object.getUserData()).onBodyHit();
            }

        }

        switch (cDef) {
            case MyGdxGame.SPIKES_BIT | MyGdxGame.DEFAULT_BIT:

                Spikes.reverseVelocity(true, false);

                break;

            case MyGdxGame.SPIKES_BIT | MyGdxGame.GUY_BIT:
                Gdx.app.log("Guy", "Dead");
                Guy.hit(true, -2f, 4f);

                break;
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
