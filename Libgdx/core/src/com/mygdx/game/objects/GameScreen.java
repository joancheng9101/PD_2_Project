package com.mygdx.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.MyGdxGame;

public abstract class GameScreen implements Screen {
    private Viewport gamePort;
    private OrthographicCamera gamecam;
    protected MapRenderer renderer;
    private World world;
    private SpriteBatch batch;
    private Box2DDebugRenderer b2dr;
    
    private boolean debugmode = false;
    private ArrayList<GameObject> objectlist; 
    
    private ArrayList<GameObject> destroyobjectlist; 
    
    private float deltaTime = 0;
    
    public MapRenderer getRenderer() 
    {
		return renderer;
	}

    public World getWorld() 
    {
		return world;
	}
    
    public Camera getCamera() 
    {
		return gamecam;
	}
    
    public GameScreen() {
        gamecam =new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/MyGdxGame.PPM,MyGdxGame.V_HEIGHT/MyGdxGame.PPM,gamecam);

        gamecam.position.set(gamePort.getWorldWidth()/2 ,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10),true);
        
        batch = new SpriteBatch();
        
        createCollisionListener();
        
        objectlist = new ArrayList<>();
        destroyobjectlist = new ArrayList<>();
    }
    
    private void createCollisionListener() {
        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {
            	Fixture A = contact.getFixtureA();
            	Fixture B = contact.getFixtureB();
                if(A.getUserData() != null) ((GameObject)A.getUserData()).OnBeginContact(A, B);
                if(B.getUserData() != null) ((GameObject)B.getUserData()).OnBeginContact(B, A);
            }

            @Override
            public void endContact(Contact contact) {
            	Fixture A = contact.getFixtureA();
            	Fixture B = contact.getFixtureB();
            	if(A.getUserData() != null) ((GameObject)A.getUserData()).OnEndContact(A, B);;
            	if(B.getUserData() != null) ((GameObject)B.getUserData()).OnEndContact(B, A);;
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
    }
    
    
    public void Start() {
		
	}
    
    public void Debugmode(boolean debugmode) 
    {
    	this.debugmode = debugmode;
    	if(debugmode)
    	{
    		b2dr = new Box2DDebugRenderer();
    	}
    	else if(b2dr != null) {
    		b2dr.dispose();
    		b2dr = null;
		}
	}
    
    public void addGameObject(GameObject gameObject)
    {
    	if(!objectlist.contains(gameObject))
    	{
    		objectlist.add(gameObject);
    	}
    }
    
    public void addDestoryGameObject(GameObject gameObject)
    {
    	if(!destroyobjectlist.contains(gameObject))
    	{
    		destroyobjectlist.add(gameObject);
    	}
    }
    
    public float getdeltaTime() 
    {
    	return deltaTime;
	}
    
    public GameObject getGameObject(int index) {
    	return objectlist.get(index);
	}
    
    public int getGameObjectSize() {
    	return objectlist.size();
	}
    
    public GameObject getGameObject(String name) {
    	for (GameObject gameObject : objectlist) {
			if(gameObject.name == name) return gameObject;
		}
    	return null;
	}

    public GameObject[] getGameObjects(String name) {
    	ArrayList<GameObject> scripts = new ArrayList<>();
    	for (GameObject gameObject : objectlist) {
    		if(gameObject.name == name) scripts.add(gameObject);
		}
    	GameObject[] ans = new GameObject[scripts.size()];
    	return scripts.toArray(ans);
	}
    
    public void Update(){
    	
    }
    public void lateUpdate(){
    	
    }
    public void update(){
    	for (GameObject gameObject : objectlist) 
    	{
    		gameObject.Update();
		}
    	Update();
        world.step(1/60f,6,2);
    	for (GameObject gameObject : objectlist) 
    	{
    		gameObject.lateUpdate();
		}
        lateUpdate();
        for (GameObject gameObject : destroyobjectlist) 
    	{
    		gameObject.getTexture().dispose();
    		for(int i = 0; i < gameObject.getFixtureSize(); i++)
    		{
    			gameObject.destoryFixture(i);
    		}
    		world.destroyBody(gameObject.getBody());
        	if(objectlist.contains(gameObject))
        	{
        		objectlist.remove(gameObject);
        	}
		}
        destroyobjectlist.clear();
        
        gamecam.update();
        renderer.setView(gamecam);
    }
    
    @Override
    public void render(float delta) {
    	deltaTime = delta;
        update();
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        batch.begin();
    	for (GameObject gameObject : objectlist) 
    	{
    		gameObject.updateSpritePosition();
    		//batch.draw(gameObject, gameObject.getX(), gameObject.getY());
    		gameObject.draw(batch);
		}
    	batch.end();
        if(debugmode)
        {
        	b2dr.render(world, gamecam.combined);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height){
        gamePort.update(width,height);
    }

    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){

    }
    @Override
    public void dispose(){
        world.dispose();
        objectlist.clear();
        batch.dispose();
        for (GameObject gameObject : objectlist) 
    	{
    		gameObject.getTexture().dispose();
    		for(int i = 0; i < gameObject.getFixtureSize(); i++)
    		{
    			gameObject.destoryFixture(i);
    		}
    		world.destroyBody(gameObject.getBody());
        	if(objectlist.contains(gameObject))
        	{
        		objectlist.remove(gameObject);
        	}
		}
        if(debugmode)
        {
        	b2dr.dispose();
        }
    }
}
