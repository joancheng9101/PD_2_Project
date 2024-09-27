package com.mygdx.game.Script;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScript;

public class Rock extends GameScript
{
	//public float speed = 0;
	public void Start() {
		getBody().setGravityScale(0);
	}
	
	public void Update() {
	    //getBody().setLinearVelocity(new Vector2(speed, 0));
	    //System.out.println(getBody().getLinearVelocity());
		
	}
	
	public void lateUpdate() {
        
	}
	
    @Override
    public void OnBeginContact(Fixture me, Fixture target) 
    {
    	GameObject targetobject = (GameObject)target.getUserData();
    	if (targetobject.name.equals("leftground"))
    	{
    		getGameObject().Destroy();
    	}
	}
}
