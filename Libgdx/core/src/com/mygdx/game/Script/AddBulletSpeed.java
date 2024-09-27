package com.mygdx.game.Script;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScript;

public class AddBulletSpeed extends GameScript {
	public void Start() {
		getBody().setGravityScale(0);
	}
	
	public void Update() {
	}
	
	public void lateUpdate() {
        
	}
	
    @Override
    public void OnBeginContact(Fixture me, Fixture target)
    {
    	GameObject targetobject = (GameObject)target.getUserData();
    	if (targetobject.getParent() != null && targetobject.getParent().name.equals("mainground"))
    	{
    		//System.out.println("aaa");
    		getGameObject().Destroy();
    	}
    	else if(targetobject.getscript("Player") != null) {
    		getGameObject().Destroy();
		}
	}
}
