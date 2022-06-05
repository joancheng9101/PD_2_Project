package com.mygdx.game.Script;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.objects.GameScreen;
import com.mygdx.game.objects.GameScript;

public class Ground extends GameScript 
{
	GameScreen nowScreen = MyGdxGame.instance().getScreen();
	
	public void Start() {
		
	}
	
	public void Update() {
		getBody().setLinearVelocity(new Vector2(10 ,getBody().getLinearVelocity().y));
	}
	
	public void lateUpdate() {
        nowScreen.getCamera().position.x =getBody().getPosition().x;
	}
}
