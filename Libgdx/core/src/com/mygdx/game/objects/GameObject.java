package com.mygdx.game.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.mygdx.game.Tools.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Script.AddBulletSpeed;

public final class GameObject extends Sprite {
	public String name = "";
	
	private Body body;
	private ArrayList<Body> slavebody;

	private ArrayList<Fixture> fixtures;

	private BodyDef bdef;
	private ArrayList<FixtureDef> fdefs;

	private HashMap<Fixture, FixtureDef> fixturetofdefs;
	private HashMap<Fixture, Body> fixturetoslavebody;

	
    private Vector2 memposition;
    private Vector2 memscale;
    private float memangle;
	
	private ArrayList<GameScript> scriptlist; 
	private ArrayList<GameScript> scriptStartlist;

    private GameObject parent;
    private ArrayList<GameObject> childred;
    
    private Vector2 spritecenter;
    
    private void setattr(String name)
    {
    	this.name = name;
        body = MyGdxGame.instance().getScreen().getWorld().createBody(bdef);
        body.setUserData(this);
    	fixtures = new ArrayList<>();
    	fixturetofdefs = new HashMap<>();
        if(fdefs == null)
        {
        	fdefs = new ArrayList<>();
        }
        else if(fdefs.size() > 0)
        {
        	for (FixtureDef fdef : fdefs) {
        		Fixture fixture = body.createFixture(fdef);
            	fixture.setUserData(this);
            	if (!fixtures.contains(fixture)) fixtures.add(fixture);
            	if (!fixturetofdefs.containsKey(fixture))fixturetofdefs.put(fixture, fdef);
			}
        }
        spritecenter = new Vector2();
        memposition = new Vector2(getPosition());
        memscale = new Vector2(getScale());
        memangle = getAngle();
        slavebody = new ArrayList<>();
        fixturetoslavebody = new HashMap<Fixture, Body>();
        scriptlist = new ArrayList<GameScript>();
        scriptStartlist = new ArrayList<GameScript>();
        childred = new ArrayList<GameObject>();
        MyGdxGame.instance().getScreen().addGameObject(this);
    }
    
    public GameObject(String name, BodyDef bdef)
    {
    	this.bdef = bdef;
    	setattr(name);
    }
    
    public GameObject(String name, GameObject gameObject)
    {
    	super(gameObject);
    	bdef = gameObject.bdef;
    	fdefs = new ArrayList<>(gameObject.fdefs);
    	setattr(name);
    	
    	parent = null;
    	childred.clear();
    	scriptlist.clear();
    	scriptStartlist.clear();
    }
    
    public GameObject(String name, BodyDef bdef, Texture texture) 
    {
    	super(texture);
    	this.bdef = bdef;
    	setattr(name);
    }
    
    public GameObject(String name, BodyDef bdef, Texture texture, int srcWidth, int srcHeight) 
    {
    	super(texture, srcWidth, srcHeight);
    	this.bdef = bdef;
    	setattr(name);
	}
    
    public GameObject(String name, BodyDef bdef, Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) 
    {
    	super(texture, srcX, srcY, srcWidth, srcHeight);
    	this.bdef = bdef;
    	setattr(name);
	}
    
    public GameObject(String name, BodyDef bdef, TextureRegion region) 
    {
    	super(region);
    	this.bdef = bdef;
    	setattr(name);
	}

    public GameObject(String name, BodyDef bdef, TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) 
    {
    	super(region, srcX, srcY, srcWidth, srcHeight);
    	this.bdef = bdef;
    	setattr(name);
	}


    public Fixture addFixture(FixtureDef fdef) 
    {
    	if (!fdefs.contains(fdef)) fdefs.add(fdef);
    	Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    	if (!fixtures.contains(fixture)) fixtures.add(fixture);
    	if (!fixturetofdefs.containsKey(fixture))fixturetofdefs.put(fixture, fdef);
    	return fixture;
	}
    
    public Fixture addFixture(FixtureDef fdef, int slavebodyindex) 
    {
    	if (!fdefs.contains(fdef)) fdefs.add(fdef);
    	Fixture fixture = slavebody.get(slavebodyindex).createFixture(fdef);
        fixture.setUserData(this);
    	if (!fixtures.contains(fixture)) fixtures.add(fixture);
    	if (!fixturetofdefs.containsKey(fixture))fixturetofdefs.put(fixture, fdef);
    	if (!fixturetoslavebody.containsKey(fixture))fixturetoslavebody.put(fixture, slavebody.get(slavebodyindex));
    	return fixture;
	}

    public Fixture addFixture(FixtureDef fdef, Body aslavebody) 
    {
    	if(slavebody.contains(aslavebody))
    	{
			if (!fdefs.contains(fdef)) fdefs.add(fdef);
			Fixture fixture = aslavebody.createFixture(fdef);
		    fixture.setUserData(this);
			if (!fixtures.contains(fixture)) fixtures.add(fixture);
			if (!fixturetofdefs.containsKey(fixture))fixturetofdefs.put(fixture, fdef);
			if (!fixturetoslavebody.containsKey(fixture))fixturetoslavebody.put(fixture, aslavebody);
			return fixture;
    	}
    	return null;
	}
    
    public void destoryFixture(int index) 
    {
    	destoryFixture(fixtures.get(index));
    }

    public void destoryFixture(Fixture fixture) 
    {
    	FixtureDef fdef = fixturetofdefs.get(fixture);
    	
    	if(fixturetoslavebody.containsKey(fixture))
    	{
    		fixturetoslavebody.get(fixture).destroyFixture(fixture);
    		fixturetoslavebody.remove(fixture);
    	}
    	else 
    	{
        	body.destroyFixture(fixture);
		}
	    if(fdefs.contains(fdef)) fdefs.remove(fdef);
	    if(fixtures.contains(fixture)) fixtures.remove(fixture);
	    if(fixturetofdefs.containsKey(fixture)) fixturetofdefs.remove(fixture);
	}
    
    public Fixture getFixture(int index) 
    {
        return fixtures.get(index);
	}

    public int getFixtureSize() 
    {
        return fixtures.size();
	}
    
    public Body addSlaveBody(BodyDef bdef) 
    {
    	//if (!fdefs.contains(fdef)) fdefs.add(fdef);
    	Body body = MyGdxGame.instance().getScreen().getWorld().createBody(bdef);
        body.setUserData(this);
        body.setTransform(getPosition(), getAngle());
    	if (!slavebody.contains(body)) slavebody.add(body);
    	//if (!fixturetofdefs.containsKey(fixture))fixturetofdefs.put(fixture, fdef);
    	return body;
	}
    
    public void destorySlaveBody(int index) 
    {
    	destorySlaveBody(slavebody.get(index));
    }

    public void destorySlaveBody(Body body) 
    {
	    if(slavebody.contains(body))
	    {
	    	slavebody.remove(body);
	    	MyGdxGame.instance().getScreen().getWorld().destroyBody(body);
	    }
    }
    
    public Body getSlaveBody(int index) 
    {
        return slavebody.get(index);
	}

    public int getSlaveBodySize() 
    {
        return slavebody.size();
	}
    
    public Body getBody() {
		return body;
	}
    
    public void setParent(GameObject parent) 
    {
    	if(this.parent != null)
    	{
    		if(this.parent.childred.contains(this)) this.parent.childred.remove(this);
    	}
    	if(parent != null)
    	{
    		if(!parent.childred.contains(this)) parent.childred.add(this);
    	}
    	this.parent = parent;
	}
        
    public GameObject getParent() 
    {
    	return parent;
	}

    public GameObject getChild(int index) 
    {
    	return childred.get(index);
	}
    
    public GameObject getChild(String name) {
    	for (GameObject gameObject : new ArrayList<>(childred)) {
			if(gameObject.name.equals(name)) return gameObject;
		}
    	return null;
	}

    public GameObject[] getChilds(String name) {
    	ArrayList<GameObject> scripts = new ArrayList<>();
    	for (GameObject gameObject : new ArrayList<>(childred)) {
    		if(gameObject.name.equals(name)) scripts.add(gameObject);
		}
    	GameObject[] ans = new GameObject[scripts.size()];
    	return scripts.toArray(ans);
	}
    
    public int getChildsize() 
    {
    	return childred.size();
	}
        
    private void updateChildPosition()
    {
    	Vector2 distance = new Vector2(getPosition().x - memposition.x, getPosition().y - memposition.y);
    	for (GameObject child : new ArrayList<>(childred)) {
    		child.setPosition(new Vector2(child.getPosition().x + distance.x, child.getPosition().y + distance.y));
		}
    	memposition = new Vector2(getPosition());
	}
    
    public Vector2 getLocalPosition()
    {
    	if(parent == null)
    	{
    		return body.getPosition();
    	}
    	else {
			return parent.getBody().getLocalPoint(body.getPosition());
		}
    }
    
    public void setLocalPosition(Vector2 localPosition)
    {
    	if(parent == null)
    	{
    		body.setTransform(localPosition, body.getAngle());
    	}
    	else {
			body.setTransform(parent.getBody().getWorldPoint(localPosition), body.getAngle());
		}
    	updateChildPosition();
    }
        
    public Vector2 getPosition()
    {
    	return new Vector2(body.getPosition());
    }
    
    public void setPosition(Vector2 position)
    {
    	setPosition(position.x, position.y); 
    }
    
    public void setPosition(float x, float y)
    {
    	body.setTransform(new Vector2(x, y), body.getAngle());
    	updateChildPosition();
    }
    
    public void updateSpritePosition()
    {
    	super.setPosition(
    			(getPosition().x - MyGdxGame.instance().getScreen().getCamera().position.x) * MyGdxGame.PPM * MyGdxGame.WindowsProportion + Gdx.graphics.getWidth() / 2 - getWidth() / 2 + spritecenter.x * (1 - getLocalScale().x) * (parent != null ? parent.getScale().x : 1), 
    			(getPosition().y - MyGdxGame.instance().getScreen().getCamera().position.y) * MyGdxGame.PPM * MyGdxGame.WindowsProportion + Gdx.graphics.getHeight() / 2 - getHeight() / 2 + spritecenter.y * (1 - getLocalScale().x) * (parent != null ? parent.getScale().y : 1));
    }

    public Vector2 getSpriteCenter()
    {
    	return new Vector2(spritecenter);
    }
    
    public void setSpriteCenter(Vector2 center)
    {
    	spritecenter = new Vector2(center);
    }

    public Vector2 getWorldPosition()
    {
    	return new Vector2(body.getPosition());
    }
    
    public void setWorldPosition(Vector2 worldPosition)
    {
    	setPosition(worldPosition); 
    }
    
    private void updateChildScale()
    {
    	Vector2 magnification = new Vector2(getScale().x / memscale.x, getScale().y / memscale.y);
    	for (GameObject child : new ArrayList<>(childred)) {
    		child.setScale(new Vector2(child.getScale().x * magnification.x, child.getScale().y * magnification.y));
		}
    	memscale = new Vector2(getScale());
	}
    
    public Vector2 getLocalScale() 
    {
    	if(parent == null)
    	{
    		return new Vector2(getScaleX(), getScaleY());
    	}
    	else {
			return new Vector2(getScaleX() / parent.getScaleX(), getScaleY() / parent.getScaleY());
		}
	}
    
    public void setLocalScale(Vector2 localScale)
    {
    	if(parent == null)
    	{
    		setScale(localScale.x, localScale.y);
    	}
    	else {
    		setScale(localScale.x * parent.getScaleX(), localScale.y * parent.getScaleY());
		}
    	updateChildScale();
    }
    
    public Vector2 getScale() 
    {
    	return new Vector2(getScaleX(), getScaleY());
	}
    
    public void setScale(Vector2 scale) 
    {
    	super.setScale(scale.x, scale.y);
    	updateChildScale();
	}

    public Vector2 getWorldScale() 
    {
    	return new Vector2(getScaleX(), getScaleY());
	}
    
    public void setWorldScale(Vector2 worldScale) 
    {
    	super.setScale(worldScale.x, worldScale.y);
    	updateChildScale();
	}
    
    private void updateChildAngle()
    {
    	float AngleDistance = getAngle() - memangle;

    	for (GameObject child : new ArrayList<>(childred)) {
    		Vector2 childmemlocalpostition = new Vector2(new Vector2(child.memposition).sub(memposition)).rotateDeg(-memangle);
    		
    		Vector2 childnewpostition = childmemlocalpostition.rotateDeg(getAngle()).add(new Vector2(child.getPosition()).sub(child.memposition)).sub(new Vector2(getPosition()).sub(memposition)).add(getPosition());
    		child.setPosition(childnewpostition);
    		child.setAngle(child.getAngle() + AngleDistance);
		}
    	memangle = getAngle();
	}
    
    public float getLocalAngle()
    {
    	if(parent == null)
    	{
    		return MathTool.radiansTodegrees(body.getAngle());
    	}
    	else {
			return MathTool.radiansTodegrees(body.getAngle() - parent.body.getAngle());
		}
	}
    
    public void setLocalAngle(float localAngle)
    {
    	localAngle = MathTool.degreesToradians(localAngle);
    	if(parent == null)
    	{
    		body.setTransform(getPosition(), localAngle);
    	}
    	else {
    		body.setTransform(getPosition(), localAngle + parent.body.getAngle());
		}
		setRotation(MathTool.radiansTodegrees(body.getAngle()));
    	updateChildAngle();
    }
    
    public float getAngle()
    {
    	return MathTool.radiansTodegrees(body.getAngle());
	}
    
    public void setAngle(float angle) 
    {
    	angle = MathTool.degreesToradians(angle);
		body.setTransform(getPosition(), angle);
		setRotation(MathTool.radiansTodegrees(body.getAngle()));
    	updateChildAngle();
	}

    public float getWorldAngle() 
    {
    	return body.getAngle();
	}
    
    public void setWorldAngle(float worldAngle) 
    {
    	worldAngle = MathTool.degreesToradians(worldAngle);
		body.setTransform(getPosition(), worldAngle);
		setRotation(MathTool.radiansTodegrees(body.getAngle()));
    	updateChildAngle();
	}
    
    public void addscript(GameScript script) {
    	script.init(this);
		if(!scriptStartlist.contains(script)) scriptStartlist.add(script);
		script.Awake();
	}

    public void removescript(GameScript script) {
    	script.OnRemove();
		if(scriptlist.contains(script)) scriptlist.remove(script);
		if(scriptStartlist.contains(script)) scriptStartlist.remove(script);
	}
    
    public GameScript getscript(int index) {
    	if(index >= scriptlist.size()) return scriptStartlist.get(index - scriptlist.size());
    	return scriptlist.get(index);
	}
    
    public GameScript getscript(String scriptname) {
    	for (GameScript gameScript : new ArrayList<>(scriptlist)) {
			if(gameScript.getClass().getSimpleName().equals(scriptname)) return gameScript;
		}
    	for (GameScript gameScript : new ArrayList<>(scriptStartlist)) {
			if(gameScript.getClass().getSimpleName().equals(scriptname)) return gameScript;
		}
    	return null;
	}

    public GameScript[] getscripts(String scriptname) {
    	ArrayList<GameScript> scripts = new ArrayList<>();
    	for (GameScript gameScript : new ArrayList<>(scriptlist)) {
			if(gameScript.getClass().getSimpleName().equals(scriptname)) scripts.add(gameScript);
		}
    	for (GameScript gameScript : new ArrayList<>(scriptStartlist)) {
			if(gameScript.getClass().getSimpleName().equals(scriptname)) scripts.add(gameScript);
		}
    	GameScript[] ans = new GameScript[scripts.size()];
    	return scripts.toArray(ans);
	}

    public int getscriptSize() {
    	return scriptlist.size() + scriptStartlist.size();
	}
    
    public void Update() 
    {
    	for (GameScript gameScript : new ArrayList<>(scriptStartlist)) {
			scriptStartlist.remove(gameScript);
			scriptlist.add(gameScript);
			gameScript.Start();
		}

    	for (GameScript gameScript : new ArrayList<>(scriptlist)) {
			gameScript.Update();
		}
	}
    
    public void OnRender(SpriteBatch batch)
    {
    	for (GameScript gameScript : new ArrayList<>(scriptlist)) {
			gameScript.OnRender(batch);
		}
    }
    
    public void lateUpdate()
    {
    	for (GameScript gameScript : new ArrayList<>(scriptlist)) {
			gameScript.lateUpdate();
		}
    	if(getAngle() - memangle != 0)
    	{
    		updateChildAngle();
    	}
    	if(getPosition().x - memposition.x != 0 || getPosition().y - memposition.y != 0)
    	{
    		updateChildPosition();
    	}
    	if(getScale().x / memscale.x != 1 || getScale().y / memscale.y != 1)
    	{
    		updateChildScale();
    	}
    	for (Body nowslavebody : slavebody) {
			nowslavebody.setTransform(getPosition(), getAngle());
		}
    }
    
    public void Destroy()
    {
    	if (MyGdxGame.instance().getScreen().DestoryGameObjectCheck(this))
    	{
	    	MyGdxGame.instance().getScreen().addDestoryGameObject(this);
	    	for (GameObject gameObject : new ArrayList<>(childred)) {
				gameObject.Destroy();
			}
    	}
    }
    
    public void OnDestroy(GameObject target) {
		for (GameScript script : new ArrayList<>(scriptlist)) {
			script.OnDestory();
		}
		scriptlist.clear();
	}
    
    public void OnBeginContact(Fixture me, Fixture target) {
		for (GameScript script : new ArrayList<>(scriptlist)) {
			script.OnBeginContact(me, target);
		}
	}
    public void OnEndContact(Fixture me, Fixture target) {
		for (GameScript script : new ArrayList<>(scriptlist)) {
			script.OnEndContact(me, target);
		}
	}
}

