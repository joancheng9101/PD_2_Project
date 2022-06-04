package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screen.PlayScreen;
import com.mygdx.game.objects.GameScreen;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class MyGdxGame extends Game {
	public static final int V_WIDTH =400;
	public static final int V_HEIGHT=208;
	public static final float PPM=100;

	public SpriteBatch batch;
	
    private static MyGdxGame _instance;
    public static MyGdxGame instance() 
    {
        return _instance;
	}
	
	@Override
	public void create() {
		_instance = this;
		batch =new SpriteBatch();
		setScreen(new PlayScreen());
		getScreen().Debugmode(true);
		getScreen().Start();
	}
	@Override
	public void render (){
		super.render();
	}
	
	@Override
	public GameScreen getScreen()
	{
		return (GameScreen)super.getScreen();
	}
}

