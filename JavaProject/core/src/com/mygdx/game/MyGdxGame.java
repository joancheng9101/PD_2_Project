package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MyGdxGame extends Game {

	
	SpriteBatch batch;
	private OrthographicCamera camera;
	Texture image1;
	private Rectangle ghost1;
	
	
	@Override
	public void create () {
	
		batch = new SpriteBatch();
		image1 = new Texture("PacmanGhost1-1.png");
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1280, 720);
		
		ghost1 = new Rectangle();
		ghost1.width = 256;
		ghost1.height = 171;
		ghost1.x = 0;
		ghost1.y = 0;
		
	}

	
	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(image1, ghost1.x, ghost1.y);
		batch.end();
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			
			ghost1.x -= 10;
		}
			
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
			ghost1.x += 10;
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
			ghost1.y += 10;
		
		if(ghost1.x < 0)
			ghost1.x = 0;
		if(ghost1.x > 1300 - 256)
			ghost1.x = 1300 - 256;
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		image1.dispose();
	}
	
}
