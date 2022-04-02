package nl.lipsum;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import static nl.lipsum.Config.*;

public class LudumDare2022 extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureStore textureStore;
	TileGrid tileGrid;
	
	@Override
	public void create () {
		textureStore = new TextureStore();
		tileGrid = new TileGrid(WIDTH,HEIGHT);
		tileGrid.setTile(5, 5, new Tile(5, 5, "white", textureStore));
		tileGrid.setTile(6, 5, new Tile(6, 5, "orange", textureStore));
		tileGrid.setTile(7, 7, new Tile(7, 7, "white", textureStore));
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");

		Gdx.graphics.setWindowedMode(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
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
