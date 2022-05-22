package com.mygdx.game.PlayScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Role.Roleone;
import com.mygdx.game.Tools.B2WorldCreator;

public class  PlayScreen implements Screen {
    private MyGdxGame game;
    private Viewport gamePort;
    private OrthographicCamera gamecam;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private Roleone player;
    public PlayScreen(MyGdxGame game) {
        this.game=game;
        gamecam =new OrthographicCamera();
        gamePort = new FitViewport(MyGdxGame.V_WIDTH/MyGdxGame.PPM,MyGdxGame.V_HEIGHT/MyGdxGame.PPM,gamecam);
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("mygame.tmx");
        renderer =new OrthogonalTiledMapRenderer(map, 1/MyGdxGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth()/2 ,gamePort.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world,map);
        player = new Roleone(world);
    }
    public void handleInput(float dt) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(1.2f, 5f), player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT ) && player.b2body.getLinearVelocity().x<=2)
            player.b2body.applyLinearImpulse(new Vector2(0.05f,0) ,player.b2body.getWorldCenter(),true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x>=-2)
            player.b2body.applyLinearImpulse(new Vector2(-0.05f,0) ,player.b2body.getWorldCenter(),true);
    }
    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        gamecam.position.x =player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
    }
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        b2dr.render(world, gamecam.combined);
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
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
    }

}
