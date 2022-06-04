package com.mygdx.game.Script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.objects.GameScreen;
import com.mygdx.game.objects.GameScript;

public class Player extends GameScript 
{
	float speed = 133;
	GameScreen nowScreen = MyGdxGame.instance().getScreen();
	
	
	@Override
	public void Start()
	{
		getBody().setGravityScale(0);
	}
	
	@Override
	public void Update()
	{
		handleInput();
	}
	
    public void handleInput() {
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {
        	getBody().setLinearVelocity(new Vector2(getBody().getLinearVelocity().x, 133 * nowScreen.getdeltaTime()));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        	getBody().setLinearVelocity(new Vector2(getBody().getLinearVelocity().x, -133 * nowScreen.getdeltaTime()));
		}
        else {
        	getBody().setLinearVelocity(new Vector2(getBody().getLinearVelocity().x, 0));
		}
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
        	getBody().setLinearVelocity(new Vector2(133 * nowScreen.getdeltaTime() ,getBody().getLinearVelocity().y));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
        	getBody().setLinearVelocity(new Vector2(-133 * nowScreen.getdeltaTime() ,getBody().getLinearVelocity().y));
        }
        else {
        	getBody().setLinearVelocity(new Vector2(0 ,getBody().getLinearVelocity().y));
		}
        nowScreen.getCamera().position.x =getBody().getPosition().x;
    }
    
    @Override
    public void OnBeginContact(Fixture me, Fixture target) {
    	
	}
}
