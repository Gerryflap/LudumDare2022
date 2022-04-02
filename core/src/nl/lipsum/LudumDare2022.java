package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static nl.lipsum.Config.*;

public class LudumDare2022 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Camera camera;
	TextureStore textureStore;
	TileGrid tileGrid;

	@Override
	public void create () {
		textureStore = new TextureStore();
		tileGrid = new TileGrid(WIDTH,HEIGHT);
		tileGrid.setTile(0, 0, new Tile(0, 0, "orange", textureStore));
		tileGrid.setTile(20, 0, new Tile(20, 0, "orange", textureStore));
		tileGrid.setTile(0, 20, new Tile(0, 20, "orange", textureStore));
		tileGrid.setTile(20, 20, new Tile(20, 20, "orange", textureStore));
		tileGrid.setTile(10, 10, new Tile(10, 10, "white", textureStore));
		tileGrid.setTile(0, 10, new Tile(0, 10, "white", textureStore));
		tileGrid.setTile(10, 0, new Tile(10, 0, "white", textureStore));
		tileGrid.setTile(10, 20, new Tile(10, 20, "white", textureStore));
		tileGrid.setTile(20, 10, new Tile(20, 10, "white", textureStore));
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		camera = new OrthographicCamera(1280, 720);
//		img = new Texture("badlogic.jpg");

//		Gdx.graphics.setWindowedMode(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
	}

	@Override
	public void render () {
		// Step Methods called here for controllers

		camera.update();

		ScreenUtils.clear(1, 0, 0, 1);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		// Render methods called here for controllers
//		batch.draw(img, 0, 0);
		tileGrid.draw(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		img.dispose();
		tileGrid.dispose();
	}
}
