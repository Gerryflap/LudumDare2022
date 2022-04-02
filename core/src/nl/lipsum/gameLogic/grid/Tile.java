package nl.lipsum.gameLogic.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.TextureStore;

import static nl.lipsum.Config.TILE_SIZE;

public enum Tile {
    GRASS(new Texture("orangeTile.jpg")),
    DIRT(new Texture("blueTile.jpg")),
    SAND(new Texture("whiteTile.jpg"));

    private final Texture tileTexture;

    Tile(Texture texture) {
        this.tileTexture = texture;
    }


    public void draw(SpriteBatch batch, int gx, int gy) {
        batch.draw(this.tileTexture, TILE_SIZE * gx - TILE_SIZE/2, TILE_SIZE * gy- TILE_SIZE/2, TILE_SIZE, TILE_SIZE);
    }

    public void dispose() {
        this.tileTexture.dispose();
    }
}
