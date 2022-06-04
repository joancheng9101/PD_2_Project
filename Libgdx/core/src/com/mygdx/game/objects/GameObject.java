package com.mygdx.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.mygdx.game.MyGdxGame;

public final class GameObject extends Sprite {
	private Body body;
    
    private ArrayList<GameScript> scriptlist;
    
    public GameObject(BodyDef bdef, FixtureDef fdef)
    {
        body = MyGdxGame.instance().getScreen().getWorld().createBody(bdef);
        body.createFixture(fdef);
        MyGdxGame.instance().getScreen().addGameObject(this);
        scriptlist = new ArrayList<GameScript>();
    }
    
    public Body getBody() {
		return body;
	}
    
    
    public void addscript(GameScript script) {
    	script.init(this);
		scriptlist.add(script);
	}
    
    public void Update() 
    {
    	for (GameScript gameScript : scriptlist) {
			gameScript.Update();
		}
	}
}

