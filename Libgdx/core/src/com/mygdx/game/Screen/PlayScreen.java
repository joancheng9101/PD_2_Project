package com.mygdx.game.Screen;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Script.Bullet;
import com.mygdx.game.Script.Ground;
import com.mygdx.game.Script.Player;
import com.mygdx.game.objects.GameObject;
import com.mygdx.game.objects.GameScreen;

public class  PlayScreen extends GameScreen {
	private MapRenderer renderer;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    
    private Player player;    
    private Player enemy;

    private float time = 0;

    public final short grouprock = -1;
    public final short categoryprock = 0x2;
    public final short categorypenemy = 0x4;

    public int isend = 0;
    
    BitmapFont font;
    BitmapFont Endfont;

    final float mapwigth = 32.5f;
    
    @Override
    public void Start() 
    {
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("assets/mygame.tmx");
        renderer =new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM);
        
        font = new BitmapFont(Gdx.files.internal("assets/font.fnt"),Gdx.files.internal("assets/font.png"),false);
        Endfont = new BitmapFont(Gdx.files.internal("assets/font.fnt"),Gdx.files.internal("assets/font.png"),false);
        
        CreateGround();
        
        BodyDef bdef = new BodyDef();
        bdef.position.set(4f, 1);
        bdef.type=BodyDef.BodyType.DynamicBody;
        FixtureDef fdef =new FixtureDef();
        CircleShape shape =new CircleShape();
        shape.setPosition(new Vector2(0,0));
        shape.setRadius(25/MyGdxGame.PPM);
        fdef.shape =shape;
        
        
        GameObject player = new GameObject("player", bdef, new Texture("assets/player1.png"));
        player.setScale(new Vector2(1.2f, 1.2f));
        player.addFixture(fdef);
        
        this.player = new Player();
        
        player.addscript(this.player);
        this.player.keys = new int[] { Input.Keys.W, Input.Keys.S, Input.Keys.D, Input.Keys.A };
        this.player.allowfire = true;
        
        bdef.position.set(6f, 1);
        bdef.type=BodyDef.BodyType.DynamicBody;
        shape =new CircleShape();
        shape.setPosition(new Vector2(0,0));
        shape.setRadius(15/MyGdxGame.PPM);
        fdef.shape =shape;
        
        this.enemy = new Player();
        GameObject enemy = new GameObject("enemy", bdef, new Texture("assets/player2.png"));
        fdef.filter.categoryBits = categorypenemy;
        fdef.filter.maskBits = ~categoryprock;
        enemy.addFixture(fdef);
        enemy.addscript(this.enemy);
        this.enemy.keys = new int[] { Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT, Input.Keys.LEFT };

	}

    private void CreateGround() 
    {
    	BodyDef bdef = new BodyDef();
        bdef.position.set(5f, MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2);
        bdef.type=BodyDef.BodyType.KinematicBody;

        GameObject mainground = new GameObject("mainground", bdef);
    	
        FixtureDef fdef = new FixtureDef();
        fdef.filter.groupIndex = grouprock;
        PolygonShape shape =new PolygonShape();
        shape.setAsBox(0.1f, MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2);
        fdef.shape=shape;
        mainground.addFixture(fdef);
    	mainground.addscript(new Ground());

    	bdef = new BodyDef();
        bdef.type=BodyDef.BodyType.StaticBody;

        GameObject downground = new GameObject("downground", bdef);
        fdef.filter.groupIndex = grouprock;
        shape =new PolygonShape();
        shape.setAsBox(MyGdxGame.V_WIDTH / MyGdxGame.PPM / 2, 0.5f);
        fdef.shape=shape;
        downground.setParent(mainground);
        downground.setLocalPosition(new Vector2(0, -(MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2) - 0.5f));
        downground.addFixture(fdef);
        
        GameObject upground = new GameObject("upground", bdef);
        fdef.filter.groupIndex = grouprock;
        upground.setParent(mainground);
        upground.setLocalPosition(new Vector2(0, (MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2) + 0.5f));
        upground.addFixture(fdef);
        
        shape =new PolygonShape();
        shape.setAsBox(0.5f, MyGdxGame.V_HEIGHT / MyGdxGame.PPM / 2);
        fdef.shape=shape;

        GameObject rightground = new GameObject("rightground", bdef);
        fdef.filter.groupIndex = grouprock;
        rightground.setParent(mainground);
        rightground.setLocalPosition(new Vector2((MyGdxGame.V_WIDTH / MyGdxGame.PPM / 2) + 0.5f, 0));
        rightground.addFixture(fdef);
        
        GameObject leftground = new GameObject("leftground", bdef, new Texture("assets/rect.png"));
        fdef.filter.groupIndex = 0;
        leftground.setColor(1, 0, 0, 1);
        leftground.setSize(1 * MyGdxGame.PPM * MyGdxGame.WindowsProportion, Gdx.graphics.getHeight());
        leftground.setParent(mainground);
        leftground.setLocalPosition(new Vector2(-(MyGdxGame.V_WIDTH / MyGdxGame.PPM / 2) - 0.4f, 0));
        leftground.addFixture(fdef);
	}
        
    @Override
    public void Update()
    {
    	if(isend == 0)
    	{
    		time += getdeltaTime();
		}
    	
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
    public void OnRenderMap()
    {
        renderer.setView((OrthographicCamera)getCamera());
        renderer.render();
    }

    @Override
    public void OnRender(SpriteBatch batch)
    {
    	String timeString = String.format("%02d", ((int)(time / 60))) + ":" + String.format("%02d", ((int)(time % 60))) + "." + String.format("%02d", ((int)((time*100) % 100)));
    	font.setColor(1, 0, 0, 1);
    	font.draw(batch, timeString, Gdx.graphics.getWidth() / 2 - 15 * timeString.length() / 2, Gdx.graphics.getHeight() - 10);
    	
    	if(isend == 0)
    	{
	    	if(player.life <= 0)
	    	{
	    		isend = -1;
	    	}
	    	else if(enemy.life <= 0) {
	    		isend = 1;
			}
    	}
    	if(isend == -1)
    	{
    		String endString = "The escapee wins!!";
    		Endfont.setColor(1, 0, 0, 1);
    		Endfont.draw(batch, endString, Gdx.graphics.getWidth() / 2 - 15 * endString.length() / 2, Gdx.graphics.getHeight() / 2);
    	}
    	else if(isend == 1) {
    		String endString = "Hunter wins!!";
    		Endfont.setColor(0, 1, 0, 1);
    		Endfont.draw(batch, endString, Gdx.graphics.getWidth() / 2 - 15 * endString.length() / 2, Gdx.graphics.getHeight() / 2);
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
