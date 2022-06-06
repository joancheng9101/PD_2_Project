package com.mygdx.game.Script;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScreen;
import com.mygdx.game.objects.GameScript;

public class Ground extends GameScript 
{
	GameObject allObject;
	GameScreen nowScreen = MyGdxGame.instance().getScreen();
    float rockCD = 3;
    
	public void Start() {
	    BodyDef bdef = new BodyDef();
	    bdef.type=BodyDef.BodyType.StaticBody;
		allObject = new GameObject("allObject", bdef);
		allObject.setParent(getGameObject());
		allObject.setLocalPosition(new Vector2(0, 0));
	}
	
	public void Update() {
    	rockCD -= nowScreen.getdeltaTime();
    	if(rockCD <= 0)
    	{
    		makeRock();
    		rockCD = (float)((Math.random() * 5.1f) + 0.1f);
    	}
		
    	if ((int)(Math.random() * 501) >= 500)
    	{
    		makeAddBulletSpeed();
    	}
    	
		getBody().setLinearVelocity(new Vector2(10 ,getBody().getLinearVelocity().y));
	}
	
    
    public void makeRock() 
    {
	    BodyDef bdef = new BodyDef();
	    bdef.type=BodyDef.BodyType.KinematicBody;

	    Vector2 screensize = new Vector2(MyGdxGame.V_WIDTH / MyGdxGame.PPM, MyGdxGame.V_HEIGHT / MyGdxGame.PPM);
	    
	    Vector2 size = new Vector2((float)((Math.random() * (screensize.x / 10 + screensize.y / 10)) + screensize.y / 10), (float)((Math.random() * (screensize.y / 3 + screensize.y / 10)) + screensize.y / 10));
	    
	    GameObject rock = new GameObject("rock", bdef, new Texture("assets/rect.png"));
	    bdef.type = BodyDef.BodyType.DynamicBody;
	    Body slaveBody = rock.addSlaveBody(bdef);
	    rock.setSize(size.x * MyGdxGame.PPM * MyGdxGame.WindowsProportion, size.y * MyGdxGame.PPM * MyGdxGame.WindowsProportion);
	    rock.setColor(0, 0, 0, 1);
	    rock.setParent(allObject);
	    rock.setLocalPosition(new Vector2(2, (float)(Math.random() * (MyGdxGame.V_HEIGHT / MyGdxGame.PPM) - (MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2))));
	    rock.setParent(null);
	    FixtureDef fdef = new FixtureDef(); 
	    
	    fdef.filter.groupIndex = ((PlayScreen)nowScreen).grouprock;
        fdef.filter.categoryBits = ((PlayScreen)nowScreen).categoryprock;
        fdef.filter.maskBits = (short)~(((PlayScreen)nowScreen).categorypenemy);

	    PolygonShape shape =new PolygonShape();
	    shape.setAsBox(size.x / 2, size.y / 2);
	    fdef.shape =shape;
	    rock.addFixture(fdef);
	    fdef.isSensor = true;
	    rock.addFixture(fdef, slaveBody);
	    rock.addscript(new Rock());
	    rock.getBody().setLinearVelocity(new Vector2(10-(float)((Math.random() * 4f) + 1f), 0));
	}
	
    public void makeAddBulletSpeed()
    {
	    BodyDef bdef = new BodyDef();
	    bdef.type=BodyDef.BodyType.KinematicBody;

	    Vector2 size = new Vector2(0.2f,0.2f);
	    
	    GameObject addBulletSpeed = new GameObject("addBulletSpeed", bdef, new Texture("assets/speed.png"));
	    bdef.type = BodyDef.BodyType.DynamicBody;
	    Body slaveBody = addBulletSpeed.addSlaveBody(bdef);
	    addBulletSpeed.setSize(size.x * MyGdxGame.PPM * MyGdxGame.WindowsProportion, size.y * MyGdxGame.PPM * MyGdxGame.WindowsProportion);
	    addBulletSpeed.setColor(1, 1, 1, 1);
	    addBulletSpeed.setParent(allObject);
	    addBulletSpeed.setLocalPosition(new Vector2(1.8f, (float)(Math.random() * (MyGdxGame.V_HEIGHT / MyGdxGame.PPM) - (MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2))));
	    addBulletSpeed.setParent(null);
	    FixtureDef fdef = new FixtureDef(); 
	    fdef.isSensor = true;

	    PolygonShape shape =new PolygonShape();
	    shape.setAsBox(size.x / 2, size.y / 2);
	    fdef.shape =shape;
	    addBulletSpeed.addFixture(fdef);
	    fdef.isSensor = true;
	    addBulletSpeed.addFixture(fdef, slaveBody);
	    addBulletSpeed.addscript(new AddBulletSpeed());
	    addBulletSpeed.getBody().setLinearVelocity(new Vector2(10-2f, 0));
	}
    
	public void lateUpdate() {
        nowScreen.getCamera().position.x =getBody().getPosition().x;
	}
}
