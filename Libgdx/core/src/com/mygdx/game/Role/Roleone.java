package com.mygdx.game.Role;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;

public class Roleone extends Sprite {
    public World world;
    public Body b2body;
    public Roleone(World world){
        this.world=world;
        defineRoleone();
    }
    public void defineRoleone(){
        BodyDef bdef =new BodyDef();
        bdef.position.set(32/ MyGdxGame.PPM, 32/MyGdxGame.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body =world.createBody(bdef);
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setRadius(20/MyGdxGame.PPM);
        fdef.shape =shape;
        b2body.createFixture(fdef);
    }
}
