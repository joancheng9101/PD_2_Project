package com.mygdx.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class GameScript {
	private GameObject gameObject;
	public void init(GameObject gameObject) 
	{
		if (this.gameObject == null)
			this.gameObject = gameObject;
	}
	
	public GameObject getGameObject() {
		return gameObject;
	}
	
    public Body getBody() {
		return gameObject.getBody();
	}
	
	public void Awake()
	{
		
	}

	public void Start()
	{
		
	}
	
    public void Update() 
    {
    	
	}
    
    public void OnRender(SpriteBatch batch) 
    {
    	
	}

    public void lateUpdate()
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
