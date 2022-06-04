package com.mygdx.game.Script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.objects.GameScript;

public class Player extends GameScript 
{
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
        	getBody().setLinearVelocity(new Vector2(getBody().getLinearVelocity().x, 2f));
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        	getBody().setLinearVelocity(new Vector2(getBody().getLinearVelocity().x, -2f));
		}
        else {
        	getBody().setLinearVelocity(new Vector2(getBody().getLinearVelocity().x, 0));
		}
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
        	getBody().setLinearVelocity(new Vector2(2 ,getBody().getLinearVelocity().y));
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
        	getBody().setLinearVelocity(new Vector2(-2 ,getBody().getLinearVelocity().y));
        }
        else {
        	getBody().setLinearVelocity(new Vector2(0 ,getBody().getLinearVelocity().y));
		}
        MyGdxGame.instance().getScreen().getCamera().position.x =getBody().getPosition().x;
    }
}
