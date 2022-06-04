package com.mygdx.game.objects;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;

public abstract class GameScreen implements Screen {
    private Viewport gamePort;
    private OrthographicCamera gamecam;
    protected MapRenderer renderer;
    private World world;
    
    private Box2DDebugRenderer b2dr;
    
    private boolean debugmode = false;
    private ArrayList<GameObject> objectlist; 
    
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
        
        
        
        objectlist = new ArrayList<>();
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
    	objectlist.add(gameObject);
    }
    
    public void update(float dt){
    	for (GameObject gameObject : objectlist) 
    	{
    		gameObject.Update();
		}
        world.step(1/60f,6,2);
        gamecam.update();
        renderer.setView(gamecam);
    }
    
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        
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
        if(debugmode)
        {
        	b2dr.dispose();
        }
    }
}
