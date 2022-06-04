package com.mygdx.game.Screen;


import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
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

public class  PlayScreen extends GameScreen {
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private GameObject player;    
    
    
    @Override
    public void Start() 
    {
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("mygame.tmx");
        renderer =new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM);
        B2WorldCreator(getWorld(),map);
        
        BodyDef bdef =new BodyDef();
        bdef.position.set(32/ MyGdxGame.PPM, 32/MyGdxGame.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setRadius(20/MyGdxGame.PPM);
        fdef.shape =shape;
        
        player = new GameObject("player", bdef);
        player.addFixture(fdef);
        player.addscript(new Player());
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
