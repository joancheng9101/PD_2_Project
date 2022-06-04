package com.mygdx.game.objects;

import com.badlogic.gdx.physics.box2d.Body;

public abstract class GameScript {
	private GameObject _gameObject;
	public void init(GameObject gameObject) 
	{
		if (_gameObject == null)
			_gameObject = gameObject;
		Start();
	}
	
	public GameObject getGameObject() {
		return _gameObject;
	}
	
    public Body getBody() {
		return _gameObject.getBody();
	}
	
	public void Start()
	{
		
	}
	
    public void Update() 
    {
    	
	}
}
