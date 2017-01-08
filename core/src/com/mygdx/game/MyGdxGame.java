package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static com.badlogic.gdx.Input.Keys.SPACE;

public class MyGdxGame extends ApplicationAdapter {
	private SpriteBatch batch;
	private TextureRegion down, up, right, left;
	private int facing;
	private float time;

	private float x, y, yv, xv;
	private static float MAX_VELOCITY = 100;

	public static final int UP = 1;
	public static final int DOWN = 2;
	public static final int LEFT = 3;
	public static final int RIGHT = 4;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
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
		TextureRegion img = down;

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, x, y);

		if(facing == UP) {
			batch.draw(up, x, y);
		} else if (facing == DOWN) {
			batch.draw(down, x, y);
		} else if (facing == RIGHT) {
			batch.draw(right, x, y);
		} else if (facing == LEFT) {
			batch.draw(left, x, y);
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
		if (Gdx.input.isKeyPressed(UP)) {
			yv = MAX_VELOCITY;
			facing = UP;
			if (Gdx.input.isKeyPressed(SPACE)) {
				yv *= 3;
			}
		}
		if (Gdx.input.isKeyPressed(DOWN)) {
			yv = MAX_VELOCITY; // yv = MAX_VELOCITY * -1;
			facing = DOWN;
			if (Gdx.input.isKeyPressed(SPACE)) {
				yv *= MAX_VELOCITY * -3;
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
