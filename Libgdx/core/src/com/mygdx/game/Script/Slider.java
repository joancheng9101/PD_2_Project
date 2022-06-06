package com.mygdx.game.Script;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScript;

public class Slider extends GameScript {
	GameObject line;
	
	public float slide = 1;
	
	public void Awake() {
	    BodyDef bdef = new BodyDef();
	    bdef.type=BodyDef.BodyType.StaticBody;

	    line = new GameObject("line", bdef, new Texture("assets/rect.png"));
	    line.setParent(getGameObject());
	    line.setScale(new Vector2(slide,1));
	    line.setPosition(new Vector2(0, 0));
	    line.setSpriteCenter(new Vector2(-line.getWidth() / 2, 0));
	}
	
	public void Start() {
	}
	public void setlinecolor(Color color)
	{
		line.setColor(color);
	}
	
	public Vector2 getlineSpriteCenter()
	{
		return new Vector2(line.getSpriteCenter());
	}
	
	public void Update() 
	{
		if (slide < 0)
		{
			slide = 0;
		}
		line.setLocalScale(new Vector2(slide,1));
	}
	
	public void lateUpdate() {
        
	}
}
