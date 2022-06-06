package com.mygdx.game.Script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScreen;
import com.mygdx.game.objects.GameScript;

public class Player extends GameScript 
{
	private final GameScreen nowScreen = MyGdxGame.instance().getScreen();
	private Vector2 velocity = new Vector2();
	
	public int[] keys = new int[] { Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT, Input.Keys.LEFT };
	public boolean allowfire = false;
	
	public Slider lifeline;
	
	public final float MAXlife = 100;
	public float life = MAXlife;
	
	public final float bulletCDMAX = 0.2f;
	public float bulletCD = 0;
	
	public final float speeduptimeMAX = 2.5f;
	public float speeduptime = 0;
	
	public final float bulletSpeed = 10;
	
	@Override
	public void Start()
	{
		getBody().setGravityScale(0);
		
	    BodyDef bdef = new BodyDef();
	    bdef.type=BodyDef.BodyType.StaticBody;

	    GameObject lifeline = new GameObject("lifeline", bdef);
	    lifeline.setParent(getGameObject());
	    
	    this.lifeline = new Slider();
	    lifeline.addscript(this.lifeline);
	    this.lifeline.setlinecolor(new Color(0, 1, 0, 1));
	    
	    lifeline.setLocalPosition(new Vector2(0, 0.3f));
	}
	
	@Override
	public void Update()
	{
		bulletCD -= nowScreen.getdeltaTime();
		if(speeduptime >= 0)
		{
			speeduptime -= nowScreen.getdeltaTime();
		}
		lifeline.slide = life/MAXlife;
		
		if (((PlayScreen)nowScreen).isend == 0)
			handleInput();
		getBody().setLinearVelocity(new Vector2(10 + velocity.x , velocity.y));
	}
	
    public void handleInput() {
        if(Gdx.input.isKeyPressed(keys[0]))
        {
        	velocity = new Vector2(velocity.x, 2);
        }
        else if (Gdx.input.isKeyPressed(keys[1])) {
        	velocity = new Vector2(velocity.x, -2);
		}
        else {
        	velocity = new Vector2(velocity.x, 0);
		}
        if(Gdx.input.isKeyPressed(keys[2]))
        {
        	velocity = new Vector2(2, velocity.y);
        }
        else if(Gdx.input.isKeyPressed(keys[3]))
        {
        	velocity = new Vector2(-2, velocity.y);
        }
        else {
        	velocity = new Vector2(0, velocity.y);
		}
        
        if(allowfire && Gdx.input.isKeyPressed(Input.Keys.SPACE) && bulletCD <= 0)
        {
		    makbullete(new Vector2(getGameObject().getPosition().x, getGameObject().getPosition().y + 0.25f));
		    makbullete(new Vector2(getGameObject().getPosition().x, getGameObject().getPosition().y - 0.25f));
		    bulletCD = bulletCDMAX / (speeduptime > 0 ? 3 : 1);
        }
    }
    
    private void makbullete(Vector2 position) 
    {
	    BodyDef bdef = new BodyDef();
	    bdef.type=BodyDef.BodyType.DynamicBody;

	    GameObject bullet = new GameObject("bullet", bdef, new Texture("assets/rect.png")); 
	    bullet.setColor(1, 0, 0, 1);
	    bullet.setPosition(position);
	    bullet.setScale(new Vector2(0.25f, 0.25f));;
	    FixtureDef fdef =new FixtureDef(); 
	    fdef.isSensor = true;
	    PolygonShape shape =new PolygonShape();
	    shape.setAsBox(0.06f, 0.01f);
	    fdef.shape =shape;
	    bullet.addFixture(fdef);
	    bullet.addscript(new Bullet());
	    ((Bullet)bullet.getscript("Bullet")).owner = getGameObject().name;
	    bullet.getBody().setLinearVelocity(new Vector2(10 + bulletSpeed, 0));
	}
    
    @Override
    public void lateUpdate()
    {
    }
    
    @Override
    public void OnBeginContact(Fixture me, Fixture target) {
    	GameObject targetobject = (GameObject)target.getUserData();
    	if(targetobject.getscript("Bullet") != null && !((Bullet)targetobject.getscript("Bullet")).owner.equals(getGameObject().name))
    	{
    		life--;
    	}
    	else if (targetobject.name == "leftground") 
    	{
			life = 0;
		}
    	else if (targetobject.name == "addBulletSpeed") 
    	{
    		if(speeduptime < 0) speeduptime = 0;
			speeduptime += speeduptimeMAX;
		}
	}
}
