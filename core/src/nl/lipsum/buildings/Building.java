package nl.lipsum.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import nl.lipsum.Drawable;
import nl.lipsum.TextureStore;

import static nl.lipsum.Config.TILE_SIZE;

public abstract class Building implements Drawable {
    public final int x;
    public final int y;

    public Building(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public abstract void draw(SpriteBatch batch);
//        batch.draw(this.tileTexture, TILE_SIZE * this.x - TILE_SIZE/2, TILE_SIZE * this.y- TILE_SIZE/2);

    public abstract void dispose();
}
