package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class GameScript {
	private GameObject gameObject;
	public void init(GameObject gameObject) 
	{
		if (this.gameObject == null)
			this.gameObject = gameObject;
		Start();
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
    public Body getBody() {
		return gameObject.getBody();
	}
	
	public void Start()
	{
		
	}
	
    public void Update() 
    {
    	
	}
    
    public void OnBeginContact(Fixture me, Fixture target) {
    	
	}
    public void OnEndContact(Fixture me, Fixture target) {
		
	}
    
    
    public void OnRemove() {
		
	}
    public void OnDestory() {
		
	}
}
