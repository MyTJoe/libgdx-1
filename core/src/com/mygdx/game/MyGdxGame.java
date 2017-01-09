package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureRegion down, up, right, left, stand;
	TextureRegion tree;
	private int facing;
	private float time;
	private float x, y, yv, xv;
	private static float MAX_VELOCITY = 100;

	private static final int UP = 1;
	private static final int DOWN = 2;
	private static final int LEFT = 3;
	private static final int RIGHT = 4;
	private static final int STAND = 5;

	private static final int WIDTH = 16;
	private static final int HEIGHT = 16;

	private static final int DRAW_WIDTH = WIDTH * 3;
	private static final int DRAW_HEIGHT = HEIGHT * 3;

	private OrthographicCamera camera;
	private TiledMapRenderer tiledMapRenderer;


	@Override
	public void create () {
		TiledMap tiledMap;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		camera.update();
		tiledMap = new TmxMapLoader().load("level1.tmx");
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);

		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		tree = new TextureRegion(tiles,0, 8, 16, 16);
		TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		stand = grid[6][2];
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);

	}

	@Override
	public void render () {
		time += Gdx.graphics.getDeltaTime();
		move();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		TextureRegion img = up;
		batch.begin();
		batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 0,100, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 232,50, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 300,167, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 500,425, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 23,399, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 555,19, DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(tree, 400, 375, DRAW_WIDTH, DRAW_HEIGHT);

		if (facing == UP) {
			batch.draw(up, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		} else if (facing == RIGHT) {
			batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		} else if (facing == LEFT) {
			batch.draw(left, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		} else if (facing == DOWN) {
			batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		} else if (facing == STAND) {
			batch.draw(stand, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		batch.end();
	}

	float decelerate(float velocity) {
		float deceleration = 0.95f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	public void move() {

		if (y < 0) {
			y = Gdx.graphics.getHeight();
		} else if (y > Gdx.graphics.getHeight()-1) {
			y = 0;
		}
		if (x < 0) {
			x = Gdx.graphics.getWidth();
		} else if (x > Gdx.graphics.getWidth()-1) {
			x = 0;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			yv = MAX_VELOCITY;
			facing = UP;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				yv += MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			yv = MAX_VELOCITY * -1;
			facing = DOWN;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				yv -= MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xv = MAX_VELOCITY;
			facing = RIGHT;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				xv += MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xv = MAX_VELOCITY * -1;
			facing = LEFT;
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				xv -= MAX_VELOCITY;
			}
		}

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}

	@Override
	public void dispose() {
		batch.dispose();

	}
}
