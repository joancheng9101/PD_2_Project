package com.mygdx.game.Screen;


import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Script.Player;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScreen;
import com.mygdx.game.objects.GameScript;

public class  PlayScreen extends GameScreen {
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private GameObject player;    
    
    final float mapwigth = 32.5f;
    
    @Override
    public void Start() 
    {
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("mygame.tmx");
        renderer =new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM);
        B2WorldCreator(getWorld(),map);
        
        BodyDef bdef = new BodyDef();
        bdef.position.set(5f, 2);
        bdef.type=BodyDef.BodyType.DynamicBody;
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setPosition(new Vector2(0,0));
        shape.setRadius(20/MyGdxGame.PPM);
        fdef.shape =shape;
        
        
        player = new GameObject("player", bdef, new Texture("player1.png"));
        player.addFixture(fdef);
        player.addscript(new Player());
        
        bdef = new BodyDef();
        bdef.position.set(5f, 2);
        bdef.type=BodyDef.BodyType.StaticBody;

        GameObject test = new GameObject("test", bdef, new Texture("player1.png"));
        test.addFixture(fdef);
        test.addscript(new GameScript()
        {
        	@Override
        	public void Start()
        	{
        		getBody().setGravityScale(0);
        	}
        	@Override
        	public void Update()
        	{
        	}
        });
        test.setParent(player);
        test.setLocalPosition(new Vector2(-0.5f, -0.5f));
	}

    private void B2WorldCreator(World world, TiledMap map){
        BodyDef bdef =new BodyDef();
        PolygonShape shape =new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        // ground
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            bdef.type= BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/ MyGdxGame.PPM,(rect.getY()+rect.getHeight()/2)/MyGdxGame.PPM);
            body=world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MyGdxGame.PPM, rect.getHeight()/2/MyGdxGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
        //stone
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect =((RectangleMapObject) object).getRectangle();
            bdef.type=BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX()+rect.getWidth()/2)/MyGdxGame.PPM,(rect.getY()+rect.getHeight()/2)/MyGdxGame.PPM);
            body=world.createBody(bdef);
            shape.setAsBox(rect.getWidth()/2/MyGdxGame.PPM, rect.getHeight()/2/MyGdxGame.PPM);
            fdef.shape=shape;
            body.createFixture(fdef);
        }
    }
    
    @Override
    public void Update()
    {
    	if(getCamera().position.x >= 35 || getCamera().position.x <= 2.5)
    	{
    		for (int i = 0; i < getGameObjectSize(); i++)
    		{
    			if(getGameObject(i).getParent() == null)
    			{
    				if(getCamera().position.x >= 35)
    				{
    					getGameObject(i).setPosition(new Vector2(getGameObject(i).getPosition().x - mapwigth, getGameObject(i).getPosition().y));
    				}
    				else if (getCamera().position.x <= 2.5) {
    					getGameObject(i).setPosition(new Vector2(getGameObject(i).getPosition().x + mapwigth, getGameObject(i).getPosition().y));
					}
    			}
    		}
    	}
    }
    @Override
    public void lateUpdate()
    {
    	
    }
    @Override
    public void render(float delta) {
    	super.render(delta);
    }

    @Override
    public void show() {
    	super.show();
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }

    @Override
    public void pause(){
    	super.pause();
    }
    @Override
    public void resume(){
    	super.resume();
    }
    @Override
    public void hide(){
    	super.hide();
    }
    @Override
    public void dispose(){
        super.dispose();
        ((OrthogonalTiledMapRenderer)renderer).dispose();
    }
}
