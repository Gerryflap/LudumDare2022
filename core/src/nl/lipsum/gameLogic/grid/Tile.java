package nl.lipsum.gameLogic.grid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.TextureStore;

import static nl.lipsum.Config.TILE_SIZE;

public enum Tile {
    GRASS_1(new Texture("grass_1.png")),
    GRASS_2(new Texture("grass_2.png")),
    GRASS_3(new Texture("grass_3.png")),
    DIRT(new Texture("dirt_1.png")),
    SAND(new Texture("sand_1.png"));

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
