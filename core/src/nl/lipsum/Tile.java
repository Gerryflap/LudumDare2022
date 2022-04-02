package nl.lipsum;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static nl.lipsum.Config.TILE_SIZE;

public class Tile implements Drawable {
    public final int x;
    public final int y;

    private Texture tileTexture;
    private final TextureStore textureStore;

    public Tile(int x, int y, String tileTextureName, TextureStore textureStore) {
        this.x = x;
        this.y = y;
        this.textureStore = textureStore;
        try {
            this.tileTexture = this.textureStore.getTileTextureByName(tileTextureName);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void setTexture(String name) throws Exception {
        this.tileTexture = this.textureStore.getTileTextureByName(name);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2);
    }

    public void dispose() {
        this.tileTexture.dispose();
    }
}
